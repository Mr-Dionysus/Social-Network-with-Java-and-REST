package org.example.exceptions;

public class UpdatePostException extends RuntimeException {
    public UpdatePostException(String message) {
        super(message);
    }

    public UpdatePostException(String message, Throwable cause) {
        super(message, cause);
    }
}
