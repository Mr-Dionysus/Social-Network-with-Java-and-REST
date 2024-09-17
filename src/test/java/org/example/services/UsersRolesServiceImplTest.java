package org.example.services;

import org.example.entities.Role;
import org.example.entities.User;
import org.example.repositories.RoleRepository;
import org.example.repositories.RoleRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersRolesServiceImplTest {
    @Mock
    private RoleRepository roleRepositoryImpl;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Assign a Role to a User")
    void assignRoleToUser() throws SQLException {
        int roleId = 1;
        String roleName = "admin";
        String description = "manage stuff";
        ArrayList<User> listUsers = new ArrayList<>();
        Role mockRole = new Role(roleId, roleName, description);

        int userId = 1;
        String login = "root";
        String password = "password";
        ArrayList<Role> listRoles = new ArrayList<>();
        User mockUser = new User(userId, login, password);

        listUsers.add(mockUser);
        mockRole.setUsers(listUsers);
        listRoles.add(mockRole);
        mockUser.setRoles(listRoles);

        roleService.assignRoleToUser(userId, roleId);

        assertNotNull(mockRole);
        assertNotNull(mockUser);

        verify(roleRepositoryImpl, times(1)).assignRoleToUser(userId, roleId);
    }
}