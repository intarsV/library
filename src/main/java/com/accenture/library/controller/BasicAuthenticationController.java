package com.accenture.library.controller;

import com.accenture.library.domain.AuthenticationResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BasicAuthenticationController {
    @GetMapping(path = "/basicauth")
    public AuthenticationResponse basicAuthentication(Authentication authentication) {
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final Object[] authorities = userDetails.getAuthorities().toArray();
        return new AuthenticationResponse(authorities[0].toString());
    }
}
