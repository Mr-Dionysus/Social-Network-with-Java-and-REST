package org.example.repositories;

import org.example.entities.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleRepository {
    Role createRole(String roleName, String description) throws SQLException;

    Role getRoleById(int roleId) throws SQLException;

    Role getRoleByIdWithoutItsUsers(int roleId) throws SQLException;

    List<Role> getAllRoles() throws SQLException;

    Role updateRoleById(int roleId, String newRoleName, String newDescription) throws SQLException;

    void deleteRoleById(int id) throws SQLException;

    void assignRoleToUser(int userId, int roleId) throws SQLException;
}
