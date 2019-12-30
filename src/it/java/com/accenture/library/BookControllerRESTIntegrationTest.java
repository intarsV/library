package com.accenture.library;

import com.accenture.library.config.SpringSecurityConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {LibraryApplication.class, SpringSecurityConfiguration.class})
@TestPropertySource(locations = "/testApplication.properties")
@TestConfiguration
public class BookControllerRESTIntegrationTest {

    private static final int ID = 1;
    private static final int NEW_ID = 4;
    private static final String TITLE = "Zelta zirgs";
    private static final String NEW_TITLE = "Good book";
    private static final String AUTHOR_NAME = "Rainis";
    private static final String GENRE = "POETRY";
    private static final int COPIES = 7;
    private static final String URL_TEMPLATE = "/api/v1/books";

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
    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "USER")
    @Test
    public void shouldReturnBookListByParameters() throws Exception {
        mvc.perform(get(URL_TEMPLATE + "?title=" + TITLE + "&authorName=" + AUTHOR_NAME + "&genre=" + GENRE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(ID)))
                .andExpect(jsonPath("$[0].title", is(TITLE)))
                .andExpect(jsonPath("$[0].authorName", is(AUTHOR_NAME)))
                .andExpect(jsonPath("$[0].genre", is(GENRE)));
    }

    //Only for ADMIN user
    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "ADMIN")
    @Test
    public void shouldSaveBook() throws Exception {
        final String requestBody = "{\"title\": \"" + NEW_TITLE + "\",\"authorName\": \"" + AUTHOR_NAME
                + "\",\"genre\": \"" + GENRE + "\", \"copies\":" + COPIES + "}";
        mvc.perform(post(URL_TEMPLATE)
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
    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "USER")
    @Test
    public void shouldReturnExceptionSaveBook() throws Exception {
        final String requestBody = "{\"title\": \"" + TITLE + "\",\"authorName\": \"" + AUTHOR_NAME
                + "\",\"genre\": \"" + GENRE + "\", \"copies\":" + COPIES + "}";
        mvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().is(403));
    }

    //Only for ADMIN user
    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "ADMIN")
    @Test
    public void shouldDisableBook() throws Exception {
        final String requestBody = "{\"id\":\"" + ID + "\"}";
        mvc.perform(put(URL_TEMPLATE + "/" + ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.enabled", is(false)));
    }

    //Only for ADMIN user
    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "USER")
    @Test
    public void shouldReturnExceptionDeleteBook() throws Exception {
        final String requestBody = "{\"id\":\"" + ID + "\"}";
        mvc.perform(put(URL_TEMPLATE + "/" + ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().is(403));
    }
}