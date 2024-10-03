package org.example.services;

import org.example.dtos.UserDTO;

public interface UserService {
    UserDTO createUser(String login, String password);

    UserDTO getUserById(int id);

    UserDTO getUserByIdWithoutHisRoles(int id);

    UserDTO updateUserById(int id, String newLogin, String newPassword);

    void deleteUserById(int id);
}
