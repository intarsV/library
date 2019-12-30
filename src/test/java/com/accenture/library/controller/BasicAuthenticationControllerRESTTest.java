package com.accenture.library.controller;

import com.accenture.library.config.SpringSecurityConfiguration;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BasicAuthenticationControllerREST.class)
@Import(SpringSecurityConfiguration.class)
public class BasicAuthenticationControllerRESTTest {

    private static final String URL_TEMPLATE = "/basicauth";

    @MockBean
    private DataSource dataSource;

    @Autowired
    private MockMvc mvc;

    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "ADMIN")
    @Test
    public void shoutReturnAuthSuccessfulAdmin() throws Exception {
        mvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(content().json("{'message':'ADMIN'}"));
    }


    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "USER")
    @Test
    public void shoutReturnAuthSuccessfulUser() throws Exception {
        mvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(content().json("{'message':'USER'}"));
    }

    @Test
    public void shoutReturnAuthReject() throws Exception {
        mvc.perform(get(URL_TEMPLATE))
                .andExpect(status().is(401));
    }
}