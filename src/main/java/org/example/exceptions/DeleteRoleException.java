package org.example.exceptions;

public class DeleteRoleException extends RuntimeException {
    public DeleteRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}
