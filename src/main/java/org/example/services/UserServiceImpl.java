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
    public User createUser(String login, String password) throws SQLException,
            ClassNotFoundException {
        User user = userRepository.create(login, password);
        return user;
    }

    public User getUserById(int id) throws SQLException {
        User user = userRepository.read(id);
        return user;
    }

    @Override
    public User updateUserById(int id, String newLogin, String newPassword) throws SQLException {
        User user = userRepository.update(id, newLogin, newPassword);
        return user;
    }

    @Override
    public void deleteUserById(int id) throws SQLException {
        userRepository.delete(id);
    }
}
