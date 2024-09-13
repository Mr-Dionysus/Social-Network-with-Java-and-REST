package org.example.services;

import org.example.entities.User;
import org.example.repositories.UserRepository;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String login, String password) throws SQLException, ClassNotFoundException {
        User createdUser = userRepository.createUser(login, password);
        return createdUser;
    }

    @Override
    public User getUserById(int id) throws SQLException {
        User foundUser = userRepository.findUserById(id);
        return foundUser;
    }

    @Override
    public User getUserByIdWithoutRoles(int id) throws SQLException {
        User foundUser = userRepository.findUserWithoutHisRoles(id);
        return foundUser;
    }

    @Override
    public User updateUserById(int id, String newLogin, String newPassword) throws SQLException {
        User updatedUser = userRepository.updateUser(id, newLogin, newPassword);
        return updatedUser;
    }

    @Override
    public void deleteUserById(int id) throws SQLException {
        userRepository.deleteUser(id);
    }
}
