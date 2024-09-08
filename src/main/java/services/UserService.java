package services;

import entities.User;

import java.sql.SQLException;

public interface UserService {
    void createUser(User user) throws SQLException, ClassNotFoundException;
    User getUserByLogin(String login) throws SQLException, ClassNotFoundException;
}
