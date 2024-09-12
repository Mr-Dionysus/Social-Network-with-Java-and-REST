package org.example.services;

import org.example.repositories.RoleUsersRepository;

public class RoleUsersServiceImpl implements RoleUsersService {
    private final RoleUsersRepository ROLE_USERS_REPOSITORY;

    public RoleUsersServiceImpl(RoleUsersRepository ROLE_USERS_REPOSITORY) {
        this.ROLE_USERS_REPOSITORY = ROLE_USERS_REPOSITORY;
    }

    @Override
    public void addUserToRole(int userId, int roleId) {
        ROLE_USERS_REPOSITORY.assignUserToRole(userId, roleId);
    }
}
