package org.example.repositories;

import org.example.connection.TestSQL;
import org.example.db.DataSource;
import org.example.entities.Role;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RoleRepositoryTest {
    private static MySQLContainer<?> mySQLcontainer;
    private static DataSource dataSource;
    public RoleRepository roleRepository = new RoleRepository(dataSource);

    @BeforeAll
    static void setUpContainer() throws SQLException {
        mySQLcontainer = new MySQLContainer<>("mysql:8.0");
        mySQLcontainer.start();

        DataSource.setTestConfiguration(mySQLcontainer.getJdbcUrl(), mySQLcontainer.getUsername(), mySQLcontainer.getPassword());
        dataSource = new DataSource();

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
    @DisplayName("Read a Role")
    void getRoleById() throws SQLException {
        String expectedRoleName = "admin";
        String expectedDescription = "manage stuff";
        int expectedRoleId = 1;
        Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription);

        Role actualRole = roleRepository.getRoleById(expectedRoleId);

        assertEquals(expectedRole, actualRole);
    }

    @Test
    @DisplayName("Read a Role without its Users")
    void getRoleByIdWithoutArray() throws SQLException {
        String expectedRoleName = "user";
        String expectedDescription = "read stuff";
        int expectedRoleId = 3;
        Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription);

        Role actualRole = roleRepository.getRoleById(expectedRoleId);

        assertEquals(expectedRole, actualRole);
    }

    @Test
    @DisplayName("Read all Roles")
    void getAllRoles() throws SQLException {
        Role expectedRole1 = this.createExpectedRole();
        int expectedRoleId2 = 2;
        String expectedRoleName2 = "hacker";
        String expectedDescription2 = "hack stuff";
        Role expectedRole2 = new Role(expectedRoleId2, expectedRoleName2, expectedDescription2);

        ArrayList<Role> expectedRoles = new ArrayList<>(Arrays.asList(expectedRole1, expectedRole2));

        ArrayList<Role> actualRoles = roleRepository.getAllRoles();
        boolean areArraysEqual = true;

        for (int i = 0; i < expectedRoles.size(); i++) {
            System.out.println(expectedRoles.get(i));
            System.out.println(actualRoles.get(i));
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

}