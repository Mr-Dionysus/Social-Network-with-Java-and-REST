package org.example.repositories;

import org.example.builder.GenericBuilder;
import org.example.db.TestSQL;
import org.example.db.DataSource;
import org.example.db.PostsSQL;
import org.example.db.UsersSQL;
import org.example.entities.Post;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostRepositoryTest {
    private static MySQLContainer<?> mySQLcontainer;
    private static DataSource dataSource;
    public PostRepository postRepository = new PostRepository(dataSource);

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

    private User findTestUser() throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectUserById = connection.prepareStatement(UsersSQL.SELECT_BY_ID.getQuery())
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

    private Post createExpectedPost(int firstOrSecond) {
        if (firstOrSecond == 1) {
            int postId = 1;
            String expectedText = "test text";
            int expectedLikes = 0;
            int expectedDislikes = 0;
            Post expectedPost = new Post(postId, expectedText, expectedLikes, expectedDislikes);

            return expectedPost;
        } else if (firstOrSecond == 2) {
            int postId = 2;
            String expectedText = "Hello there";
            int expectedLikes = 0;
            int expectedDislikes = 0;
            Post expectedPost = new Post(postId, expectedText, expectedLikes, expectedDislikes);

            return expectedPost;
        }

        return null;
    }

    @Test
    @DisplayName("Create a Post")
    void createPost() throws SQLException {
        Post expectedPost1 = this.createExpectedPost(1);
        Post expectedPost2 = this.createExpectedPost(2);
        User expectedUser = this.findTestUser();

        int expectedPostId = 2;
        String expectedText = expectedPost2.getText();
        int expectedLikes = 0;
        int expectedDislikes = 0;
        int expectedUserId = expectedUser.getId();

        Post actualPost = postRepository.createPost(expectedText, expectedUserId);
        ArrayList<Post> testListPosts = new ArrayList<>();
        testListPosts.add(expectedPost1);
        testListPosts.add(expectedPost2);
        expectedUser.setPosts(testListPosts);

        Post expectedPost = new Post(expectedPostId, expectedText, expectedLikes, expectedDislikes, expectedUser);

        assertEquals(expectedPost, actualPost);
    }

    @Test
    @DisplayName("Find a Post by ID")
    void getPostById() throws SQLException {
        User expectedUser = this.findTestUser();
        Post testPost = this.createExpectedPost(1);
        expectedUser.setPosts(new ArrayList<>(List.of(testPost)));
        Post expectedPost = this.createExpectedPost(1);
        expectedPost.setAuthor(expectedUser);

        Post actualPost = postRepository.getPostById(1);

        assertEquals(expectedPost, actualPost);
    }

    @Test
    @DisplayName("Find a Post by ID without its Users")
    void getPostByIdWithoutItsUser() throws SQLException {
        Post expectedPost = this.createExpectedPost(1);
        int expectedPostId = expectedPost.getId();

        Post actualPost = postRepository.getPostByIdWithoutItsUser(expectedPostId);

        assertEquals(expectedPost, actualPost);
    }

    @Test
    @DisplayName("Update a Post by ID")
    void updatePostById() throws SQLException {
        User expectedUser = this.findTestUser();
        Post testPost1 = this.createExpectedPost(1);
        String expectedText = "test updated test";
        int expectedLikes = 0;
        int expectedDislikes = 0;
//        Post testPost2 = GenericBuilder.of(Post)
        Post testPost2 = new Post(2, expectedText, null);
        expectedUser.setPosts(new ArrayList<>(Arrays.asList(testPost1, testPost2)));
        Post expectedPost = new Post(2, expectedText, expectedLikes, expectedDislikes, expectedUser);

        Post actualPost = postRepository.updatePostById(2, expectedText);

        assertEquals(expectedPost, actualPost);
    }

    private Post checkIfPostDeleted() throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectPostById = connection.prepareStatement(PostsSQL.SELECT_BY_ID.getQuery())
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
        }

        return null;
    }

    @Test
    @DisplayName("Delete a Post by ID")
    void deletePostById() throws SQLException {
        postRepository.deletePostById(2);
        Post actualPost = this.checkIfPostDeleted();
        Post expectedPost = null;

        assertEquals(expectedPost, actualPost);
    }
}