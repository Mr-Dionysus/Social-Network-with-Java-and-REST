package org.example.exceptions;

public class UpdateUserException extends RuntimeException {
    public UpdateUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
