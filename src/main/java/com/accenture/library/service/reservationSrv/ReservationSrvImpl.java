package com.accenture.library.service.reservationSrv;

import com.accenture.library.domain.Book;
import com.accenture.library.domain.Reservation;
import com.accenture.library.domain.User;
import com.accenture.library.dto.ReservationDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.ReservationRepository;
import com.accenture.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationSrvImpl implements ReservationSrv {

    private ReservationRepository reservationRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Autowired
    public ReservationSrvImpl(ReservationRepository reservationRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Long makeReservation(Long bookId, String userName) {
        User user = userRepository.findByUserName(userName);
        Reservation reservation = new Reservation(new Book(bookId), user, new Date());
        try {
            return reservationRepository.save(reservation).getId();
        } catch (Exception e) {
            String message = "Unable to save to database " + "bookId:" + bookId + " userId:" + user.getId();
            throw new LibraryException(message, e);
        }
    }

    @Override
    @Transactional
    public Long handOut(Long reservationId) {
        Optional<Reservation> findReservation = reservationRepository.findById(reservationId);
        if (!findReservation.isPresent()) {
            throw new LibraryException("No reservation with id: " + reservationId);
        }
        Reservation reservation = findReservation.get();
        Book book = reservation.getBook();
        if (book.getAvailable() == 0) {
            throw new LibraryException("Book with id: " + book.getId() + " is not available");
        }
        book.setAvailable(book.getAvailable() - 1);
        bookRepository.save(book);

        reservation.setReservationDate(new Date());
        reservation.setHandOut(true);
        return reservationRepository.save(reservation).getId();
    }

    @Override
    @Transactional
    public Long takeIn(Long reservationId) {
        Optional<Reservation> findReservation = reservationRepository.findById(reservationId);
        if (!findReservation.isPresent()) {
            throw new LibraryException("No reservation with id: " + reservationId);
        }
        Reservation reservation = findReservation.get();

        Book book = reservation.getBook();
        book.setAvailable(book.getAvailable() + 1);
        bookRepository.save(book);

        reservation.setReturned(true);
        return reservationRepository.save(reservation).getId();
    }

    @Override
    public List<ReservationDTO> getByParameters(String bookTitle, String userName, Boolean returned) {
        return reservationRepository.getByParameters(bookTitle, userName, returned);
    }

    @Override
    public List<ReservationDTO> getReservationQueue() {
        return reservationRepository.getQueue();
    }

}
