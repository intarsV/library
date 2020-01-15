package com.accenture.library;

import com.accenture.library.config.SpringSecurityConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {LibraryApplication.class, SpringSecurityConfiguration.class})
@ActiveProfiles(value = "integration")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationControllerIT {

    private static final int RESERVATION_ID = 2;
    private static final Long BOOK_ID = 2L;
    private static final String BOOK_TITLE = "GoodBook";
    private static final int USER_ID = 2;
    private static final String USER_NAME = "ivars";
    private static final String PASSWORD = "ivars000";
    private static final Date RESERVATION_DATE = Calendar.getInstance().getTime();
    private static final String GENRE = "NOVEL";
    private static final int INITIAL_COPIES = 3;
    private static final String QUEUE = "QUEUE";
    private static final String HANDOUT = "HANDOUT";
    private static final String RETURNED = "RETURNED";
    private static final String CANCELED = "CANCELED";

    @Value("${admin}")
    private String admin;
    @Value("${adminPassword}")
    private String adminPassword;
    @Value("${user}")
    private String user;
    @Value("${userPassword}")
    private String userPassword;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldReturnUserActiveReservations() throws Exception {
        final String requestBody = "{\"status\": \"" + HANDOUT + "\"}";
        mvc.perform(get("/api/v1/reservations?status=" + HANDOUT)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((user + ":" + userPassword).getBytes()))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(RESERVATION_ID)))
                .andExpect(jsonPath("$[0].status", is(HANDOUT)));
    }

    @Test
    public void shouldReturnUserReturnedReservations() throws Exception {
        final String requestBody = "{\"status\": \"" + RETURNED + "\"}";
        mvc.perform(get("/api/v1/reservations?status=" + RETURNED)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((user + ":" + userPassword).getBytes()))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is(RETURNED)));
    }

    @Test
    public void shouldReturnUserReservationQueue() throws Exception {
        final String requestBody = "{\"status\": \"" + QUEUE + "\"}";
        mvc.perform(get("/api/v1/reservations?status=" + QUEUE)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((user + ":" + userPassword).getBytes()))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[0].status", is(QUEUE)));
    }

    @Test
    public void shouldCancelUserReservation() throws Exception {
        final String requestBody = "{\"id\": " + 4 + ", \"status\": \"" + CANCELED + "\"}";
        mvc.perform(put("/api/v1/reservations/" + 3)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((user + ":" + userPassword).getBytes()))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(content().string("3"));
    }

    @Test
    public void shouldAddUserReservation() throws Exception {
        final String requestBody = "{\"bookId\": " + BOOK_ID + "}";
        mvc.perform(post("/api/v1/reservations")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((user + ":" + userPassword).getBytes()))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.status", is(QUEUE)));
    }

    @Test
    public void shouldReturnAllUserReservationQueue() throws Exception {
        final String requestBody = "{\"status\": \"" + QUEUE + "\"}";
        mvc.perform(get("/api/v1/reservations?status=" + QUEUE)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((admin + ":" + adminPassword).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[0].status", is(QUEUE)));
    }

    @Test
    public void shouldHandOutReservation() throws Exception {
        final String requestBody = "{\"id\": " + 2 + ", \"status\": \"" + HANDOUT + "\"}";
        mvc.perform(put("/api/v1/reservations/" + 2)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((admin + ":" + adminPassword).getBytes()))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(content().string("2"));
    }

    @Test
    public void shouldTakeInReservation() throws Exception {
        final String requestBody = "{\"id\": " + 2 + ", \"status\": \"" + RETURNED + "\"}";
        mvc.perform(put("/api/v1/reservations/" + 2)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((admin + ":" + adminPassword).getBytes()))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(content().string("2"));
    }
}