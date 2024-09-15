package org.example.exceptions;

public class GetPostException extends RuntimeException {
    public GetPostException(String message) {
        super(message);
    }

    public GetPostException(String message, Throwable cause) {
        super(message, cause);
    }
}