package org.example.controllers;

import org.example.dtos.RoleDTO;
import org.example.dtos.UserDTO;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.mappers.RoleMapper;
import org.example.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsersRolesControllerTest {

    @Mock
    private RoleService roleService;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private UsersRolesController usersRolesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Assign a Role to a User")
    void assignRoleToUser() {
        int userId = 1;
        String login = "admin";
        String password = "password";
        User mockUser = new User(userId, login, password);

        int roleId = 1;
        String roleName = "admin";
        String description = "manage stuff";
        Role mockRole = new Role(roleId, roleName, description);
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(roleName);
        mockRoleDTO.setDescription(description);
        mockRoleDTO.setUsers(new ArrayList<>(List.of(mockUser)));

        when(roleService.getRoleById(roleId)).thenReturn(mockRole);
        when(roleMapper.roleToRoleDTO(mockRole)).thenReturn(mockRoleDTO);

        ResponseEntity<RoleDTO> response = usersRolesController.assignRoleToUser(userId, roleId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRoleDTO, response.getBody());

        verify(roleService).assignRoleToUser(userId, roleId);
        verify(roleMapper).roleToRoleDTO(mockRole);
    }
}