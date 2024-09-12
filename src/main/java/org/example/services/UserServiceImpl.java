package org.example.services;

import org.example.entities.User;
import org.example.repositories.UserRepository;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserRepository USER_REPOSITORY;

    public UserServiceImpl(UserRepository USER_REPOSITORY) {
        this.USER_REPOSITORY = USER_REPOSITORY;
    }

    @Override
    public User createUser(String login, String password) throws SQLException, ClassNotFoundException {
        User user = USER_REPOSITORY.create(login, password);
        return user;
    }

    @Override
    public User getUserById(int id) throws SQLException {
        User user = USER_REPOSITORY.readUser(id);
        return user;
    }

    @Override
    public User getUserByIdWithoutArr(int id) throws SQLException {
        User user = USER_REPOSITORY.readUserWithoutArr(id);
        return user;
    }

    @Override
    public User updateUserById(int id, String newLogin, String newPassword) throws SQLException {
        User user = USER_REPOSITORY.update(id, newLogin, newPassword);
        return user;
    }

    @Override
    public void deleteUserById(int id) throws SQLException {
        USER_REPOSITORY.delete(id);
    }
}
