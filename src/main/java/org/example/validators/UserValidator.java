package org.example.validators;

import org.example.entities.User;
import org.example.exceptions.PostNotFoundException;

public class UserValidator {
    private UserValidator() {
        throw new IllegalStateException("Utility Class");
    }

    public static void userId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID can't be 0 or less");
        }
    }

    public static void foundUser(User foundUser, int userId) {
        if (foundUser == null) {
            throw new PostNotFoundException("User with ID '" + userId + " can't be found");
        }
    }

    public static void createdUser(User createdUser, String login) {
        if (createdUser == null) {
            throw new PostNotFoundException("Created User with the login '" + login + "' can't be found");
        }
    }

    public static void login(String login) {
        if (login == null || login.trim()
                                  .isEmpty() || login.contains(" ")) {
            throw new IllegalArgumentException("User's login can't be null, empty, and can't have spaces");
        }
    }

    public static void password(String password) {
        if (password == null || password.trim()
                                        .isEmpty() || password.contains(" ")) {
            throw new IllegalArgumentException("User's password can't be null, empty, and can't have spaces");
        }
    }
}
