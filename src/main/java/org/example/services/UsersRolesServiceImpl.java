package org.example.services;

import org.example.exceptions.AssignRoleException;
import org.example.repositories.UsersRolesRepository;
import org.example.validators.RoleValidator;
import org.example.validators.UserValidator;

import java.sql.SQLException;

public class UsersRolesServiceImpl implements UsersRolesService {
    private final UsersRolesRepository usersRolesRepository;

    public UsersRolesServiceImpl(UsersRolesRepository usersRolesRepository) {
        this.usersRolesRepository = usersRolesRepository;
    }

    @Override
    public void assignRoleToUser(int userId, int roleId) {
        UserValidator.userId(userId);
        RoleValidator.roleId(roleId);

        try {
            usersRolesRepository.assignRoleToUser(userId, roleId);
        } catch (SQLException e) {
            throw new AssignRoleException("Error while assigning a Role to a User", e);
        }
    }
}
