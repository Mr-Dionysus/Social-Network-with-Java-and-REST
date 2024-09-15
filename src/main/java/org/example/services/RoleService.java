package org.example.services;

import org.example.entities.Role;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RoleService {
    Role createRole(String roleName, String description) throws SQLException;

    Role getRoleById(int id) throws SQLException;

    Role getRoleByIdWithoutItsUsers(int id) throws SQLException;

    ArrayList<Role> getAllRoles() throws SQLException;

    Role updateRoleById(int id, String newRoleName, String newDescription) throws SQLException;

    void deleteRoleById(int id) throws SQLException;
}
