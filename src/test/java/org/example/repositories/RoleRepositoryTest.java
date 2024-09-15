package org.example.repositories;

import org.example.connection.TestSQL;
import org.example.db.DataSource;
import org.example.entities.Role;
import org.example.entities.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleRepositoryTest {
    private static MySQLContainer<?> mySQLcontainer;
    private static DataSource dataSource;
    public RoleRepository roleRepository = new RoleRepository(dataSource);

    @BeforeAll
    static void setUpContainer() throws SQLException {
        mySQLcontainer = new MySQLContainer<>("mysql:8.0");
        mySQLcontainer.start();
        dataSource = new DataSource(mySQLcontainer.getJdbcUrl(), mySQLcontainer.getUsername(), mySQLcontainer.getPassword());



        try (Connection connection = dataSource.connect()) {
            TestSQL.createAllTablesWithTestEntities(connection, dataSource);
        }
    }

    @AfterAll
    static void tearDownContainer() {
        mySQLcontainer.stop();
    }

    private Role createExpectedRole() {
        String expectedRoleName = "admin";
        String expectedDescription = "manage stuff";
        int expectedRoleId = 1;
        Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription);

        return expectedRole;
    }

    @Test
    @DisplayName("Create a Role")
    void createRole() throws SQLException {
        String expectedRoleName = "user";
        String expectedDescription = "read stuff";
        int expectedRoleId = 3;
        Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription);

        Role actualRole = roleRepository.createRole(expectedRoleName, expectedDescription);

        assertEquals(expectedRole, actualRole);
    }

    @Test
    @DisplayName("Get a Role")
    void getRoleById() throws SQLException {
        String expectedRoleName = "admin";
        String expectedDescription = "manage stuff";
        int expectedRoleId = 1;
        Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription);

        int expectedUserId = 1;
        String expectedLogin = "testLogin";
        String expectedPassword = "testPassword";
        User expectedUser = new User(expectedUserId, expectedLogin, expectedPassword);
        ArrayList<User> expectedListUsers = new ArrayList<>();
        expectedListUsers.add(expectedUser);
        expectedRole.setUsers(expectedListUsers);

        Role actualRole = roleRepository.getRoleById(expectedRoleId);

        assertEquals(expectedRole, actualRole);
    }

    @Test
    @DisplayName("Get a Role without its Users")
    void getRoleByIdWithoutArray() throws SQLException {
        int expectedRoleId = 1;
        Role expectedRole = this.createExpectedRole();

        Role actualRole = roleRepository.getRoleById(expectedRoleId);

        assertEquals(expectedRole, actualRole);
    }

    @Test
    @DisplayName("Get all Roles")
    void getAllRoles() throws SQLException {
        Role expectedRole1 = this.createExpectedRole();
        ArrayList<Role> expectedRoles = new ArrayList<>(List.of(expectedRole1));
        ArrayList<Role> actualRoles =  (ArrayList<Role>) roleRepository.getAllRoles();
        boolean areArraysEqual = true;

        for (int i = 0; i < expectedRoles.size(); i++) {
            if (!expectedRoles.get(i)
                              .equals(actualRoles.get(i))) {
                areArraysEqual = false;
                break;
            }
        }

        assertTrue(areArraysEqual);
    }

    @Test
    @DisplayName("Update a Role")
    void updateRoleById() throws SQLException {
        int expectedRoleId = 2;
        String expectedRoleName = "hacker";
        String expectedDescription = "hack stuff";
        Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription);

        roleRepository.createRole(expectedRoleName, expectedDescription);
        Role actualRole = roleRepository.updateRoleById(expectedRoleId, expectedRoleName, expectedDescription);

        assertEquals(expectedRole, actualRole);
    }

    @Test
    @DisplayName("Delete a Role by ID")
    void deleteRoleById() throws SQLException {
        int expectedRoleId = 2;
        roleRepository.deleteRoleById(expectedRoleId);

        Role actualRole = roleRepository.getRoleById(expectedRoleId);

        assertNull(actualRole);
    }

    @Test
    @DisplayName("Assign a Role to a User")
    void assignRoleToUser() throws SQLException {
        int expectedRoleId = 1;
        String expectedRoleName = "admin";
        String expectedDescription = "manage stuff";

        int expectedUserId = 1;
        String expectedLogin = "testLogin";
        String expectedPassword = "testPassword";

        User expectedUser = new User(expectedUserId, expectedLogin, expectedPassword);
        ArrayList<User> expectedListUsers = new ArrayList<>(List.of(expectedUser));
        Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription, expectedListUsers);

        roleRepository.assignRoleToUser(expectedUserId, expectedRoleId);
        Role actualRole = roleRepository.getRoleById(expectedRoleId);

        assertEquals(expectedRole, actualRole);
    }
}