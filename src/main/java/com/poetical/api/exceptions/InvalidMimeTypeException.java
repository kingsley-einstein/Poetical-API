package com.poetical.api.exceptions;

public class InvalidMimeTypeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidMimeTypeException(String message) {
        super(message);
    }
}