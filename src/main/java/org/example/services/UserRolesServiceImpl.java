package org.example.services;

import org.example.repositories.UserRolesRepository;

import java.sql.SQLException;

public class UserRolesServiceImpl implements UserRolesService {
    private final UserRolesRepository userRolesRepository;

    public UserRolesServiceImpl(UserRolesRepository userRolesRepository) {
        this.userRolesRepository = userRolesRepository;
    }

    @Override
    public void addRoleToUser(int userId, int roleId) throws SQLException {
        userRolesRepository.assignRoleToUser(userId, roleId);
    }
}
