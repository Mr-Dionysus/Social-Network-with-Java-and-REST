package org.example.services;

import org.example.entities.User;

public interface UserService {
    User createUser(String login, String password);

    User getUserById(int id);

    User getUserByIdWithoutHisRoles(int id);

    User updateUserById(int id, String newLogin, String newPassword);

    void deleteUserById(int id);
}
