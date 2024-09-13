package org.example.services;

import org.example.entities.Role;
import org.example.repositories.RoleRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(String roleName, String description) throws SQLException {
        Role createdRole = roleRepository.createRole(roleName, description);
        return createdRole;
    }

    @Override
    public Role getRoleById(int id) throws SQLException {
        Role foundRole = roleRepository.readRole(id);
        return foundRole;
    }

    @Override
    public Role getRoleByIdWithoutArr(int id) throws SQLException {
        Role foundRole = roleRepository.readRoleWithoutArray(id);
        return foundRole;
    }

    @Override
    public ArrayList<Role> getAllRoles() throws SQLException {
        ArrayList<Role> listFoundRoles = roleRepository.readAllRoles();
        return listFoundRoles;
    }

    @Override
    public Role updateRoleById(int id, String newRoleName, String newDescription) throws SQLException {
        Role updatedRole = roleRepository.updateRole(id, newRoleName, newDescription);
        return updatedRole;
    }

    @Override
    public void deleteRoleById(int id) throws SQLException {
        roleRepository.deleteRoleById(id);
    }
}
