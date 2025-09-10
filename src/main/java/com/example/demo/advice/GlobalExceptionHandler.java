package com.example.demo.advice;

import com.example.demo.Exception.InvalidAgeException;
import com.example.demo.Exception.InvalidStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(InvalidAgeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseException handleInvalidAgeException(Exception ex) {
        return new ResponseException(ex.getMessage());
    }

    @ExceptionHandler(InvalidStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleInvalidStatusException(Exception ex) {
        return new ResponseException(ex.getMessage());
    }

}