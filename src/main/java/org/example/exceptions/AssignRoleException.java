package org.example.exceptions;

public class AssignRoleException extends RuntimeException {
    public AssignRoleException(String message) {
        super(message);
    }

    public AssignRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}
