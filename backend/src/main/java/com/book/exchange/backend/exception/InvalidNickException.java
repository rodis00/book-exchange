package com.book.exchange.backend.exception;

public class InvalidNickException extends RuntimeException {
    public InvalidNickException(String message) {
        super(message);
    }
}
