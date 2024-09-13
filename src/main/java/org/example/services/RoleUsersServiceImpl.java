package org.example.services;

import org.example.repositories.RoleUsersRepository;

import java.sql.SQLException;

public class RoleUsersServiceImpl implements RoleUsersService {
    private final RoleUsersRepository roleUsersRepository;

    public RoleUsersServiceImpl(RoleUsersRepository roleUsersRepository) {
        this.roleUsersRepository = roleUsersRepository;
    }

    @Override
    public void addUserToRole(int userId, int roleId) throws SQLException {
        roleUsersRepository.assignUserToRole(userId, roleId);
    }
}
