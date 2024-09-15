package org.example.services;

import org.example.entities.Role;
import org.example.exceptions.*;
import org.example.repositories.RoleRepository;
import org.example.validators.RoleValidator;

import java.sql.SQLException;
import java.util.ArrayList;

public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(String roleName, String description) {
        RoleValidator.roleName(roleName);
        RoleValidator.description(description);

        try {
            Role createdRole = roleRepository.createRole(roleName, description);
            RoleValidator.createdRole(createdRole, roleName);

            return createdRole;
        } catch (SQLException e) {
            throw new CreateRoleException("Error while creating a Role", e);
        }
    }

    @Override
    public Role getRoleById(int roleId) {
        RoleValidator.roleId(roleId);

        try {
            Role foundRole = roleRepository.getRoleById(roleId);
            RoleValidator.foundRole(foundRole, roleId);

            return foundRole;
        } catch (SQLException e) {
            throw new GetRoleException("Error while getting a Role by ID", e);
        }
    }

    @Override
    public Role getRoleByIdWithoutUsers(int roleId) {
        RoleValidator.roleId(roleId);

        try {
            Role foundRole = roleRepository.getRoleWithoutItsUsers(roleId);
            RoleValidator.foundRole(foundRole, roleId);

            return foundRole;
        } catch (SQLException e) {
            throw new GetRoleException("Error while getting a Role by ID without Users array", e);
        }
    }

    @Override
    public ArrayList<Role> getAllRoles() {
        try {
            ArrayList<Role> listFoundRoles = roleRepository.getAllRoles();
            RoleValidator.listFoundRoles(listFoundRoles);

            return listFoundRoles;
        } catch (SQLException e) {
            throw new GetRoleException("Error while getting all Roles", e);
        }
    }

    @Override
    public Role updateRoleById(int roleId, String newRoleName, String newDescription) {
        RoleValidator.roleId(roleId);
        RoleValidator.roleName(newRoleName);
        RoleValidator.description(newDescription);

        try {
            Role updatedRole = roleRepository.updateRoleById(roleId, newRoleName, newDescription);
            RoleValidator.foundRole(updatedRole, roleId);

            return updatedRole;
        } catch (SQLException e) {
            throw new UpdateRoleException("Error while updating a Role", e);
        }
    }

    @Override
    public void deleteRoleById(int roleId) {
        RoleValidator.roleId(roleId);

        if (this.getRoleById(roleId) == null) {
            throw new RoleNotFoundException("Error while deleting the Role. Role with ID '" + roleId + "' " + "can't be found");
        }

        try {
            roleRepository.deleteRoleById(roleId);
        } catch (SQLException e) {
            throw new DeleteRoleException("Error while deleting a Role", e);
        }
    }
}
