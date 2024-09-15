package org.example.repositories;

import org.example.connection.MySQLtest;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersRolesRepositoryTest {
    private static MySQLContainer<?> mySQLcontainer;
    private static DataSource dataSource;
    public UsersRolesRepository usersRolesRepository = new UsersRolesRepository(dataSource);
    public RoleRepository roleRepository = new RoleRepository(dataSource);

    @BeforeAll
    static void setUpContainer() throws SQLException {
        mySQLcontainer = new MySQLContainer<>("mysql:8.0");
        mySQLcontainer.start();

        DataSource.setTestConfiguration(mySQLcontainer.getJdbcUrl(), mySQLcontainer.getUsername(), mySQLcontainer.getPassword());
        dataSource = new DataSource();

        try (Connection connection = dataSource.connect()) {
            MySQLtest.createAllTablesWithTestEntities(connection, dataSource);
        }
    }

    @AfterAll
    static void tearDownContainer() {
        mySQLcontainer.stop();
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

        usersRolesRepository.assignRoleToUser(expectedUserId, expectedRoleId);
        Role actualRole = roleRepository.getRoleById(expectedRoleId);

        assertEquals(expectedRole, actualRole);
    }
}