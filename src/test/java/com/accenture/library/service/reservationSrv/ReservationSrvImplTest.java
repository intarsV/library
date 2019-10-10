package com.accenture.library.service.reservationSrv;

import com.accenture.library.domain.Author;
import com.accenture.library.domain.Book;
import com.accenture.library.domain.Reservation;
import com.accenture.library.domain.User;
import com.accenture.library.dto.ReservationDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.ReservationRepository;
import com.accenture.library.repository.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationSrvImplTest {
    private static final Long RESERVATION_ID = 1L;

    private static final Long BOOK_ID = 2L;
    private static final String BOOK_TITLE = "GoodBook";
    private static final Long USER_ID = 3L;
    private static final String USER_NAME = "Jānis";
    private static final Date RESERVATION_DATE = Calendar.getInstance().getTime();
    private static final String GENRE = "NOVEL";
    private static final int INITIAL_COPIES = 3;
    private static final int BEFORE_TAKE_IN = 2;

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    ReservationSrvImpl service;

    @Test
    public void makeReservationShouldReturnSavedReservationId() {
        User user = createTestUser();
        Book book = createBook(INITIAL_COPIES);
        final Reservation reservation = new Reservation(book, new User(), RESERVATION_DATE, false, false, false);
        reservation.setId(RESERVATION_ID);
        when(userRepository.findByUserName(USER_NAME)).thenReturn(user);
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        assertEquals(RESERVATION_ID, service.makeReservation(BOOK_ID, USER_NAME));
    }

    @Test
    public void makeReservationShouldThrowExceptionOnSaveError() {
        User user = createTestUser();
        Book book = createBook(INITIAL_COPIES);
        final Reservation reservation = new Reservation(book, new User(), RESERVATION_DATE, false, false, false);
        reservation.setId(RESERVATION_ID);
        when(userRepository.findByUserName(USER_NAME)).thenReturn(user);
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
        when(reservationRepository.save(any(Reservation.class))).thenThrow(
                new LibraryException("Unable to save to database bookId:" + BOOK_ID + " userId:" + USER_ID));
        exception.expect(LibraryException.class);
        exception.expectMessage("Unable to save to database bookId:" + BOOK_ID + " userId:" + USER_ID);
        service.makeReservation(BOOK_ID, USER_NAME);
    }

    @Test
    public void makeReservationShouldThrowExceptionIfBookIdNotFound() {
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.empty());
        exception.expect(LibraryException.class);
        exception.expectMessage("No book with such ID");
        service.makeReservation(BOOK_ID, USER_NAME);
    }

    @Test
    public void handOutShouldUpdateReservationStatusAndAvailableBookCopies() {
        final Book book = createBook(INITIAL_COPIES);
        final Reservation reservation = new Reservation(book, new User(), RESERVATION_DATE, true, false, false);
        reservation.setId(RESERVATION_ID);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(
                Optional.of(reservation));
        when(bookRepository.save(book)).thenReturn(book);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        assertEquals(RESERVATION_ID, service.handOut(RESERVATION_ID));
        assertEquals(INITIAL_COPIES - 1, book.getAvailable());
    }

    @Test
    public void handOutShouldThrowExceptionIfReservationIdNotFound() {
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(
                Optional.empty());
        exception.expect(LibraryException.class);
        exception.expectMessage("No reservation with id: " + RESERVATION_ID);
        service.handOut(RESERVATION_ID);
    }

    @Test
    public void handOutShouldThrowExceptionIfBookAvailableCopiesIsZero() {
        final int AVAILABLE_COPIES = 0;
        final Book book = createBook(AVAILABLE_COPIES);
        book.setId(BOOK_ID);
        final Reservation reservation = new Reservation(book, new User(), RESERVATION_DATE, true, false, false);
        reservation.setId(RESERVATION_ID);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(
                Optional.of(reservation));
        exception.expect(LibraryException.class);
        exception.expectMessage("Book with id: " + BOOK_ID + " is not available");
        service.handOut(RESERVATION_ID);
    }

    @Test
    public void ByParametersShouldReturnHistoryReservationList() {
        final ReservationDTO reservationDTO = createReservationDTO(
                true);
        final List<ReservationDTO> mockList = createReservationDTOList(reservationDTO);
        when(reservationRepository.getByParameters(BOOK_TITLE, USER_NAME, true, true))
                .thenReturn(mockList);
        assertEquals(mockList, service.getByParameters(BOOK_TITLE, USER_NAME, true, true));
    }

    @Test
    public void takeInShouldUpdateReservationStatusAndAvailableBookCopies() {
        final Book book = createBook(BEFORE_TAKE_IN);
        final Reservation reservation = new Reservation(book, new User(), RESERVATION_DATE, true, true, false);
        reservation.setId(RESERVATION_ID);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(
                Optional.of(reservation));
        when(bookRepository.save(book)).thenReturn(book);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        assertEquals(RESERVATION_ID, service.takeIn(RESERVATION_ID));
        assertEquals(BEFORE_TAKE_IN + 1, book.getAvailable());
    }

    @Test
    public void takeInShouldThrowExceptionIfReservationIdNotFound() {
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(
                Optional.empty());
        exception.expect(LibraryException.class);
        exception.expectMessage("No reservation with id: " + RESERVATION_ID);
        service.takeIn(RESERVATION_ID);
    }

    @Test
    public void ByParametersShouldReturnActiveReservationList() {
        final ReservationDTO reservationDTO = createReservationDTO(
                true);
        final List<ReservationDTO> mockList = createReservationDTOList(reservationDTO);
        when(reservationRepository.getByParameters(BOOK_TITLE, USER_NAME, true, false))
                .thenReturn(mockList);
        assertEquals(mockList, service.getByParameters(BOOK_TITLE, USER_NAME, true, false));
    }

    @Test
    public void byParametersShouldReturnEmptyListIfParametersNullOrEmpty() {
        final List<ReservationDTO> list = new ArrayList<>();
        assertEquals(list, service.getByParameters(null, "", null, null));
    }

    @Test
    public void QueueForAdminShouldReturnReservationQueueList() {
        final ReservationDTO reservationDTO = createReservationDTO(
                false);
        final List<ReservationDTO> mockList = createReservationDTOList(reservationDTO);
        when(reservationRepository.getQueue()).thenReturn(mockList);
        assertEquals(mockList, service.getReservationQueue());
    }


    ///Auxiliary methods

    private ReservationDTO createReservationDTO(boolean handOut) {
        final ReservationDTO reservationDTO = new ReservationDTO(RESERVATION_ID, BOOK_ID, BOOK_TITLE,
                USER_ID, USER_NAME, RESERVATION_DATE, handOut, false, false);
        return reservationDTO;
    }

    private List<ReservationDTO> createReservationDTOList(ReservationDTO reservationDTO) {
        final List<ReservationDTO> reservationDTOList = new ArrayList<>();
        reservationDTOList.add(reservationDTO);
        return reservationDTOList;
    }

    private Book createBook(int available) {
        Author author = new Author();
        return new Book(BOOK_TITLE, author, GENRE, INITIAL_COPIES, available, false);
    }

    private User createTestUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setUserName(USER_NAME);
        return user;
    }

}