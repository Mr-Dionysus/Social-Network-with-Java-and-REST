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
        Role role = ROLE_REPOSITORY.createRole(roleName, description);
        return role;
    }

    @Override
    public Role getRoleById(int id) {
        Role role = ROLE_REPOSITORY.readRole(id);
        return role;
    }


    public Role getRoleByIdWithoutArr(int id) {
        Role role = ROLE_REPOSITORY.readRoleWithoutArray(id);
        return role;
    }

    @Override
    public ArrayList<Role> getAllRoles() throws SQLException {
        ArrayList<Role> listRoles = ROLE_REPOSITORY.readAllRoles();
        return listRoles;
    }

    @Override
    public Role updateRoleById(int id, String newRoleName, String newDescription) throws SQLException {
        Role role = ROLE_REPOSITORY.updateRole(id, newRoleName, newDescription);
        return role;
    }

    @Override
    public void deleteRoleById(int id) throws SQLException {
        ROLE_REPOSITORY.deleteRoleById(id);
    }
}
