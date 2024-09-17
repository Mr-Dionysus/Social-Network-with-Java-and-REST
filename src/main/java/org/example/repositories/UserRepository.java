package org.example.repositories;

import org.example.entities.User;

import java.sql.SQLException;

public interface UserRepository {
    User createUser(String login, String password) throws SQLException;

    User getUserById(int userId) throws SQLException;

    User getUserWithoutHisRoles(int userId) throws SQLException;

    User updateUserById(int userId, String newLogin, String newPassword) throws SQLException;

    void deleteUserById(int userId) throws SQLException;
}
