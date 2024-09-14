package org.example.services;

import org.example.entities.Post;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    void createRole() throws SQLException {
        int roleId = 1;
        String roleName = "admin";
        String description = "manage stuff";

        Role mockRole = new Role(roleId, roleName, description);
        when(roleRepository.createRole(roleName, description)).thenReturn(mockRole);

        Role actualRole = roleService.createRole(roleName, description);

        assertNotNull(actualRole);
        assertEquals(mockRole, actualRole);

        verify(roleRepository, times(1)).createRole(roleName, description);
    }

    @Test
    @DisplayName("Get a Role by ID")
    void getRoleById() throws SQLException {
        int roleId = 1;
        String roleName = "admin";
        String description = "manage stuff";

        int userId = 1;
        String login = "root";
        String password = "password";
        User user = new User(userId, login, password);
        ArrayList<User> listUsers = new ArrayList<>(List.of(user));

        Role mockRole = new Role(roleId, roleName, description, listUsers);
        when(roleRepository.readRole(roleId)).thenReturn(mockRole);

        Role actualRole = roleService.getRoleById(roleId);

        assertNotNull(actualRole);
        assertEquals(mockRole, actualRole);

        verify(roleRepository, times(1)).readRole(roleId);
    }

    @Test
    @DisplayName("Get a Role by ID without its Users")
    void getRoleByIdWithoutUsers() throws SQLException {
        int roleId = 1;
        String roleName = "admin";
        String description = "manage stuff";

        Role mockRole = new Role(roleId, roleName, description);
        when(roleRepository.readRoleWithoutArray(roleId)).thenReturn(mockRole);

        Role actualRole = roleService.getRoleByIdWithoutUsers(roleId);

        assertNotNull(actualRole);
        assertEquals(mockRole, actualRole);

        verify(roleRepository, times(1)).readRoleWithoutArray(roleId);
    }

    @Test
    @DisplayName("Get all Roles")
    void getAllRoles() throws SQLException {
        int roleId1 = 1;
        String roleName1 = "admin";
        String description1 = "manage stuff";

        Role mockRole1 = new Role(roleId1, roleName1, description1);

        int roleId2 = 2;
        String roleName2 = "user";
        String description2 = "do staff";

        Role mockRole2 = new Role(roleId2, roleName2, description2);
        ArrayList<Role> mockListRoles = new ArrayList<>(Arrays.asList(mockRole1, mockRole2));
        when(roleRepository.readAllRoles()).thenReturn(mockListRoles);

        ArrayList<Role> actualListRoles = roleService.getAllRoles();

        assertNotNull(actualListRoles);
        assertEquals(mockListRoles, actualListRoles);

        verify(roleRepository, times(1)).readAllRoles();
    }

    @Test
    @DisplayName("Update a Role by ID")
    void updateRoleById() throws SQLException {
        int roleId = 1;
        String roleName = "admin";
        String description = "manage stuff";
        Role mockRole = new Role(roleId, roleName, description);

        String newRoleName = "user";
        String newDescription = "do staff";
        mockRole.setRoleName(newRoleName);
        mockRole.setDescription(newDescription);

        when(roleRepository.updateRole(roleId, newRoleName, newDescription)).thenReturn(mockRole);

        Role actualRole = roleService.updateRoleById(roleId, newRoleName, newDescription);

        assertNotNull(actualRole);
        assertEquals(mockRole, actualRole);

        verify(roleRepository, times(1)).updateRole(roleId, newRoleName, newDescription);
    }

    @Test
    @DisplayName("Delete a Role by ID")
    void deleteRoleById() throws SQLException {
        int roleId = 1;

        roleService.deleteRoleById(roleId);

        verify(roleRepository, times(1)).deleteRoleById(roleId);
    }
}