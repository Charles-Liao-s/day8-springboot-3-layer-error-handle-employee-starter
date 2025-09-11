package com.example.demo.advice;

import com.example.demo.Exception.InvalidAgeException;
import com.example.demo.Exception.InvalidStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(InvalidAgeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseException handleInvalidAgeException(Exception ex) {
        return new ResponseException(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseException handlerArgumentNotValid(MethodArgumentNotValidException exception){
        String errorMessage = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        return new ResponseException(errorMessage);
    }

}