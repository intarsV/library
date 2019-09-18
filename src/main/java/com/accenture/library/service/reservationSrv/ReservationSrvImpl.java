package com.accenture.library.service.reservationSrv;

import com.accenture.library.domain.Book;
import com.accenture.library.domain.Reservation;
import com.accenture.library.domain.User;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.ReservationRepository;
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

    @Autowired
    public ReservationSrvImpl(ReservationRepository reservationRepository, BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public Long save(Long bookId, Long userId, Date reservationDate) {
        Optional<Book> foundBook = bookRepository.findById(bookId);
        if (!foundBook.isPresent() || foundBook.get().getAvailable() == 0) {
            throw new LibraryException("No such available book found!");
        }
        Book book = foundBook.get();
        book.setAvailable(book.getAvailable() - 1);
        User user = new User();
        user.setId(userId);
        Reservation reservation = new Reservation(book, user, reservationDate);
        return reservationRepository.save(reservation).getId();
    }

    @Override
    @Transactional
    public Long update(Long reservationId) {
        Optional<Reservation> foundReservation = reservationRepository.findById(reservationId);
        if (!foundReservation.isPresent()) {
            throw new LibraryException("No such reservation found!");
        }
        Reservation reservation = foundReservation.get();
        reservation.setReturned(true);
        Book book = reservation.getBook();
        book.setAvailable(book.getAvailable() + 1);
        return reservationRepository.save(reservation).getId();
    }


    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAllByReturned(false);
    }

    @Override
    public List<Reservation> getAllByBook(Long bookId) {
        Optional<Book> foundBook = bookRepository.findById(bookId);
        if (!foundBook.isPresent()) {
            throw new LibraryException("No such book found!");
        }
        Book book = foundBook.get();
        return reservationRepository.findByBook(book);
    }

    @Override//Need check user
    public List<Reservation> getAllByUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return reservationRepository.findByUser(user);
    }
}
