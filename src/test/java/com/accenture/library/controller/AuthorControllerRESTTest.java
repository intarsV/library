package com.accenture.library.controller;


import com.accenture.library.config.SpringSecurityConfiguration;
import com.accenture.library.dto.AuthorDTO;
import com.accenture.library.service.author.AuthorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthorControllerREST.class)
@Import(SpringSecurityConfiguration.class)
public class AuthorControllerRESTTest {

    private static final Long ID = 1L;
    private static final String AUTHOR_NAME = "Janka";
    private static final String URL_TEMPLATE = "/api/v1/authors";

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private AuthorServiceImpl service;

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldReturnAuthorDTOList() throws Exception {
        final List<AuthorDTO> mockList = createAuthorDTOList();
        when(service.authorList()).thenReturn(mockList);
        mvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(mockList))));
    }

    //Only for ADMIN user
    @WithMockUser(username = "karlis", password = "karlis000", authorities = "ADMIN")
    @Test
    public void shouldSaveAuthor() throws Exception {
        final AuthorDTO authorDTO = createAuthorDTO();
        final String requestBody = "{\"name\": \"" + AUTHOR_NAME + "\"}";
        when(service.addAuthor(any(AuthorDTO.class))).thenReturn(authorDTO);
        mvc.perform(post(URL_TEMPLATE)
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
        when(service.addAuthor(any(AuthorDTO.class))).thenReturn(authorDTO);
        mvc.perform(post(URL_TEMPLATE)
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
        mvc.perform(put(URL_TEMPLATE + "/" + ID)
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
        mvc.perform(put(URL_TEMPLATE + "/" + ID)
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
}