package com.accenture.library.controller;

import com.accenture.library.domain.Authority;
import com.accenture.library.domain.User;
import com.accenture.library.dto.UserResponseDTO;
import com.accenture.library.service.user.UserService;
import com.accenture.library.service.user.UserServiceImpl;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerRESTTest {

    private static final Long USER_ID = 3L;
    private static final String USER_NAME = "Jānis";
    private static final String PASSWORD = "xxx";
    private static final boolean ENABLED = true;
    private static final String ENCODED_USERNAME = "sercetJanis";
    private static final String ENCODED_PASSWORD = "sercetPassword";

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserServiceImpl service;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    //For ANY user
    @WithMockUser()
    @Test
    public void shouldAddNewUser() throws Exception {
        final String requestBody = "{\"userName\": \"" + ENCODED_USERNAME + "\",\"password\": \""
                + ENCODED_PASSWORD + "\"}";
        when(service.addUser(ENCODED_USERNAME, ENCODED_PASSWORD)).thenReturn(USER_ID);
        mvc.perform(post("/api/v1/users/add")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string((mapper.writeValueAsString(USER_ID))));
    }

    //For ADMIN user
    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "ADMIN")
    @Test
    public void shouldReturnUserResponseDTOList() throws Exception {
        final List<UserResponseDTO> mockList = createResponseList();
        when(service.getUsers()).thenReturn(mockList);
        mvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(mockList))));
    }

    //For ADMIN user
    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "USER")
    @Test
    public void shouldReturnExceptionOnGetUser() throws Exception {
        final List<UserResponseDTO> mockList = createResponseList();
        when(service.getUsers()).thenReturn(mockList);
        mvc.perform(get("/api/v1/users"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "ADMIN")
    @Test
    public void shouldDisableUser() throws Exception {
        final String requestBody = "{\"id\": \"" + USER_ID + "\",\"enabled\": \""
                + false + "\"}";
        when(service.disableUser(USER_ID)).thenReturn(USER_ID);
        mvc.perform(post("/api/v1/users/admin/disable")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(content().string((mapper.writeValueAsString(USER_ID))));
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "USER")
    @Test
    public void shouldReturnExceptionOnUserDisable() throws Exception {
        final String requestBody = "{\"id\": \"" + USER_ID + "\",\"enabled\": \""
                + false + "\"}";
        when(service.disableUser(USER_ID)).thenReturn(USER_ID);
        mvc.perform(post("/api/v1/users/admin/disable")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "ADMIN")
    @Test
    public void shouldEnableUser() throws Exception {
        final String requestBody = "{\"id\": \"" + USER_ID + "\",\"enabled\": \""
                + true + "\"}";
        when(service.enableUser(USER_ID)).thenReturn(USER_ID);
        mvc.perform(post("/api/v1/users/admin/enable")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(content().string((mapper.writeValueAsString(USER_ID))));
    }

    @WithMockUser(username = USER_NAME, password = PASSWORD, authorities = "USER")
    @Test
    public void shouldReturnExceptionOnUserEnable() throws Exception {
        final String requestBody = "{\"id\": \"" + USER_ID + "\",\"enabled\": \""
                + false + "\"}";
        when(service.enableUser(USER_ID)).thenReturn(USER_ID);
        mvc.perform(post("/api/v1/users/admin/disable")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isForbidden());
    }

    //Auxiliary methods
    private Authority createAuthority() {
        final Authority userAuthority = new Authority();
        userAuthority.setAuthorityName("USER");
        return userAuthority;
    }

    private User createTestUser() {
        final User user = new User();
        user.setId(USER_ID);
        user.setUserName(USER_NAME);
        return user;
    }

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