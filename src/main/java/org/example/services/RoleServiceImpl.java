package org.example.services;

import jakarta.transaction.Transactional;
import org.example.dtos.RoleDTO;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.exceptions.*;
import org.example.mappers.RoleMapper;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDTO createRole(String roleName, String description) {

        Role role = new Role(roleName, description);
        Role createdRole = roleRepository.save(role);
        RoleDTO createdRoleDTO = roleMapper.roleToRoleDTO(createdRole);

        return createdRoleDTO;
    }

    @Override
    public RoleDTO getRoleById(int roleId) {

        Role foundRole = roleRepository.findById(roleId)
                                       .isPresent() ? roleRepository.findById(roleId)
                                                                    .get() : null;
        RoleDTO foundRoleDTO = roleMapper.roleToRoleDTO(foundRole);

        return foundRoleDTO;
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> listFoundRoles = roleRepository.findAll();
        List<RoleDTO> listFoundRolesDTO = new ArrayList<>();

        for (Role role : listFoundRoles) {
            RoleDTO roleDTO = roleMapper.roleToRoleDTO(role);
            listFoundRolesDTO.add(roleDTO);
        }

        return listFoundRolesDTO;
    }

    @Override
    public RoleDTO updateRoleById(int roleId, String newRoleName, String newDescription) {

        Role role = roleRepository.findById(roleId)
                                  .isPresent() ? roleRepository.findById(roleId)
                                                               .get() : null;

        role.setRoleName(newRoleName);
        role.setDescription(newDescription);
        Role updatedRole = roleRepository.save(role);
        RoleDTO updatedRoleDTO = roleMapper.roleToRoleDTO(updatedRole);

        return updatedRoleDTO;
    }

    @Override
    public void deleteRoleById(int roleId) {

        if (this.getRoleById(roleId) == null) {
            throw new RoleNotFoundException("Error while deleting the Role. Role with ID '" + roleId + "' " + "can't be found");
        }

        Role role = roleRepository.findById(roleId)
                                  .isPresent() ? roleRepository.findById(roleId)
                                                               .get() : null;
        List<User> users = role.getUsers();

        for (User user : users) {
            user.getRoles()
                .remove(role);
            userRepository.save(user);
        }

        roleRepository.deleteById(roleId);

    }

    @Override
    @Transactional
    public void assignRoleToUser(int userId, int roleId) {

        User user = userRepository.findById(userId)
                                  .isPresent() ? userRepository.findById(userId)
                                                               .get() : null;
        Role role = roleRepository.findById(roleId)
                                  .isPresent() ? roleRepository.findById(roleId)
                                                               .get() : null;

        List<Role> updatedRoles = new ArrayList<>(user.getRoles());
        updatedRoles.add(role);
        user.setRoles(updatedRoles);
        userRepository.save(user);
    }
}
