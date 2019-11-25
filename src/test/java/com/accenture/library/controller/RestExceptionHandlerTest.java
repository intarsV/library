package com.accenture.library.controller;

import com.accenture.library.dto.AuthorDTO;
import com.accenture.library.exceptions.LibraryException;
import com.accenture.library.service.author.AuthorServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestExceptionHandlerTest {

    private static final Long ID = 1L;
    private static final String AUTHOR_NAME = "Janka";
    private static final String DATABASE_SAVE_ERROR = "Database save error";
    private static final String BAD_REQUEST_ERROR = "Any service level error";

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AuthorServiceImpl service;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @WithMockUser(username = "karlis", password = "karlis000", authorities = "ADMIN")
    @Test
    public void shouldReturnEntityWithInternalServerErrorStatusOnSaveAuthor() throws Exception {
        final AuthorDTO authorDTO = createAuthorDTO();
        final String requestBody = "{\"name\": \"" + AUTHOR_NAME + "\"}";
        when(service.addAuthor(any(AuthorDTO.class))).thenThrow(new LibraryException(DATABASE_SAVE_ERROR));
        mvc.perform(post("/api/v1/authors")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isInternalServerError());

    }

    @WithMockUser(username = "karlis", password = "karlis000", authorities = "ADMIN")
    @Test
    public void shouldReturnEntityWithBadRequestErrorStatusOnDisableAuthor() throws Exception {
        final String requestBody = "{\"name\": \"" + AUTHOR_NAME + "\"}";
        when(service.disableAuthor(ID)).thenThrow(new LibraryException(BAD_REQUEST_ERROR));
        mvc.perform(put("/api/v1/authors/" + ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest());

    }

    //Auxiliary methods

    private AuthorDTO createAuthorDTO() {
        return new AuthorDTO(ID, AUTHOR_NAME, true);
    }

}