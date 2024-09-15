package org.example.exceptions;

public class GetRoleException extends RuntimeException {
    public GetRoleException(String message) {
        super(message);
    }

    public GetRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}
