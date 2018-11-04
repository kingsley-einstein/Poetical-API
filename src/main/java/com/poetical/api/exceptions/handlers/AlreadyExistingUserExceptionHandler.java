package com.poetical.api.exceptions.handlers;

import com.poetical.api.exceptions.AlreadyExistingUserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class AlreadyExistingUserExceptionHandler {

    @ExceptionHandler(value = AlreadyExistingUserException.class)
    public ResponseEntity<Object> exception(AlreadyExistingUserException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}