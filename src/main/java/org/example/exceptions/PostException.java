package org.example.exceptions;

public class PostException extends RuntimeException {
    public PostException(String message, Throwable cause) {
        super(message, cause);
    }
}
