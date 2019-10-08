package com.accenture.library.service.reservationSrv;

import com.accenture.library.dto.ReservationDTO;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.ReservationRepository;
import com.accenture.library.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationSrvImplTest {

    private static final Long ID = 1L;
    private static final Long BOOK_ID = 2L;
    private static final String BOOK_TITLE = "GoodBook";
    private static final Long USER_ID = 3L;
    private static final String USER_NAME = "Jānis";
    private static final Date RESERVATION_DATE = Calendar.getInstance().getTime();

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    ReservationSrvImpl service;

    @Test
    public void shouldReturnReservationQueueForAdmin(){
        ReservationDTO reservationDTO=createReservationDTO(ID, BOOK_ID, BOOK_TITLE, USER_ID, USER_NAME, RESERVATION_DATE, false, false, false);
        List<ReservationDTO> mockList=reservationDTOList(reservationDTO);
        when(reservationRepository.getQueue()).thenReturn(mockList);
        Assert.assertEquals(mockList, service.getReservationQueue());
    }

    ///Auxiliary methods

    private ReservationDTO createReservationDTO(Long id, Long bookId, String bookTitle, Long userId, String userName, Date date, boolean handOut, boolean returned, boolean deleted) {
        final ReservationDTO reservationDTO = new ReservationDTO(id, bookId, bookTitle, userId, userName, date, handOut, returned, deleted);
        return reservationDTO;
    }

    private List<ReservationDTO> reservationDTOList(ReservationDTO reservationDTO) {
        final List<ReservationDTO> reservationDTOList = new ArrayList<>();
        reservationDTOList.add(reservationDTO);
        return reservationDTOList;
    }

}