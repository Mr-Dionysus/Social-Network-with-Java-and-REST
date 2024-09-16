package org.example.services;

import org.example.entities.Role;

import java.util.List;

public interface RoleService {
    Role createRole(String roleName, String description);

    Role getRoleById(int id);

    Role getRoleByIdWithoutItsUsers(int id);

    List<Role> getAllRoles();

    Role updateRoleById(int id, String newRoleName, String newDescription);

    void deleteRoleById(int id);

    void assignRoleToUser(int userId, int roleId);
}
