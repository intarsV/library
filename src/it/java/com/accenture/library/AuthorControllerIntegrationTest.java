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
public class AuthorControllerIntegrationTest {

    private static final int ID = 1;
    private static final String AUTHOR_NAME = "Janka";
    private static final String URL_TEMPLATE = "/api/v1/authors";

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
    public void getAllAuthors() throws Exception {
        mvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Rainis")));
    }

    //Only for ADMIN user
    @WithMockUser(username = "karlis", password = "karlis000", authorities = "ADMIN")
    @Test
    public void shouldSaveAuthor() throws Exception {
        final String requestBody = "{\"name\": \"" + AUTHOR_NAME + "\"}";
        mvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(4)));
    }

    @WithMockUser(username = "janis", password = "janis000", authorities = "USER")
    @Test
    public void shouldReturnUnauthorisedRequestSaveAuthor() throws Exception {
        final String requestBody = "{\"name\": " + AUTHOR_NAME + "\"}";
        mvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().is(403));
    }

    //Only for ADMIN user
    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "ADMIN")
    @Test
    public void shouldDisableAuthor() throws Exception {
        final String requestBody = "{\"id\":\"" + ID + "\"}";
        mvc.perform(put(URL_TEMPLATE + "/" + ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.enabled", is(false)));
    }

    //Only for ADMIN user
    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "User")
    @Test
    public void shouldReturnExceptionDisableAuthor() throws Exception {
        final String requestBody = "{\"id\":\"" + ID + "\"}";
        mvc.perform(put(URL_TEMPLATE + "/" + ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().is(403));
    }
}
