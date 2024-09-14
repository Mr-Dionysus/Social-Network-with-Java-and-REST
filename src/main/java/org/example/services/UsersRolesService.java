package org.example.services;

import java.sql.SQLException;

public interface UsersRolesService {
    void assignRoleToUser(int userId, int roleId) throws SQLException;
}
