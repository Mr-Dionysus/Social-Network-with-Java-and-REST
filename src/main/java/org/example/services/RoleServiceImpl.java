package org.example.services;

import org.example.dtos.RoleDTO;
import org.example.entities.Role;
import org.example.exceptions.*;
import org.example.mappers.RoleMapper;
import org.example.mappers.RoleMapperImpl;
import org.example.repositories.RoleRepository;
import org.example.validators.RoleValidator;
import org.example.validators.UserValidator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper = new RoleMapperImpl();

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDTO createRole(String roleName, String description) {
        RoleValidator.roleName(roleName);
        RoleValidator.description(description);

        try {
            Role createdRole = roleRepository.createRole(roleName, description);
            RoleValidator.createdRole(createdRole, roleName);
            RoleDTO createdRoleDTO = roleMapper.roleToRoleDTO(createdRole);

            return createdRoleDTO;
        } catch (SQLException e) {
            throw new RoleException("Error while creating a Role", e);
        }
    }

    @Override
    public RoleDTO getRoleById(int roleId) {
        RoleValidator.roleId(roleId);

        try {
            Role foundRole = roleRepository.getRoleById(roleId);
            RoleValidator.foundRole(foundRole, roleId);
            RoleDTO foundRoleDTO = roleMapper.roleToRoleDTO(foundRole);

            return foundRoleDTO;
        } catch (SQLException e) {
            throw new RoleException("Error while getting a Role by ID", e);
        }
    }

    @Override
    public RoleDTO getRoleByIdWithoutItsUsers(int roleId) {
        RoleValidator.roleId(roleId);

        try {
            Role foundRole = roleRepository.getRoleWithoutItsUsers(roleId);
            RoleValidator.foundRole(foundRole, roleId);
            RoleDTO foundRoleDTO = roleMapper.roleToRoleDTO(foundRole);

            return foundRoleDTO;
        } catch (SQLException e) {
            throw new RoleException("Error while getting a Role by ID without Users array", e);
        }
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        try {
            List<Role> listFoundRoles = roleRepository.getAllRoles();
            RoleValidator.listFoundRoles(listFoundRoles);
            List<RoleDTO> listFoundRolesDTO = new ArrayList<>();

            for (Role role : listFoundRoles) {
                RoleDTO roleDTO = roleMapper.roleToRoleDTO(role);
                listFoundRolesDTO.add(roleDTO);
            }

            return listFoundRolesDTO;
        } catch (SQLException e) {
            throw new RoleException("Error while getting all Roles", e);
        }
    }

    @Override
    public RoleDTO updateRoleById(int roleId, String newRoleName, String newDescription) {
        RoleValidator.roleId(roleId);
        RoleValidator.roleName(newRoleName);
        RoleValidator.description(newDescription);

        try {
            Role updatedRole = roleRepository.updateRoleById(roleId, newRoleName, newDescription);
            RoleValidator.foundRole(updatedRole, roleId);
            RoleDTO updatedRoleDTO = roleMapper.roleToRoleDTO(updatedRole);

            return updatedRoleDTO;
        } catch (SQLException e) {
            throw new RoleException("Error while updating a Role", e);
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
            throw new RoleException("Error while deleting a Role", e);
        }
    }

    @Override
    public void assignRoleToUser(int userId, int roleId) {
        UserValidator.userId(userId);
        RoleValidator.roleId(roleId);

        try {
            roleRepository.assignRoleToUser(userId, roleId);
        } catch (SQLException e) {
            throw new RoleException("Error while assigning a Role to a User", e);
        }
    }
}
