package com.poetical.api.exceptions;

public class IncorrectPasswordException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IncorrectPasswordException(String message) {
        super(message);
    }
}