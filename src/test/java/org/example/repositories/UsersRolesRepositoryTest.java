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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersRolesRepositoryTest {
    private static MySQLContainer<?> mySQLcontainer;
    private static DataSource dataSource;
    public UserRepository userRepository = new UserRepository(dataSource);
    public UsersRolesRepository usersRolesRepository = new UsersRolesRepository(dataSource);
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

    ArrayList<Role> findListOfRoles(Connection connection, int userId) throws SQLException {
        try (PreparedStatement prepStmtSelectAllRoleIdsByUserId = connection.prepareStatement(MySQLtest.SQL_SELECT_ALL_ROLE_IDS_BY_USER_ID)) {
            prepStmtSelectAllRoleIdsByUserId.setInt(1, userId);

            try (ResultSet rsFoundAllRoleIds = prepStmtSelectAllRoleIdsByUserId.executeQuery()) {
                ArrayList<Role> listFoundRoles = new ArrayList<>();

                while (rsFoundAllRoleIds.next()) {
                    int roleId = rsFoundAllRoleIds.getInt("role_id");
                    Role foundRole = roleRepository.readRoleWithoutArray(roleId);
                    listFoundRoles.add(foundRole);
                }

                return listFoundRoles;
            }
        }
    }

    @Test
    void assignRoleToUser() {
        try (Connection connection = dataSource.connect()) {
            String expectedRoleName = "admin";
            String expectedDescription = "manage stuff";
            int expectedRoleId = 1;
            int expectedUserId = 1;
            String expectedLogin = "testLogin";
            String expectedPassword = "testPassword";
            Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription);
            ArrayList<Role> expectedListRoles = new ArrayList<>(List.of(expectedRole));
            User expectedUser = new User(expectedUserId, expectedLogin, expectedPassword, expectedListRoles);

            usersRolesRepository.assignRoleToUser(expectedUserId, expectedRoleId);
            User actualUser = userRepository.findUserWithoutHisRoles(expectedUserId);
            ArrayList<Role> actualListRoles = findListOfRoles(connection, expectedUserId);
            actualUser.setRoles(actualListRoles);

            assertEquals(expectedUser, actualUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}