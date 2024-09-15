package org.example.exceptions;

public class CreatePostException extends RuntimeException {
    public CreatePostException(String message) {
        super(message);
    }

    public CreatePostException(String message, Throwable cause) {
        super(message, cause);
    }
}