package services;

import entities.User;

import java.sql.SQLException;

public interface UserService {
    void createUser(User user) throws SQLException, ClassNotFoundException;
    User getUserByLogin(String login) throws SQLException, ClassNotFoundException;
    User updateUserByLogin(String oldLogin, String newLogin, String newPassword) throws SQLException;
    void deleteUserByLogin(String login) throws SQLException;
}
