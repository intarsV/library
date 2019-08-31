package com.accenture.library.controller;

import com.accenture.library.domain.AuthenticationBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BasicAuthenticationController {

    @GetMapping(path = "/basicauth")
    public AuthenticationBean helloWorldBean() {

//        System.out.println("BEeeee");
//        String encoded=new BCryptPasswordEncoder().encode("initex000");
//        System.out.println("XIX "+encoded+" XIX");
//        String encoded1=new BCryptPasswordEncoder().encode("ritvars000");
//        System.out.println(encoded1);
        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
        return new AuthenticationBean("You are authenticated");
    }
}
