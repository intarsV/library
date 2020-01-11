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

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {LibraryApplication.class, SpringSecurityConfiguration.class})
@ActiveProfiles(value = "integration")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookControllerRESTIntegrationTest {

    private static final int ID = 1;
    private static final int NEW_ID = 4;
    private static final String TITLE = "Zelta zirgs";
    private static final String NEW_TITLE = "Good book";
    private static final String AUTHOR_NAME = "Rainis";
    private static final String GENRE = "POETRY";
    private static final int COPIES = 7;
    private static final String URL_TEMPLATE = "/api/v1/books";

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

    //For ANY user
    @Test
    public void shouldReturnAllBookList() throws Exception {
        mvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(ID)))
                .andExpect(jsonPath("$[0].title", is(TITLE)));
    }

    //For ANY user
    @Test
    public void shouldReturnBookListByParameters() throws Exception {
        mvc.perform(get(URL_TEMPLATE + "?title=" + TITLE + "&authorName=" + AUTHOR_NAME + "&genre=" + GENRE)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((user + ":" + userPassword).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(ID)))
                .andExpect(jsonPath("$[0].title", is(TITLE)))
                .andExpect(jsonPath("$[0].authorName", is(AUTHOR_NAME)))
                .andExpect(jsonPath("$[0].genre", is(GENRE)));
    }

    //Only for ADMIN user
    @Test
    public void shouldSaveBook() throws Exception {
        final String requestBody = "{\"title\": \"" + NEW_TITLE + "\",\"authorName\": \"" + AUTHOR_NAME
                + "\",\"genre\": \"" + GENRE + "\", \"copies\":" + COPIES + "}";
        mvc.perform(post(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((admin + ":" + adminPassword).getBytes()))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(NEW_ID)))
                .andExpect(jsonPath("$.title", is(NEW_TITLE)))
                .andExpect(jsonPath("$.authorName", is(AUTHOR_NAME)))
                .andExpect(jsonPath("$.genre", is(GENRE)))
                .andExpect(jsonPath("$.copies", is(COPIES)));
    }

    //Only for ADMIN user
    @Test
    public void shouldReturnExceptionSaveBook() throws Exception {
        final String requestBody = "{\"title\": \"" + TITLE + "\",\"authorName\": \"" + AUTHOR_NAME
                + "\",\"genre\": \"" + GENRE + "\", \"copies\":" + COPIES + "}";
        mvc.perform(post(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((user + ":" + userPassword).getBytes()))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().is(403));
    }

    //Only for ADMIN user
    @Test
    public void shouldDisableBook() throws Exception {
        final String requestBody = "{\"id\":\"" + ID + "\"}";
        mvc.perform(put(URL_TEMPLATE + "/" + ID)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((admin + ":" + adminPassword).getBytes()))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.enabled", is(false)));
    }

    //Only for ADMIN user
    @Test
    public void shouldReturnExceptionDeleteBook() throws Exception {
        final String requestBody = "{\"id\":\"" + ID + "\"}";
        mvc.perform(put(URL_TEMPLATE + "/" + ID)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((user + ":" + userPassword).getBytes()))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().is(403));
    }
}