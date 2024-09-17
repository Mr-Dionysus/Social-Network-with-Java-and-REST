package org.example.repositories;

import org.example.connection.TestSQL;
import org.example.db.DataSource;
import org.example.db.UsersSQL;
import org.example.entities.Post;
import org.example.entities.Role;
import org.example.entities.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {
    private static MySQLContainer<?> mySQLcontainer;
    private static DataSource dataSource;
    public final UserRepository userRepository = new UserRepositoryImpl(dataSource);

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

    @Test
    @DisplayName("Create a User")
    void createUser() throws SQLException {
        String expectedLogin = "login";
        String expectedPassword = "password";
        User expectedUser = new User(3, expectedLogin, expectedPassword);

        User actualUser = userRepository.createUser(expectedLogin, expectedPassword);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("Find a User by ID")
    void getUserById() throws SQLException {
        String expectedLogin = "testLogin";
        String expectedPassword = "testPassword";
        ArrayList<Role> expectedRoles = new ArrayList<>();
        ArrayList<Post> expectedPosts = new ArrayList<>();

        int expectedPostId = 1;
        String expectedText = "test text";
        int expectedLikes = 0;
        int expectedDislikes = 0;
        Post expectedPost = new Post(expectedPostId, expectedText, expectedLikes, expectedDislikes, null);
        expectedPosts.add(expectedPost);
        User expectedUser = new User(1, expectedLogin, expectedPassword, expectedRoles, expectedPosts);

        User actualUser = userRepository.getUserById(1);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("Find a User without his Roles")
    void getUserWithoutHisRoles() throws SQLException {
        String expectedLogin = "testLogin";
        String expectedPassword = "testPassword";
        ArrayList<Role> expectedRoles = new ArrayList<>();
        ArrayList<Post> expectedPosts = new ArrayList<>();
        Post expectedPost = new Post(1, "test text", 0, 0, null);
        expectedPosts.add(expectedPost);
        User expectedUser = new User(1, expectedLogin, expectedPassword, expectedRoles, expectedPosts);

        User actualUser = userRepository.getUserById(1);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("Update a User")
    void updateUserById() throws SQLException {
        String expectedLogin = "testLogin2";
        String expectedPassword = "testPassword2";
        User expectedUser = new User(2, expectedLogin, expectedPassword);

        User actualUser = userRepository.updateUserById(2, expectedLogin, expectedPassword);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("Delete a User")
    void deleteUserById() throws SQLException {
        userRepository.deleteUserById(1);
        User actualUser = findTestUser();

        assertNull(actualUser);
    }

    private User findTestUser() throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectUserById =
                     connection.prepareStatement(UsersSQL.SELECT_BY_ID.getQuery())
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
        }

        return null;
    }
}