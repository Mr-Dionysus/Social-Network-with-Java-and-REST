package org.example.services;

import org.example.entities.Role;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RoleService {
    Role createRole(String roleName, String description);

    Role getRoleById(int id);

    Role getRoleByIdWithoutArr(int id);

    ArrayList<Role> getAllRoles() throws SQLException;

    Role updateRoleById(int id, String newRoleName, String newDescription) throws SQLException;

    void deleteRoleById(int id) throws SQLException;
}
