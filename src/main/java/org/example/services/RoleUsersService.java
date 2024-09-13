package org.example.services;

import java.sql.SQLException;

public interface RoleUsersService {
    void addUserToRole(int userId, int roleId) throws SQLException;
}
