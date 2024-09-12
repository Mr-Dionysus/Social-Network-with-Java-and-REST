package org.example.services;

import org.example.entities.Role;
import org.example.repositories.RoleRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class RoleServiceImpl implements RoleService {
    private final RoleRepository ROLE_REPOSITORY;

    public RoleServiceImpl(RoleRepository ROLE_REPOSITORY) {
        this.ROLE_REPOSITORY = ROLE_REPOSITORY;
    }

    @Override
    public Role createRole(String roleName, String description) {
        Role createdRole = ROLE_REPOSITORY.createRole(roleName, description);
        return createdRole;
    }

    @Override
    public Role getRoleById(int id) {
        Role foundRole = ROLE_REPOSITORY.readRole(id);
        return foundRole;
    }

    @Override
    public Role getRoleByIdWithoutArr(int id) {
        Role foundRole = ROLE_REPOSITORY.readRoleWithoutArray(id);
        return foundRole;
    }

    @Override
    public ArrayList<Role> getAllRoles() throws SQLException {
        ArrayList<Role> listFoundRoles = ROLE_REPOSITORY.readAllRoles();
        return listFoundRoles;
    }

    @Override
    public Role updateRoleById(int id, String newRoleName, String newDescription) throws SQLException {
        Role updatedRole = ROLE_REPOSITORY.updateRole(id, newRoleName, newDescription);
        return updatedRole;
    }

    @Override
    public void deleteRoleById(int id) throws SQLException {
        ROLE_REPOSITORY.deleteRoleById(id);
    }
}
