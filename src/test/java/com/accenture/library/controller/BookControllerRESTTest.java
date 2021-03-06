package com.accenture.library.controller;

import com.accenture.library.config.SpringSecurityConfiguration;
import com.accenture.library.dto.BookDTO;
import com.accenture.library.service.book.BookServiceImpl;
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
@WebMvcTest(controllers = BookControllerREST.class)
@Import(SpringSecurityConfiguration.class)
public class BookControllerRESTTest {

    private static final Long ID = 1L;
    private static final String TITLE = "Very good book";
    private static final String AUTHOR_NAME = "Janka";
    private static final String GENRE = "NOVEL";
    private static final int COPIES = 1;
    private static final int AVAILABLE = 1;
    private static final boolean ENABLED = true;
    private static final String URL_TEMPLATE = "/api/v1/books";

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private BookServiceImpl service;

    @Autowired
    private MockMvc mvc;

    //For ANY user
    @WithMockUser(username = "vilnis")
    @Test
    public void shouldReturnAllBookList() throws Exception {
        final List<BookDTO> mockList = createList();
        when(service.getByParameters(null, null, null)).thenReturn(mockList);
        mvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(mockList))));
    }

    //For ANY user
    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "USER")
    @Test
    public void shouldReturnBookListByParameters() throws Exception {
        final List<BookDTO> mockList = createList();
        when(service.getByParameters(TITLE, AUTHOR_NAME, GENRE)).thenReturn(mockList);
        mvc.perform(get(URL_TEMPLATE + "?title=" + TITLE + "&authorName=" + AUTHOR_NAME + "&genre=" + GENRE))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(mockList))));
    }

    //Only for ADMIN user
    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "ADMIN")
    @Test
    public void shouldSaveBook() throws Exception {
        final BookDTO bookDTO = createBookDTO();

        final String requestBody = "{\"title\": \"" + TITLE + "\",\"authorName\": \"" + AUTHOR_NAME
                + "\",\"genre\": \"" + GENRE + "\", \"copies\":" + COPIES + "}";

        when(service.addBook(any(BookDTO.class))).thenReturn(bookDTO);
        mvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string(mapper.writeValueAsString(bookDTO)));
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
        final BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setEnabled(false);
        final String requestBody = "{\"id\":\"" + ID + "\"}";
        when(service.disableBook(ID)).thenReturn(false);
        mvc.perform(put(URL_TEMPLATE + "/" + ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(content().string(mapper.writeValueAsString(bookDTO)));
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

    ///Auxiliary methods

    private BookDTO createBookDTO() {
        return new BookDTO(ID, TITLE, AUTHOR_NAME, GENRE, COPIES, AVAILABLE, ENABLED);
    }

    private List<BookDTO> createList() {
        final List<BookDTO> list = new ArrayList<>();
        list.add(createBookDTO());
        return list;
    }
}