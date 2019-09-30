package com.accenture.library.domain;

public class AuthenticationResponse {

    private String message;

    public AuthenticationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("[message=%s]", message);
    }
}
