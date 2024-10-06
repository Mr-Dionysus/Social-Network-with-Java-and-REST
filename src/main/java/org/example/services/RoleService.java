package org.example.services;

import org.example.dtos.RoleDTO;

import java.util.List;

public interface RoleService {
    RoleDTO createRole(String roleName, String description);

    RoleDTO getRoleById(int id);

    List<RoleDTO> getAllRoles();

    RoleDTO updateRoleById(int id, String newRoleName, String newDescription);

    void deleteRoleById(int id);

    void assignRoleToUser(int userId, int roleId);
}
