package com.accenture.library.controller;

import com.accenture.library.domain.Author;
import com.accenture.library.dto.AuthorDTO;
import com.accenture.library.service.authorSrv.AuthorSrvImpl;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
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
public class AuthorControllerRESTTest {

    private static final Long ID = 1L;
    private static final String AUTHOR_NAME = "Janka";

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AuthorSrvImpl service;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    //For ANY user
    @WithMockUser(username="vilnis")
    @Test
    public void shouldReturnAuthorDTOList() throws Exception {
        List<AuthorDTO> mockList = createList();
        when(service.authorList()).thenReturn(mockList);
        mvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id': 1,'name':'Janka','deleted': false}]"));
    }

    //Only for ADMIN user
    @WithMockUser(username = "initex", password = "initex000", authorities = "ADMIN")
    @Test
    public void shouldSaveAuthor ()throws Exception{
        String requestBody = "{\"name\": \"Janka\"}";
        when(service.saveAuthor("Janka")).thenReturn(ID);
        mvc.perform(post("/api/v1/authors")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().json("{'id': 1,'name':'Janka','deleted': false}"));
    }


    @WithMockUser(username = "ivars", password = "ivars000", authorities = "USER")
    @Test
    public void shouldReturnUnauthorisedRequestSaveAuthor() throws Exception {
        String requestBody = "{\"name\": \"Janka\"}";
        when(service.saveAuthor("Janka")).thenReturn(ID);
        mvc.perform(post("/api/v1/authors")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().is(403));
    }

    //Auxiliary methods

    private AuthorDTO createDTO() {
        return new AuthorDTO(ID, AUTHOR_NAME, false);
    }

    private List<AuthorDTO> createList(){
        List<AuthorDTO> list=new ArrayList<>();
        list.add(createDTO());
        return list;
    }

    private Author createAuthor() {
        return new Author( ID, AUTHOR_NAME, false);
    }
}