package org.example.exceptions;

public class GetUserException extends RuntimeException {
    public GetUserException(String message) {
        super(message);
    }

    public GetUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
