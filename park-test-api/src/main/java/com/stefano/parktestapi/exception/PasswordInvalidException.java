package com.stefano.parktestapi.exception;

public class PasswordInvalidException extends RuntimeException {

    public PasswordInvalidException(String message) {
        super(message);
    }
}
