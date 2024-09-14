package org.example.repositories;

import connection.MySQLtest;
import org.example.DataSource;
import org.example.entities.Post;
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

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private static MySQLContainer<?> mySQLcontainer;
    private static DataSource dataSource;
    public UserRepository userRepository = new UserRepository(dataSource);

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
    void createUser() {
        try {
            String expectedLogin = "login";
            String expectedPassword = "password";
            User expectedUser = new User(3, expectedLogin, expectedPassword);

            User actualUser = userRepository.createUser(expectedLogin, expectedPassword);

            assertEquals(expectedUser, actualUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void findUserById() {
        try {
            String expectedLogin = "testLogin";
            String expectedPassword = "testPassword";
            ArrayList<Role> expectedRoles = new ArrayList<>();
            ArrayList<Post> expectedPosts = new ArrayList<>();
            Post expectedPost = new Post(1, "test text", 0, 0, null);
            expectedPosts.add(expectedPost);
            User expectedUser = new User(1, expectedLogin, expectedPassword, expectedRoles, expectedPosts);

            User actualUser = userRepository.findUserById(1);

            assertEquals(expectedUser, actualUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findUserWithoutHisRoles() {
        try {
            String expectedLogin = "testLogin";
            String expectedPassword = "testPassword";
            ArrayList<Role> expectedRoles = new ArrayList<>();
            ArrayList<Post> expectedPosts = new ArrayList<>();
            Post expectedPost = new Post(1, "test text", 0, 0, null);
            expectedPosts.add(expectedPost);
            User expectedUser = new User(1, expectedLogin, expectedPassword, expectedRoles, expectedPosts);

            User actualUser = userRepository.findUserById(1);

            assertEquals(expectedUser, actualUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateUser() {
        try {
            String expectedLogin = "testLogin2";
            String expectedPassword = "testPassword2";
            ArrayList<Role> expectedRoles = new ArrayList<>();
            ArrayList<Post> expectedPosts = new ArrayList<>();
            User expectedUser = new User(2, expectedLogin, expectedPassword, expectedRoles, expectedPosts);

            User actualUser = userRepository.updateUser(2, expectedLogin, expectedPassword);

            assertEquals(expectedUser, actualUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    User findTestUser() {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectUserById = connection.prepareStatement(MySQLtest.SQL_SELECT_USER_BY_ID)
        ) {
            int userId = 1;
            prepStmtSelectUserById.setInt(1, userId);

            try (ResultSet rsSelectUserById = prepStmtSelectUserById.executeQuery()) {
                if (rsSelectUserById.next()) {
                    String login = rsSelectUserById.getString("login");
                    String password = rsSelectUserById.getString("password");
                    User user = new User(userId, login, password);

                    return user;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Test
    void deleteUser() {
        try {
            userRepository.deleteUser(1);
            User expectedUser = null;
            User actualUser = findTestUser();

            assertEquals(expectedUser, actualUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}