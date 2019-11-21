package com.accenture.library.controller;

import com.accenture.library.domain.Author;
import com.accenture.library.domain.Book;
import com.accenture.library.domain.User;
import com.accenture.library.dto.ReservationDTO;
import com.accenture.library.service.reservation.ReservationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerRESTTest {

    private static final Long RESERVATION_ID = 1L;
    private static final Long BOOK_ID = 2L;
    private static final String BOOK_TITLE = "GoodBook";
    private static final Long USER_ID = 3L;
    private static final String USER_NAME = "Jānis";
    private static final String PASSWORD = "janis000";
    private static final Date RESERVATION_DATE = Calendar.getInstance().getTime();
    private static final String GENRE = "NOVEL";
    private static final int INITIAL_COPIES = 3;
    private static final String QUEUE = "QUEUE";
    private static final String HANDOUT = "HANDOUT";
    private static final String RETURNED = "RETURNED";
    private static final String CANCELED = "CANCELED";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ReservationServiceImpl service;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "USER")
    @Test
    public void shouldReturnUserActiveReservations() throws Exception {
        final String requestBody = "{\"status\": \"" + HANDOUT + "\"}";
        final List<ReservationDTO> mockList = createReservationDTOList(createReservationDTO(HANDOUT));
        when(service.getByParameters(null, USER_NAME, HANDOUT, false)).thenReturn(mockList);
        mvc.perform(get("/api/v1/reservations?status=" + HANDOUT)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(mockList)));
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "USER")
    @Test
    public void shouldReturnUserReturnedReservations() throws Exception {
        final String requestBody = "{\"status\": \"" + RETURNED + "\"}";
        final List<ReservationDTO> mockList = createReservationDTOList(createReservationDTO(RETURNED));
        when(service.getByParameters(null, USER_NAME, RETURNED, false)).thenReturn(mockList);
        mvc.perform(get("/api/v1/reservations?status=" + RETURNED)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(mockList)));
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "USER")
    @Test
    public void shouldReturnUserReservationQueue() throws Exception {
        final String requestBody = "{\"status\": \"" + QUEUE + "\"}";
        final List<ReservationDTO> mockList = createReservationDTOList(createReservationDTO(QUEUE));
        when(service.getByParameters(null, USER_NAME, QUEUE, false)).thenReturn(mockList);
        mvc.perform(get("/api/v1/reservations?status=" + QUEUE)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(mockList)));
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "USER")
    @Test
    public void shouldCancelUserReservation() throws Exception {
        final String requestBody = "{\"id\": " + RESERVATION_ID + ", \"status\": \"" + CANCELED + "\"}";
        when(service.updateStatus(RESERVATION_ID, USER_NAME, CANCELED, false)).thenReturn(RESERVATION_ID);
        mvc.perform(put("/api/v1/reservations/" + RESERVATION_ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(content().string(mapper.writeValueAsString(RESERVATION_ID)));
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "USER")
    @Test
    public void shouldAddUserReservation() throws Exception {
        final ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(RESERVATION_ID);
        reservationDTO.setBookId(BOOK_ID);
        final ReservationDTO responseReservationDTO = new ReservationDTO();
        responseReservationDTO.setId(RESERVATION_ID);
        responseReservationDTO.setBookTitle(BOOK_TITLE);
        responseReservationDTO.setReservationDate(RESERVATION_DATE);
        final String requestBody = "{\"bookId\": " + BOOK_ID + "}";
        when(service.makeReservation(BOOK_ID, USER_NAME)).thenReturn(responseReservationDTO);
        mvc.perform(post("/api/v1/reservations")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string(mapper.writeValueAsString(responseReservationDTO)));
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "ADMIN")
    @Test
    public void shouldReturnAllUserReservationQueue() throws Exception {
        final String requestBody = "{\"status\": \"" + QUEUE + "\"}";
        final List<ReservationDTO> mockList = createReservationDTOList(createReservationDTO(QUEUE));
        when(service.getByParameters(null, null, QUEUE, true)).thenReturn(mockList);
        mvc.perform(get("/api/v1/reservations?status=" + QUEUE))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(mockList)));
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "ADMIN")
    @Test
    public void shouldHandOutReservation() throws Exception {
        final String requestBody = "{\"id\": " + RESERVATION_ID + ", \"status\": \"" + HANDOUT + "\"}";
        when(service.updateStatus(RESERVATION_ID, USER_NAME, HANDOUT, true)).thenReturn(RESERVATION_ID);
        mvc.perform(put("/api/v1/reservations/" + RESERVATION_ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(content().string(mapper.writeValueAsString(RESERVATION_ID)));
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "ADMIN")
    @Test
    public void shouldTakeInReservation() throws Exception {
        final String requestBody = "{\"id\": " + RESERVATION_ID + ", \"status\": \"" + RETURNED + "\"}";
        when(service.updateStatus(RESERVATION_ID, USER_NAME, RETURNED, true)).thenReturn(RESERVATION_ID);
        mvc.perform(put("/api/v1/reservations/" + RESERVATION_ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(content().string(mapper.writeValueAsString(RESERVATION_ID)));
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
        Author author = new Author();
        return new Book(BOOK_TITLE, author, GENRE, INITIAL_COPIES, available, true);
    }

    private User createTestUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setUserName(USER_NAME);
        return user;
    }
}