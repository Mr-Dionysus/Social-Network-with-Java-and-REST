package org.example.repositories;

import connection.MySQLtest;
import org.example.DataSource;
import org.example.entities.Role;
import org.example.entities.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleUsersRepositoryTest {
    private static MySQLContainer<?> mySQLcontainer;
    private static DataSource dataSource;
    public RoleRepository roleRepository = new RoleRepository(dataSource);
    public UsersRolesRepository usersRolesRepository = new UsersRolesRepository(dataSource);

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
    void assignRoleToUser() {
        try {
            String expectedRoleName = "admin";
            String expectedDescription = "manage stuff";
            int expectedRoleId = 1;
            int expectedUserId = 1;
            String expectedLogin = "testLogin";
            String expectedPassword = "testPassword";
            User expectedUser = new User(expectedUserId, expectedLogin, expectedPassword);
            ArrayList<User> expectedListUsers = new ArrayList<>(List.of(expectedUser));
            Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription, expectedListUsers);

            usersRolesRepository.assignRoleToUser(expectedUserId, expectedRoleId);
            Role actualRole = roleRepository.readRole(expectedRoleId);

            assertEquals(expectedRole, actualRole);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}