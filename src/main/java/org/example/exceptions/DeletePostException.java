package org.example.exceptions;

public class DeletePostException extends RuntimeException {
    public DeletePostException(String message) {
        super(message);
    }

    public DeletePostException(String message, Throwable cause) {
        super(message, cause);
    }

}
