package com.example.demo.advice;

import com.example.demo.Exception.InvalidAgeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = InvalidAgeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseException handleResponseStatusException(ResponseStatusException ex) {
        return new ResponseException(ex.getReason());
    }

}
