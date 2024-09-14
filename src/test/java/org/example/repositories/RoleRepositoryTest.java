package org.example.repositories;

import connection.MySQLtest;
import org.example.DataSource;
import org.example.entities.Role;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
    public PostRepository postRepository = new PostRepository(dataSource);
    public UserRepository userRepository = new UserRepository(dataSource);
    public RoleRepository roleRepository = new RoleRepository(dataSource);

    @BeforeAll
    static void setUpContainer() {
        mySQLcontainer = new MySQLContainer<>("mysql:8.0");
        mySQLcontainer.start();

        DataSource.setTestConfiguration(mySQLcontainer.getJdbcUrl(), mySQLcontainer.getUsername(), mySQLcontainer.getPassword());
        dataSource = new DataSource();

        try (Connection connection = dataSource.connect()) {
            MySQLtest.createAllTablesWithTestEntities(connection, dataSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void tearDownContainer() {
        mySQLcontainer.stop();
    }

    @Test
    void createRole() {
        try {
            String expectedRoleName = "user";
            String expectedDescription = "read stuff";
            int expectedRoleId = 3;
            Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription);

            Role actualRole = roleRepository.createRole(expectedRoleName, expectedDescription);

            assertEquals(expectedRole, actualRole);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void readRole() {
        try {
            String expectedRoleName = "admin";
            String expectedDescription = "manage stuff";
            int expectedRoleId = 1;
            Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription);

            Role actualRole = roleRepository.readRole(expectedRoleId);

            assertEquals(expectedRole, actualRole);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void readRoleWithoutArray() {
        try {
            String expectedRoleName = "admin";
            String expectedDescription = "manage stuff";
            int expectedRoleId = 1;
            Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription);

            Role actualRole = roleRepository.readRole(expectedRoleId);

            assertEquals(expectedRole, actualRole);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void readAllRoles() {
        try {
            String expectedRoleName1 = "admin";
            String expectedDescription1 = "manage stuff";
            int expectedRoleId1 = 1;
            Role expectedRole1 = new Role(expectedRoleId1, expectedRoleName1, expectedDescription1);
            String expectedRoleName2 = "admin";
            String expectedDescription2 = "manage stuff";
            int expectedRoleId2 = 1;
            Role expectedRole2 = new Role(expectedRoleId2, expectedRoleName2, expectedDescription2);
            ArrayList<Role> expectedRoles = new ArrayList<>(Arrays.asList(expectedRole1));

            ArrayList<Role> actualRoles = roleRepository.readAllRoles();
            boolean areArraysEqual = true;

            for (int i = 0; i < expectedRoles.size(); i++) {
                if (!expectedRoles.get(i)
                                  .equals(actualRoles.get(i))) {
                    areArraysEqual = false;
                    break;
                }
            }

            assertTrue(areArraysEqual);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateRole() {
        try {
            String expectedRoleName = "hacker";
            String expectedDescription = "hack stuff";
            int expectedRoleId = 2;
            Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription);

            roleRepository.createRole(expectedRoleName, expectedDescription);
            Role actualRole = roleRepository.updateRole(expectedRoleId, expectedRoleName, expectedDescription);

            assertEquals(expectedRole, actualRole);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteRoleById() {
        try {
            Role expectedRole = null;
            int expectedRoleId = 2;
            roleRepository.deleteRoleById(expectedRoleId);

            Role actualRole = roleRepository.readRole(expectedRoleId);

            assertEquals(expectedRole, actualRole);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}