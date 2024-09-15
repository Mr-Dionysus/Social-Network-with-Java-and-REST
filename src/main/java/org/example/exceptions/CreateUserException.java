package org.example.exceptions;

public class CreateUserException extends RuntimeException {

    public CreateUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
