package com.accenture.library.controller;

import com.accenture.library.domain.AuthenticationBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BasicAuthenticationController {

    @GetMapping(path = "/basicauth")
    public AuthenticationBean helloWorldBean() {
        return new AuthenticationBean("You are authenticated");
    }
}
