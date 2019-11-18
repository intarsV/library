package com.accenture.library.controller;

import com.accenture.library.domain.Author;
import com.accenture.library.dto.AuthorDTO;
import com.accenture.library.service.author.AuthorServiceImpl;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerRESTTest {

    private static final Long ID = 1L;
    private static final String AUTHOR_NAME = "Janka";

    @Autowired
    private ObjectMapper mapper;

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

    //For ANY user
    @WithMockUser(username = "vilnis")
    @Test
    public void shouldReturnAuthorDTOList() throws Exception {
        final List<AuthorDTO> mockList = createAuthorDTOList();
        when(service.authorList()).thenReturn(mockList);
        mvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(mockList))));
    }

    //Only for ADMIN user
    @WithMockUser(username = "karlis", password = "karlis000", authorities = "ADMIN")
    @Test
    public void shouldSaveAuthor() throws Exception {
        final AuthorDTO authorDTO = createAuthorDTO();
        final String requestBody = "{\"name\": \"" + AUTHOR_NAME + "\"}";
        when(service.saveAuthor(any(AuthorDTO.class))).thenReturn(authorDTO);
        mvc.perform(post("/api/v1/authors")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string((mapper.writeValueAsString(authorDTO))));
    }

    @WithMockUser(username = "janis", password = "janis000", authorities = "USER")
    @Test
    public void shouldReturnUnauthorisedRequestSaveAuthor() throws Exception {
        final AuthorDTO authorDTO = createAuthorDTO();
        final String requestBody = "{\"name\": " + AUTHOR_NAME + "\"}";
        when(service.saveAuthor(any(AuthorDTO.class))).thenReturn(authorDTO);
        mvc.perform(post("/api/v1/authors")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().is(403));
    }

    //Only for ADMIN user
    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "ADMIN")
    @Test
    public void shouldDisableAuthor() throws Exception {
        final AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(ID);
        authorDTO.setEnabled(false);
        final String requestBody = "{\"id\":\"" + ID + "\"}";
        when(service.disableAuthor(ID)).thenReturn(false);
        mvc.perform(put("/api/v1/authors/"+ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(content().string((mapper.writeValueAsString(authorDTO))));
    }

    //Only for ADMIN user
    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "User")
    @Test
    public void shouldReturnExceptionDisableAuthor() throws Exception {
        final String requestBody = "{\"id\":\"" + ID + "\"}";
        mvc.perform(put("/api/v1/authors/"+ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().is(403));
    }

    //Auxiliary methods

    private AuthorDTO createAuthorDTO() {
        return new AuthorDTO(ID, AUTHOR_NAME, true);
    }

    private List<AuthorDTO> createAuthorDTOList() {
        final List<AuthorDTO> list = new ArrayList<>();
        list.add(createAuthorDTO());
        return list;
    }

    private Author createAuthor() {
        return new Author(ID, AUTHOR_NAME, true);
    }
}