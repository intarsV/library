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
public class UserControllerRESTIntegrationTest {

    private static final int USER_ID = 3;
    private static final String USER_NAME = "Jānis";
    private static final int EXISTING_USER_ID = 2;
    private static final String EXISTING_USER_NAME = "ivars";
    private static final String PASSWORD = "xxx";
    private static final String ENCODED_USERNAME = "secretJanis";
    private static final String ENCODED_PASSWORD = "secretPassword";
    private static final String URL_TEMPLATE = "/api/v1/users";

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
    public void shouldAddNewUser() throws Exception {
        final String requestBody = "{\"userName\": \"" + ENCODED_USERNAME + "\",\"password\": \""
                + ENCODED_PASSWORD + "\"}";
        mvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(USER_ID)));
    }

    //For ADMIN user
    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "ADMIN")
    @Test
    public void shouldReturnUserResponseDTOList() throws Exception {
        mvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(EXISTING_USER_ID)))
                .andExpect(jsonPath("$[0].userName", is(EXISTING_USER_NAME)));
    }

    //For ADMIN user
    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "USER")
    @Test
    public void shouldReturnExceptionOnGetUser() throws Exception {
        mvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "ADMIN")
    @Test
    public void shouldEnableOrDisableUser() throws Exception {
        final String requestBody = "{\"id\": \"" + 2 + "\",\"enabled\": \""
                + false + "\"}";
        mvc.perform(put(URL_TEMPLATE + "/" + 2)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.enabled", is(false)));
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "USER")
    @Test
    public void shouldReturnExceptionOnUserDisable() throws Exception {
        final String requestBody = "{\"id\": \"" + USER_ID + "\",\"enabled\": \""
                + false + "\"}";
        mvc.perform(put(URL_TEMPLATE + "/" + USER_ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isForbidden());
    }
}