package org.example.controllers;

import org.example.dtos.RoleDTO;
import org.example.entities.Role;
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

class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create a Role")
    void createRole() {
        int roleId = 1;
        String roleName = "admin";
        String description = "do stuff";

        Role mockRole = new Role(roleId, roleName, description);
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(roleName);
        mockRoleDTO.setDescription(description);

        when(roleService.createRole(roleName, description)).thenReturn(mockRole);
        when(roleMapper.roleToRoleDTO(mockRole)).thenReturn(mockRoleDTO);

        ResponseEntity<RoleDTO> response = roleController.createRole(mockRoleDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockRoleDTO, response.getBody());

        verify(roleService).createRole(roleName, description);
        verify(roleMapper).roleToRoleDTO(mockRole);
    }

    @Test
    @DisplayName("Get a Role by ID")
    void getRoleById() {
        int roleId = 1;
        String roleName = "admin";
        String description = "do stuff";

        Role mockRole = new Role(roleId, roleName, description);
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(roleName);
        mockRoleDTO.setDescription(description);

        when(roleService.getRoleById(roleId)).thenReturn(mockRole);
        when(roleMapper.roleToRoleDTO(mockRole)).thenReturn(mockRoleDTO);

        ResponseEntity<RoleDTO> response = roleController.getRoleById(roleId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRoleDTO, response.getBody());

        verify(roleService).getRoleById(roleId);
        verify(roleMapper).roleToRoleDTO(mockRole);
    }

    @Test
    @DisplayName("Get all Roles")
    void getAllRoles() {
        int roleId = 1;
        String roleName1 = "admin";
        String description1 = "do stuff";
        Role mockRole1 = new Role(roleId, roleName1, description1);

        int roleId2 = 2;
        String roleName2 = "user";
        String description2 = "use stuff";
        Role mockRole2 = new Role(roleId2, roleName2, description2);

        RoleDTO mockRoleDTO1 = new RoleDTO();
        mockRoleDTO1.setRoleName(roleName1);
        mockRoleDTO1.setDescription(description1);

        RoleDTO mockRoleDTO2 = new RoleDTO();
        mockRoleDTO2.setRoleName(roleName2);
        mockRoleDTO2.setDescription(description2);

        List<Role> listRoles = new ArrayList<>();
        listRoles.add(mockRole1);
        listRoles.add(mockRole2);

        when(roleService.getAllRoles()).thenReturn(listRoles);
        when(roleMapper.roleToRoleDTO(mockRole1)).thenReturn(mockRoleDTO1);
        when(roleMapper.roleToRoleDTO(mockRole2)).thenReturn(mockRoleDTO2);

        List<RoleDTO> listRoleDTOs = new ArrayList<>();
        listRoleDTOs.add(mockRoleDTO1);
        listRoleDTOs.add(mockRoleDTO2);

        ResponseEntity<ArrayList<RoleDTO>> response = roleController.getAllRoles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listRoleDTOs, response.getBody());

        verify(roleService).getAllRoles();
        verify(roleMapper).roleToRoleDTO(mockRole1);
        verify(roleMapper).roleToRoleDTO(mockRole2);
    }

    @Test
    @DisplayName("Update a Role by ID")
    void updateRoleById() {
        int roleId = 1;
        String roleName = "admin";
        String description = "do stuff";

        Role mockRole = new Role(roleId, roleName, description);
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(roleName);
        mockRoleDTO.setDescription(description);

        when(roleService.updateRoleById(roleId, roleName, description)).thenReturn(mockRole);
        when(roleMapper.roleToRoleDTO(mockRole)).thenReturn(mockRoleDTO);

        ResponseEntity<RoleDTO> response = roleController.updateRoleById(roleId, mockRoleDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRoleDTO, response.getBody());

        verify(roleService).updateRoleById(roleId,roleName, description);
        verify(roleMapper).roleToRoleDTO(mockRole);
    }

    @Test
    @DisplayName("Delete a Role by ID")
    void deleteRoleById() {
        int roleId = 1;
        ResponseEntity<Void> response = roleController.deleteRoleById(roleId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(roleService).deleteRoleById(roleId);
    }
}