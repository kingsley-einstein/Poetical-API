package com.poetical.api.exceptions;

public class AlreadyExistingUserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AlreadyExistingUserException(String message) {
        super(message);
    }
}