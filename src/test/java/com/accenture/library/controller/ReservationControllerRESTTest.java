package com.accenture.library.controller;

import com.accenture.library.domain.Author;
import com.accenture.library.domain.Book;
import com.accenture.library.domain.User;
import com.accenture.library.dto.ReservationDTO;
import com.accenture.library.service.reservationSrv.ReservationSrvImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private static final Date RESERVATION_DATE = Calendar.getInstance().getTime();
    private static final String GENRE = "NOVEL";
    private static final int INITIAL_COPIES = 3;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ReservationSrvImpl service;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = USER_NAME, password = "janis000", authorities = "USER")
    @Test
    public void shouldReturnUserActiveReservations() throws Exception {
        final String requestBody = "{\"handOut\": \"true\",\"returned\": \"false\"}";
        final List<ReservationDTO> mockList = createReservationDTOList(createReservationDTO(true, false));
        when(service.getByParameters(null, USER_NAME, true, false)).thenReturn(mockList);
        mvc.perform(post("/api/v1/reservations/user/queue")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(mockList)));
    }

    @WithMockUser(username = USER_NAME, password = "janis000", authorities = "USER")
    @Test
    public void shouldReturnUserReturnedReservations() throws Exception {
        final String requestBody = "{\"handOut\": \"true\",\"returned\": \"false\"}";
        final List<ReservationDTO> mockList = createReservationDTOList(createReservationDTO(true, true));
        ReservationDTO r = createReservationDTO(true, false);
        when(service.getByParameters(null, USER_NAME, true, false)).thenReturn(mockList);
        mvc.perform(post("/api/v1/reservations/user/queue")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(mockList)));
    }

    @WithMockUser(username = USER_NAME, password = "janis000", authorities = "USER")
    @Test
    public void shouldReturnUserReservationQueue() throws Exception {
        final String requestBody = "{\"handOut\": \"true\",\"returned\": \"false\"}";
        final List<ReservationDTO> mockList = createReservationDTOList(createReservationDTO(false, false));
        ReservationDTO r = createReservationDTO(true, false);
        when(service.getByParameters(null, USER_NAME, true, false)).thenReturn(mockList);
        mvc.perform(post("/api/v1/reservations/user/queue")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(mockList)));
    }

    @WithMockUser(username = USER_NAME, password = "janis000", authorities = "USER")
    @Test
    public void shouldAddUserReservation() throws Exception {
        final ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(RESERVATION_ID);
        reservationDTO.setBookId(BOOK_ID);
        final ReservationDTO responseReservationDTO=new ReservationDTO();
        responseReservationDTO.setId(RESERVATION_ID);
        responseReservationDTO.setBookTitle(BOOK_TITLE);
        responseReservationDTO.setReservationDate(RESERVATION_DATE);
        final String requestBody = "{\"bookId\": " + BOOK_ID + "}";
        when(service.makeReservation(BOOK_ID, USER_NAME)).thenReturn(responseReservationDTO);
        mvc.perform(post("/api/v1/reservations/user/make-reservation")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string(mapper.writeValueAsString(responseReservationDTO)));
    }

    @WithMockUser(username = USER_NAME, password = "janis000", authorities = "ADMIN")
    @Test
    public void shouldReturnAllUserReservationQueue() throws Exception {
        final List<ReservationDTO> mockList = createReservationDTOList(createReservationDTO(false, false));
        ReservationDTO r = createReservationDTO(true, false);
        when(service.getReservationQueue()).thenReturn(mockList);
        mvc.perform(get("/api/v1/reservations/admin/queue"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(mockList)));
    }

    @WithMockUser(username = USER_NAME, password = "janis000", authorities = "ADMIN")
    @Test
    public void shouldHandOutReservation() throws Exception {
        final ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(RESERVATION_ID);
        final String requestBody = "{\"id\": " + RESERVATION_ID + "}";
        when(service.handOut(RESERVATION_ID)).thenReturn(RESERVATION_ID);
        mvc.perform(post("/api/v1/reservations/admin/hand-out")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string(mapper.writeValueAsString(reservationDTO)));
    }

    @WithMockUser(username = USER_NAME, password = "janis000", authorities = "ADMIN")
    @Test
    public void shouldTakeInReservation() throws Exception {
        final ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(RESERVATION_ID);
        final String requestBody = "{\"id\": " + RESERVATION_ID + "}";
        when(service.handOut(RESERVATION_ID)).thenReturn(RESERVATION_ID);
        mvc.perform(post("/api/v1/reservations/admin/take-in")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(reservationDTO)));
    }

    @WithMockUser(username = USER_NAME, password = "janis000", authorities = "ADMIN")
    @Test
    public void shouldReturnUserActiveReservationsByParameters() throws Exception {
        final String requestBody = "{\"handOut\": \"true\",\"returned\": \"false\"}";
        final List<ReservationDTO> mockList = createReservationDTOList(createReservationDTO(true, false));
        when(service.getByParameters(null, null, true, false)).thenReturn(mockList);
        mvc.perform(post("/api/v1/reservations/admin/search")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(mockList)));
    }

    @WithMockUser(username = USER_NAME, password = "janis000", authorities = "ADMIN")
    @Test
    public void shouldReturnUserReturnedReservationsByParameters() throws Exception {
        final String requestBody = "{\"handOut\": \"true\",\"returned\": \"true\"}";
        final List<ReservationDTO> mockList = createReservationDTOList(createReservationDTO(true, true));
        when(service.getByParameters(null, null, true, true)).thenReturn(mockList);
        mvc.perform(post("/api/v1/reservations/admin/search")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(mockList)));
    }


    ///Auxiliary methods

    private ReservationDTO createReservationDTO(boolean handOut, boolean returned) {
        final ReservationDTO reservationDTO = new ReservationDTO(RESERVATION_ID, BOOK_ID, BOOK_TITLE,
                USER_ID, USER_NAME, RESERVATION_DATE, handOut, returned, false);
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