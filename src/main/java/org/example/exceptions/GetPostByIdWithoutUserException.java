package org.example.exceptions;

public class GetPostByIdWithoutUserException extends RuntimeException {
    public GetPostByIdWithoutUserException(String message) {
        super(message);
    }

    public GetPostByIdWithoutUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
