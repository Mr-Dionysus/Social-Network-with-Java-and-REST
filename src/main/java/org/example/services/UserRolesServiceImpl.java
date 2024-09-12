package org.example.services;

import org.example.repositories.UserRolesRepository;

public class UserRolesServiceImpl implements UserRolesService {
    private final UserRolesRepository USER_ROLES_REPOSITORY;

    public UserRolesServiceImpl(UserRolesRepository USER_ROLES_REPOSITORY) {
        this.USER_ROLES_REPOSITORY = USER_ROLES_REPOSITORY;
    }

    @Override
    public void addRoleToUser(int userId, int roleId) {
        USER_ROLES_REPOSITORY.assignRoleToUser(userId, roleId);
    }
}
