package org.example.services;

import org.example.dtos.UserDTO;
import org.example.entities.User;
import org.example.exceptions.*;
import org.example.mappers.UserMapper;
import org.example.mappers.UserMapperImpl;
import org.example.repositories.UserRepository;
import org.example.validators.UserValidator;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper = new UserMapperImpl();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(String login, String password) {
        UserValidator.login(login);
        UserValidator.password(password);

        try {
            User createdUser = userRepository.createUser(login, password);
            UserValidator.createdUser(createdUser, login);
            UserDTO createdUserDTO = userMapper.userToUserDTO(createdUser);

            return createdUserDTO;
        } catch (SQLException e) {
            throw new UserException("Error while creating a User", e);
        }
    }

    @Override
    public UserDTO getUserById(int userId) {
        UserValidator.userId(userId);

        try {
            User foundUser = userRepository.getUserById(userId);
            UserValidator.foundUser(foundUser, userId);
            UserDTO foundUserDTO = userMapper.userToUserDTO(foundUser);

            return foundUserDTO;
        } catch (SQLException e) {
            throw new UserException("Error while getting a User", e);
        }
    }

    @Override
    public UserDTO getUserByIdWithoutHisRoles(int userId) {
        UserValidator.userId(userId);

        try {
            User foundUser = userRepository.getUserWithoutHisRoles(userId);
            UserValidator.foundUser(foundUser, userId);
            UserDTO foundUserDTO = userMapper.userToUserDTO(foundUser);

            return foundUserDTO;
        } catch (SQLException e) {
            throw new UserException("Error while getting a User without his Roles", e);
        }
    }

    @Override
    public UserDTO updateUserById(int userId, String newLogin, String newPassword) {
        UserValidator.userId(userId);
        UserValidator.login(newLogin);
        UserValidator.password(newPassword);

        try {
            User updatedUser = userRepository.updateUserById(userId, newLogin, newPassword);
            UserValidator.foundUser(updatedUser, userId);
            UserDTO updatedUserDTO = userMapper.userToUserDTO(updatedUser);

            return updatedUserDTO;
        } catch (SQLException e) {
            throw new UserException("Error while updating a User by ID", e);
        }
    }

    @Override
    public void deleteUserById(int userId) {
        UserValidator.userId(userId);

        if (this.getUserById(userId) == null) {
            throw new UserNotFoundException("Error while deleting the User. User with ID '" + userId + "' can't be found");
        }

        try {
            userRepository.deleteUserById(userId);
        } catch (SQLException e) {
            throw new UserException("Error while deleting a User by ID", e);
        }
    }
}
