package org.example.services;

import org.example.dtos.RoleDTO;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.exceptions.RoleNotFoundException;
import org.example.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create a Role")
    void createRole() {
        String roleName = "admin";
        String description = "manage stuff";

        Role mockRole = new Role(roleName, description);
        when(roleRepository.save(mockRole)).thenReturn(mockRole);
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(roleName);
        mockRoleDTO.setDescription(description);

        RoleDTO actualRole = roleService.createRole(roleName, description);

        assertNotNull(actualRole);
        assertEquals(mockRoleDTO, actualRole);

        verify(roleRepository, times(1)).save(mockRole);
    }

    @Test
    @DisplayName("Get a Role by ID")
    void getRoleById() {
        int roleId = 1;
        String roleName = "admin";
        String description = "manage stuff";

        int userId = 1;
        String login = "root";
        String password = "password";
        User user = new User(userId, login, password);
        ArrayList<User> listUsers = new ArrayList<>(List.of(user));

        Role mockRole = new Role(roleId, roleName, description, listUsers);
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(mockRole));
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(roleName);
        mockRoleDTO.setDescription(description);

        RoleDTO actualRole = roleService.getRoleById(roleId);

        assertNotNull(actualRole);
        assertEquals(mockRoleDTO, actualRole);

        verify(roleRepository, times(2)).findById(roleId);
    }


    @Test
    @DisplayName("Get all Roles")
    void getAllRoles() {
        int roleId1 = 1;
        String roleName1 = "admin";
        String description1 = "manage stuff";

        Role mockRole1 = new Role(roleId1, roleName1, description1);

        int roleId2 = 2;
        String roleName2 = "user";
        String description2 = "do staff";

        Role mockRole2 = new Role(roleId2, roleName2, description2);
        List<Role> mockListRoles = new ArrayList<>(Arrays.asList(mockRole1, mockRole2));
        when(roleRepository.findAll()).thenReturn(mockListRoles);

        RoleDTO mockRoleDTO1 = new RoleDTO();
        mockRoleDTO1.setRoleName(roleName1);
        mockRoleDTO1.setDescription(description1);

        RoleDTO mockRoleDTO2 = new RoleDTO();
        mockRoleDTO2.setRoleName(roleName2);
        mockRoleDTO2.setDescription(description2);

        List<RoleDTO> mockListRoleDTOs = new ArrayList<>();
        mockListRoleDTOs.add(mockRoleDTO1);
        mockListRoleDTOs.add(mockRoleDTO2);

        List<RoleDTO> actualListRoles = roleService.getAllRoles();

        assertNotNull(actualListRoles);
        assertEquals(mockListRoleDTOs, actualListRoles);

        verify(roleRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Update a Role by ID")
    void updateRoleById() {
        int roleId = 1;
        String roleName = "admin";
        String description = "manage stuff";
        Role mockRole = new Role(roleId, roleName, description);

        String newRoleName = "user";
        String newDescription = "do staff";
        mockRole.setRoleName(newRoleName);
        mockRole.setDescription(newDescription);

        when(roleRepository.save(mockRole)).thenReturn(mockRole);
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(mockRole));
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(newRoleName);
        mockRoleDTO.setDescription(newDescription);

        RoleDTO actualRole = roleService.updateRoleById(roleId, newRoleName, newDescription);

        assertNotNull(actualRole);
        assertEquals(mockRoleDTO, actualRole);

        verify(roleRepository, times(1)).save(mockRole);
    }

    @Test
    @DisplayName("Delete a Role by ID with RoleNotFoundException")
    void deleteRoleByIdWithExcepction() {
        int roleId = 1;
        String expectedMessage = "Role with ID '1' can't be found";
        doThrow(new RoleNotFoundException(expectedMessage)).when(roleRepository)
                                                           .deleteById(roleId);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(new Role()));
        RoleNotFoundException exception = assertThrows(RoleNotFoundException.class, () -> roleService.deleteRoleById(roleId));

        assertEquals(expectedMessage, exception.getMessage());
    }
}