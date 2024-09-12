package org.example.services;

import org.example.repositories.RoleUsersRepository;

public class RoleUsersServiceImpl implements RoleUsersService {
    private final RoleUsersRepository ROLE_USERS_REPOSITORY;

    public RoleUsersServiceImpl(RoleUsersRepository roleUsersRepository) {
        ROLE_USERS_REPOSITORY = roleUsersRepository;
    }

    @Override
    public void addUserToRole(int userId, int roleId) {
        ROLE_USERS_REPOSITORY.assignUserToRole(userId, roleId);
    }
}
