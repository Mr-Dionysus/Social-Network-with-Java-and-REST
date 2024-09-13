package org.example.services;

import org.example.repositories.UserRolesRepository;

public class UserRolesServiceImpl implements UserRolesService {
    private final UserRolesRepository userRolesRepository;

    public UserRolesServiceImpl(UserRolesRepository userRolesRepository) {
        this.userRolesRepository = userRolesRepository;
    }

    @Override
    public void addRoleToUser(int userId, int roleId) {
        userRolesRepository.assignRoleToUser(userId, roleId);
    }
}
