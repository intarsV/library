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
import com.accenture.library.service.reservation.ReservationServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {
    private static final Long RESERVATION_ID = 1L;
    private static final Long BOOK_ID = 2L;
    private static final String BOOK_TITLE = "GoodBook";
    private static final Long USER_ID = 3L;
    private static final String USER_NAME = "Jānis";
    private static final Date RESERVATION_DATE = Calendar.getInstance().getTime();
    private static final String GENRE = "NOVEL";
    private static final int INITIAL_COPIES = 3;
    private static final int BEFORE_TAKE_IN = 2;
    private static final String QUEUE = "QUEUE";
    private static final String HANDOUT = "HANDOUT";
    private static final String RETURNED = "RETURNED";
    private static final String CANCELED = "CANCELED";
    private static final String DATABASE_SAVE_ERROR = "Database save error";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    ReservationServiceImpl service;

    @Test
    public void makeReservationShouldReturnSavedReservationDTO() {
        User user = createTestUser();
        Book book = createBook(INITIAL_COPIES);
        final Reservation reservation = new Reservation(book, new User(), RESERVATION_DATE, QUEUE);
        reservation.setId(RESERVATION_ID);
        when(userRepository.findByUserName(USER_NAME)).thenReturn(user);
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        ReservationDTO reservationDTO = service.makeReservation(BOOK_ID, USER_NAME);
        assertEquals("GoodBook", reservationDTO.getBookTitle());
        assertEquals(QUEUE, reservationDTO.getStatus());
    }

    @Test
    public void shouldThrowErrorOnRepositorySave() {
        User user = createTestUser();
        Book book = createBook(INITIAL_COPIES);
        final Reservation reservation = new Reservation(book, new User(), RESERVATION_DATE, QUEUE);
        reservation.setId(RESERVATION_ID);
        when(userRepository.findByUserName(USER_NAME)).thenReturn(user);
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
        exception.expect(LibraryException.class);
        exception.expectMessage("Database save error");
        service.makeReservation(BOOK_ID, USER_NAME);
    }

    @Test
    public void makeReservationShouldThrowExceptionOnSaveError() {
        User user = createTestUser();
        Book book = createBook(INITIAL_COPIES);
        final Reservation reservation = new Reservation(book, new User(), RESERVATION_DATE, QUEUE);
        reservation.setId(RESERVATION_ID);
        when(userRepository.findByUserName(USER_NAME)).thenReturn(user);
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
        when(reservationRepository.save(any(Reservation.class))).thenThrow(
                new LibraryException(DATABASE_SAVE_ERROR));
        exception.expect(LibraryException.class);
        exception.expectMessage(DATABASE_SAVE_ERROR);
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
    public void updateReservationShouldThrowExceptionUnexpectedStatus() {
        final String UNKNOWN_STATUS="UNKNOWN";
        final Reservation reservation = createReservation(QUEUE);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.of(reservation));
        exception.expect(LibraryException.class);
        exception.expectMessage("Unexpected status value");
        service.updateStatus(RESERVATION_ID, USER_NAME, UNKNOWN_STATUS, false);
    }

    @Test
    public void shouldCancelReservation() {
        final Reservation reservation = createReservation(CANCELED);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        assertEquals(RESERVATION_ID, service.updateStatus(RESERVATION_ID, USER_NAME, CANCELED, false));
    }

    @Test
    public void shouldThrowErrorOnReservationCancelRepositorySave() {
        final Reservation reservation = createReservation(CANCELED);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.of(reservation));
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenThrow(new DataAccessException("Bac!") {
        });
        exception.expect(LibraryException.class);
        exception.expectMessage("Database save error");
        service.updateStatus(RESERVATION_ID, USER_NAME, CANCELED, false);
    }

    @Test
    public void shouldThrowExceptionOnReservationCancelNoReservation() {
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.empty());
        exception.expect(LibraryException.class);
        exception.expectMessage("No such reservation");
        service.updateStatus(RESERVATION_ID, USER_NAME, CANCELED, false);
    }

    @Test
    public void shouldThrowExceptionOnReservationCancelNoReservationForUser() {
        final Reservation reservation = createReservation(CANCELED);
        final String DIFFERENT_USER = "Karlis";
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.of(reservation));
        exception.expect(LibraryException.class);
        exception.expectMessage("User don't have such reservation");
        service.updateStatus(RESERVATION_ID, DIFFERENT_USER, CANCELED, false);
    }

    @Test
    public void handOutShouldUpdateReservationStatusAndAvailableBookCopies() {
        final Book book = createBook(INITIAL_COPIES);
        final Reservation reservation = new Reservation(book, new User(), RESERVATION_DATE, HANDOUT);
        reservation.setId(RESERVATION_ID);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(
                Optional.of(reservation));
        when(bookRepository.save(book)).thenReturn(book);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        assertEquals(RESERVATION_ID, service.updateStatus(RESERVATION_ID, USER_NAME, HANDOUT, true));
        assertEquals(INITIAL_COPIES - 1, book.getAvailable());
    }

    @Test
    public void handOutShouldThrowExceptionIfReservationIdNotFound() {
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.empty());
        exception.expect(LibraryException.class);
        exception.expectMessage("No such reservation");
        service.updateStatus(RESERVATION_ID, USER_NAME, HANDOUT, true);
    }

    @Test
    public void handOutShouldThrowExceptionIfUserIsNotAdmin() {
        Reservation reservation = createReservation(QUEUE);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.of(reservation));
        exception.expect(AccessDeniedException.class);
        exception.expectMessage("You don't have permission for this action");
        service.updateStatus(RESERVATION_ID, USER_NAME, HANDOUT, false);
    }

    @Test
    public void handOutShouldThrowExceptionIfBookAvailableCopiesIsZero() {
        final int AVAILABLE_COPIES = 0;
        final Book book = createBook(AVAILABLE_COPIES);
        book.setId(BOOK_ID);
        final Reservation reservation = new Reservation(book, new User(), RESERVATION_DATE, HANDOUT);
        reservation.setId(RESERVATION_ID);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(
                Optional.of(reservation));
        exception.expect(LibraryException.class);
        exception.expectMessage("Book is not available");
        service.updateStatus(RESERVATION_ID, USER_NAME, HANDOUT, true);
    }

    @Test
    public void handOutShouldThrowExceptionOnRepositorySave() {
        final Reservation reservation = createReservation(CANCELED);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.of(reservation));
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenThrow(new DataAccessException("Bac!") {
        });
        exception.expect(LibraryException.class);
        exception.expectMessage("Database save error");
        service.updateStatus(RESERVATION_ID, USER_NAME, HANDOUT, true);
    }

    @Test
    public void takeInShouldUpdateReservationStatusAndAvailableBookCopies() {
        final Book book = createBook(BEFORE_TAKE_IN);
        final Reservation reservation = new Reservation(book, new User(), RESERVATION_DATE, RETURNED);
        reservation.setId(RESERVATION_ID);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(
                Optional.of(reservation));
        when(bookRepository.save(book)).thenReturn(book);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        assertEquals(RESERVATION_ID, service.updateStatus(RESERVATION_ID, USER_NAME, RETURNED, true));
        assertEquals(BEFORE_TAKE_IN + 1, book.getAvailable());
    }

    @Test
    public void takeInShouldThrowExceptionIfUserIsNotAdmin() {
        Reservation reservation = createReservation(QUEUE);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.of(reservation));
        exception.expect(AccessDeniedException.class);
        exception.expectMessage("You don't have permission for this action");
        service.updateStatus(RESERVATION_ID, USER_NAME, RETURNED, false);
    }

    @Test
    public void takeInShouldThrowExceptionIfReservationIdNotFound() {
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.empty());
        exception.expect(LibraryException.class);
        exception.expectMessage("No such reservation");
        service.updateStatus(RESERVATION_ID, USER_NAME, RETURNED, true);
    }

    @Test
    public void byParametersShouldReturnHistoryReservationList() {
        final ReservationDTO reservationDTO = createReservationDTO(RETURNED);
        final List<ReservationDTO> mockList = createReservationDTOList(reservationDTO);
        when(reservationRepository.getByParameters(BOOK_TITLE, USER_NAME, RETURNED))
                .thenReturn(mockList);
        assertEquals(mockList, service.getByParameters(BOOK_TITLE, USER_NAME, RETURNED, false));
    }

    @Test
    public void byParametersShouldReturnActiveReservationList() {
        final ReservationDTO reservationDTO = createReservationDTO(HANDOUT);
        final List<ReservationDTO> mockList = createReservationDTOList(reservationDTO);
        when(reservationRepository.getByParameters(BOOK_TITLE, USER_NAME, HANDOUT))
                .thenReturn(mockList);
        assertEquals(mockList, service.getByParameters(BOOK_TITLE, USER_NAME, HANDOUT, false));
    }

    @Test
    public void byParametersShouldReturnEmptyListIfParametersNullOrEmptyForUser() {
        exception.expect(LibraryException.class);
        exception.expectMessage("Search parameters missing");
        service.getByParameters(null, "", null, false);
    }


    @Test
    public void byParametersShouldReturnEmptyListIfParametersNullOrEmptyForAdmin() {
        exception.expect(LibraryException.class);
        exception.expectMessage("Search parameters missing");
        service.getByParameters(null, "", null, true);
    }

    @Test
    public void QueueForAdminShouldReturnReservationQueueList() {
        final ReservationDTO reservationDTO = createReservationDTO(QUEUE);
        final List<ReservationDTO> mockList = createReservationDTOList(reservationDTO);
        when(reservationRepository.getByParameters(null, null, QUEUE)).thenReturn(mockList);
        assertEquals(mockList, service.getByParameters(null, null, QUEUE, true));
    }

    ///Auxiliary methods

    private ReservationDTO createReservationDTO(String status) {
        final ReservationDTO reservationDTO = new ReservationDTO(RESERVATION_ID, BOOK_ID, BOOK_TITLE,
                USER_ID, USER_NAME, RESERVATION_DATE, status);
        return reservationDTO;
    }

    private List<ReservationDTO> createReservationDTOList(ReservationDTO reservationDTO) {
        final List<ReservationDTO> reservationDTOList = new ArrayList<>();
        reservationDTOList.add(reservationDTO);
        return reservationDTOList;
    }

    private Book createBook(int available) {
        final Author author = new Author();
        return new Book(BOOK_TITLE, author, GENRE, INITIAL_COPIES, available, false);
    }

    private User createTestUser() {
        final User user = new User();
        user.setId(USER_ID);
        user.setUserName(USER_NAME);
        return user;
    }

    private Reservation createReservation(String status) {
        final Reservation reservation = new Reservation();
        reservation.setId(RESERVATION_ID);
        reservation.setBook(createBook(BEFORE_TAKE_IN));
        reservation.setUser(createTestUser());
        reservation.setStatus(status);
        return reservation;
    }
}