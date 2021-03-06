package com.accenture.library.controller;

import com.accenture.library.config.SpringSecurityConfiguration;
import com.accenture.library.dto.UserResponseDTO;
import com.accenture.library.service.user.UserServiceImpl;
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

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserControllerREST.class)
@Import(SpringSecurityConfiguration.class)
public class UserControllerRESTTest {

    private static final Long USER_ID = 3L;
    private static final String USER_NAME = "Jānis";
    private static final String PASSWORD = "xxx";
    private static final boolean ENABLED = true;
    private static final String ENCODED_USERNAME = "secretJanis";
    private static final String ENCODED_PASSWORD = "secretPassword";
    private static final String URL_TEMPLATE = "/api/v1/users";

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private UserServiceImpl service;

    @Autowired
    private MockMvc mvc;


    //For ANY user
    @Test
    public void shouldAddNewUser() throws Exception {
        final UserResponseDTO userResponseDTO = createUserResponseDTO();
        final String requestBody = "{\"userName\": \"" + ENCODED_USERNAME + "\",\"password\": \""
                + ENCODED_PASSWORD + "\"}";
        when(service.addUser(ENCODED_USERNAME, ENCODED_PASSWORD)).thenReturn(userResponseDTO);
        mvc.perform(post(URL_TEMPLATE)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string((mapper.writeValueAsString(userResponseDTO))));
    }

    //For ADMIN user
    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "ADMIN")
    @Test
    public void shouldReturnUserResponseDTOList() throws Exception {
        final List<UserResponseDTO> mockList = createResponseList();
        when(service.getUsers()).thenReturn(mockList);
        mvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(mockList))));
    }

    //For ADMIN user
    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "USER")
    @Test
    public void shouldReturnExceptionOnGetUser() throws Exception {
        final List<UserResponseDTO> mockList = createResponseList();
        when(service.getUsers()).thenReturn(mockList);
        mvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "ADMIN")
    @Test
    public void shouldEnableOrDisableUser() throws Exception {
        final UserResponseDTO userResponseDTO = createUserResponseDTO();
        final String requestBody = "{\"id\": \"" + USER_ID + "\",\"enabled\": \""
                + true + "\"}";
        when(service.enableDisableUser(USER_ID)).thenReturn(userResponseDTO);
        mvc.perform(put(URL_TEMPLATE + "/" + USER_ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(content().string((mapper.writeValueAsString(userResponseDTO))));
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

    //Auxiliary methods

    private UserResponseDTO createUserResponseDTO() {
        UserResponseDTO responseDTO = new UserResponseDTO(USER_ID, USER_NAME, ENABLED);
        return responseDTO;
    }

    private List<UserResponseDTO> createResponseList() {
        List<UserResponseDTO> list = new ArrayList<>();
        list.add(createUserResponseDTO());
        return list;
    }


}