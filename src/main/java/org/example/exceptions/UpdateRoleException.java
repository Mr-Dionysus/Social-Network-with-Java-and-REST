package org.example.exceptions;

public class UpdateRoleException extends RuntimeException {
    public UpdateRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}
