package org.example.services;

import org.example.entities.User;
import org.example.exceptions.CreateUserException;
import org.example.exceptions.DeleteUserException;
import org.example.exceptions.GetUserException;
import org.example.exceptions.UpdateUserException;
import org.example.repositories.UserRepository;
import org.example.validators.UserValidator;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String login, String password) {
        UserValidator.login(login);
        UserValidator.password(password);

        try {
            User createdUser = userRepository.createUser(login, password);
            UserValidator.createdUser(createdUser, login);

            return createdUser;
        } catch (SQLException e) {
            throw new CreateUserException("Error while creating a User", e);
        }
    }

    @Override
    public User getUserById(int userId) {
        UserValidator.userId(userId);

        try {
            User foundUser = userRepository.findUserById(userId);
            UserValidator.foundUser(foundUser, userId);

            return foundUser;
        } catch (SQLException e) {
            throw new GetUserException("Error while getting a User", e);
        }
    }

    @Override
    public User getUserByIdWithoutHisRoles(int userId) {
        UserValidator.userId(userId);

        try {
            User foundUser = userRepository.findUserWithoutHisRoles(userId);
            UserValidator.foundUser(foundUser, userId);

            return foundUser;
        } catch (SQLException e) {
            throw new GetUserException("Error while getting a User without his Roles", e);
        }
    }

    @Override
    public User updateUserById(int userId, String newLogin, String newPassword) {
        UserValidator.userId(userId);
        UserValidator.login(newLogin);
        UserValidator.password(newPassword);

        try {
            User updatedUser = userRepository.updateUser(userId, newLogin, newPassword);
            UserValidator.foundUser(updatedUser, userId);

            return updatedUser;
        } catch (SQLException e) {
            throw new UpdateUserException("Error while updating a User by ID", e);
        }
    }

    @Override
    public void deleteUserById(int userId) {
        UserValidator.userId(userId);

        try {
            userRepository.deleteUser(userId);
        } catch (SQLException e) {
            throw new DeleteUserException("Error while deleting a User by ID", e);
        }
    }
}
