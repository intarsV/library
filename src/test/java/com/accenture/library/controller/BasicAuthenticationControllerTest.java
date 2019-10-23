package com.accenture.library.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicAuthenticationControllerTest {

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

    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "ADMIN")
    @Test
    public void shoutReturnAuthSuccessfulAdmin() throws Exception {
        mvc.perform(get("/basicauth"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'message':'ADMIN'}"));
    }


    @WithMockUser(username = "vilnis", password = "vilnis000", authorities = "USER")
    @Test
    public void shoutReturnAuthSuccessfulUser() throws Exception {
        mvc.perform(get("/basicauth"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'message':'USER'}"));
    }

    @Test
    public void shoutReturnAuthReject() throws Exception {
        mvc.perform(get("/basicauth"))
                .andExpect(status().is(401));
    }
}