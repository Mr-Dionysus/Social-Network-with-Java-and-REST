package org.example.services;

import java.sql.SQLException;

public interface UserRolesService {
    void addRoleToUser(int userId, int roleId) throws SQLException;
}
