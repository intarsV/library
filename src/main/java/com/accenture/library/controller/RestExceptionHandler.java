package com.accenture.library.controller;

import com.accenture.library.dto.ExceptionResponse;
import com.accenture.library.exceptions.LibraryException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(LibraryException.class)
    protected ResponseEntity<Object> handleValidationException(LibraryException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
