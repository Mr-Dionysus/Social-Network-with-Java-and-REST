package org.example.services;

import org.example.repositories.UserRolesRepository;

public class UserRolesServiceImpl implements UserRolesService {
    private final UserRolesRepository USER_ROLES_REPOSITORY;

    public UserRolesServiceImpl(UserRolesRepository userRolesRepository) {
        USER_ROLES_REPOSITORY = userRolesRepository;
    }

    @Override
    public void addRoleToUser(int userId, int roleId) {
        USER_ROLES_REPOSITORY.assignRoleToUser(userId, roleId);
    }
}
