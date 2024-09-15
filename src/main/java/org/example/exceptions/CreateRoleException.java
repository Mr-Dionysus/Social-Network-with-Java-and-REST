package org.example.exceptions;

public class CreateRoleException extends RuntimeException {
    public CreateRoleException(String message) {
        super(message);
    }

    public CreateRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}