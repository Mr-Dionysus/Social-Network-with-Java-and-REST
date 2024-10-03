package org.example.services;

import org.example.dtos.UserDTO;
import org.example.entities.User;

public interface UserService {
    UserDTO createUser(String login, String password);

    UserDTO getUserById(int id);

    User getUserByIdWithoutHisRoles(int id);

    UserDTO updateUserById(int id, String newLogin, String newPassword);

    void deleteUserById(int id);
}
