package org.example.repositories;

import connection.MySQLtest;
import org.example.DataSource;
import org.example.entities.Post;
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
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostRepositoryTest {
    private static MySQLContainer<?> mySQLcontainer;
    private static DataSource dataSource;
    public PostRepository postRepository = new PostRepository(dataSource);

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
    void createPost() {
        try {
            String actualText = "Hello there";
            int actualUserId = 1;
            Post actualPost = postRepository.createPost(actualText, actualUserId);

            User expectedUser = findTestUser();
            Post testPost1 = new Post(1, "test text", null);
            Post testPost2 = new Post(2, "Hello there", null);
            ArrayList<Post> testListPosts = new ArrayList<>(Arrays.asList(testPost1, testPost2));
            expectedUser.setPosts(testListPosts);
            String expectedText = "Hello there";
            Post expectedPost = new Post(2, expectedText, expectedUser);

            assertEquals(expectedPost, actualPost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findPostById() {
        try {
            String expectedText = "test text";
            int expectedLikes = 0;
            int expectedDislikes = 0;
            User expectedUser = findTestUser();
            Post testPost1 = new Post(1, "test text", null);
            expectedUser.setPosts(new ArrayList<>(Arrays.asList(testPost1)));
            Post expectedPost = new Post(1, expectedText, expectedLikes, expectedDislikes, expectedUser);

            Post actualPost = postRepository.findPostById(1);

            assertEquals(expectedPost, actualPost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findPostByIdWithoutUser() {
        try {
            String expectedText = "test text";
            int expectedLikes = 0;
            int expectedDislikes = 0;
            Post expectedPost = new Post(1, expectedText, expectedLikes, expectedDislikes);

            Post actualPost = postRepository.findPostByIdWithoutUser(1);

            assertEquals(expectedPost, actualPost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updatePostById() {
        try {
            String expectedText = "test updated test";
            int expectedLikes = 0;
            int expectedDislikes = 0;
            User expectedUser = findTestUser();
            Post testPost1 = new Post(1, "test text", null);
            Post testPost2 = new Post(2, expectedText, null);
            expectedUser.setPosts(new ArrayList<>(Arrays.asList(testPost1, testPost2)));
            Post expectedPost = new Post(2, expectedText, expectedLikes, expectedDislikes, expectedUser);

            Post actualPost = postRepository.updatePostById(2, expectedText);

            assertEquals(expectedPost, actualPost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Post checkIfPostDeleted() {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectPostById = connection.prepareStatement(MySQLtest.SQL_SELECT_POST_BY_ID)
        ) {
            int postId = 2;
            prepStmtSelectPostById.setInt(1, postId);

            try (ResultSet rsFoundPost = prepStmtSelectPostById.executeQuery()) {
                if (rsFoundPost.next()) {
                    String text = rsFoundPost.getString("text");
                    int likes = rsFoundPost.getInt("likes");
                    int dislikes = rsFoundPost.getInt("dislikes");
                    Post foundPost = new Post(postId, text, likes, dislikes);

                    if (text == null & likes == 0 & dislikes == 0) {
                        return null;
                    } else {
                        return foundPost;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Test
    void deletePostById() {
        try {
            postRepository.deletePostById(2);
            Post actualPost = checkIfPostDeleted();
            Post expectedPost = null;

            assertEquals(expectedPost, actualPost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}