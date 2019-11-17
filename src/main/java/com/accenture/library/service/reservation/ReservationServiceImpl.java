package com.accenture.library.service.reservation;

import com.accenture.library.domain.Book;
import com.accenture.library.domain.Reservation;
import com.accenture.library.domain.User;
import com.accenture.library.dto.ReservationDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.ReservationRepository;
import com.accenture.library.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final String DATABASE_SAVE_ERROR = "Database save error";
    private static final String QUEUE = "QUEUE";
    private static final String HANDOUT = "HANDOUT";
    private static final String RETURNED = "RETURNED";
    private static final String CANCELED = "CANCELED";

    private ReservationRepository reservationRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository
            , BookRepository bookRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReservationDTO makeReservation(Long bookId, String userName) {
        final User user = userRepository.findByUserName(userName);
        final Optional<Book> bookFound = bookRepository.findById(bookId);
        if (!bookFound.isPresent()) {
            throw new LibraryException("No book with such ID");
        }
        final Reservation reservation = new Reservation(new Book(bookId), user, new Date(), QUEUE);
        final ReservationDTO reservationDTO = new ReservationDTO();
        try {
            reservationDTO.setId(reservationRepository.save(reservation).getId());
        } catch (Exception e) {
            logger.error(DATABASE_SAVE_ERROR, e);
            throw new LibraryException(DATABASE_SAVE_ERROR);
        }
        reservationDTO.setBookTitle(bookFound.get().getTitle());
        reservationDTO.setReservationDate(reservation.getReservationDate());
        return reservationDTO;
    }

    @Override
    @Transactional
    public Long updateStatus(Long reservationId, String userName, String status, boolean isAdmin) {
        final Reservation reservation = verifyReservationId(reservationId);
        switch (status) {
            case HANDOUT:
                return handOutReservation(reservation, isAdmin);
            case RETURNED:
                return returnReservation(reservation, isAdmin);
            case CANCELED:
                return cancelReservation(reservation, userName, isAdmin);
            default:
                throw new LibraryException("Unexpected status value");
        }
    }

    @Override
    public List<ReservationDTO> getByParameters(String bookTitle, String userName, String status, boolean isAdmin) {
        if (isAdmin) {
            validateAdminRequest(bookTitle, userName, status);
        } else {
            validateUserRequest(bookTitle, status);
        }
        return reservationRepository.getByParameters(bookTitle, userName, status);
    }

    //Auxiliary methods

    private Long cancelReservation(Reservation reservation, String userName, boolean isAdmin) {
        final User user = reservation.getUser();

        if (!user.getUserName().equals(userName) && !isAdmin) {
            throw new LibraryException("User don't have such reservation");
        }
        reservation.setStatus(CANCELED);
        try {
            reservationRepository.save(reservation);
        } catch (Exception e) {
            logger.error(DATABASE_SAVE_ERROR, e);
            throw new LibraryException(DATABASE_SAVE_ERROR);
        }
        return reservation.getId();
    }

    private Long handOutReservation(Reservation reservation, boolean isAdmin) {
        if (!isAdmin) {
            throw new AccessDeniedException("You don't have permission for this action");
        }
        final Book book = reservation.getBook();
        if (book.getAvailable() == 0) {
            throw new LibraryException("Book with is not available");
        }
        book.setAvailable(book.getAvailable() - 1);

        reservation.setReservationDate(new Date());
        reservation.setStatus(HANDOUT);
        saveToDatabase(reservation, book);
        return reservation.getId();
    }

    private Long returnReservation(Reservation reservation, boolean isAdmin) {
        if (!isAdmin) {
            throw new AccessDeniedException("You don't have permission for this action");
        }
        final Book book = reservation.getBook();
        book.setAvailable(book.getAvailable() + 1);
        reservation.setStatus(RETURNED);
        saveToDatabase(reservation, book);
        return reservation.getId();
    }

    private Reservation verifyReservationId(Long reservationId) {
        final Optional<Reservation> findReservation = reservationRepository.findById(reservationId);
        if (!findReservation.isPresent()) {
            throw new LibraryException("No such reservation");
        }
        return findReservation.get();
    }

    private void validateUserRequest(String bookTitle, String status) {
        if (StringUtils.isEmpty(bookTitle) && StringUtils.isEmpty(status)) {
            throw new LibraryException("Search parameters missing");
        }
    }

    private void validateAdminRequest(String bookTitle, String userName, String status) {
        if (StringUtils.isEmpty(bookTitle) && StringUtils.isEmpty(userName) && StringUtils.isEmpty(status)) {
            throw new LibraryException("Search parameters missing");
        }
    }

    private void saveToDatabase(Reservation reservation, Book book) {
        try {
            bookRepository.save(book);
            reservationRepository.save(reservation);
        } catch (Exception e) {
            logger.error(DATABASE_SAVE_ERROR, e);
            throw new LibraryException(DATABASE_SAVE_ERROR);
        }
    }
}
