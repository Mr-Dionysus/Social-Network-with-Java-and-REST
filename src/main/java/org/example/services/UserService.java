package org.example.services;

import org.example.entities.User;

import java.sql.SQLException;

public interface UserService {
    User createUser(String login, String password) throws SQLException, ClassNotFoundException;

    User getUserById(int id) throws SQLException, ClassNotFoundException;

    User getUserByIdWithoutArr(int id) throws SQLException;

    User updateUserById(int id, String newLogin, String newPassword) throws SQLException;

    void deleteUserById(int id) throws SQLException;
}
