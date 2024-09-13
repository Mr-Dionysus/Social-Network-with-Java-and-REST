package org.example.repositories;

import org.example.DataSource;
import org.example.entities.Post;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class PostRepositoryTest {
    private static MySQLContainer<?> mySQLcontainer;
    private static DataSource dataSource;
    public PostRepository postRepository = new PostRepository(dataSource);

    @BeforeAll
    static void setUpContainer() {
        mySQLcontainer = new MySQLContainer<>("mysql:8.0");
        mySQLcontainer.start();
    }

    @AfterAll
    static void tearDownContainer() {
        mySQLcontainer.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        DataSource.setTestConfiguration(mySQLcontainer.getJdbcUrl(), mySQLcontainer.getUsername(), mySQLcontainer.getPassword());
        dataSource = new DataSource();

        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtCreateTableUsers = connection.prepareStatement("""
                     CREATE TABLE IF NOT EXISTS users (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     login tinytext not null,
                     password tinytext not null,
                     unique key (login(25))
                     )
                     """);
             PreparedStatement prepStmtCreateTableRoles = connection.prepareStatement("""
                     CREATE TABLE IF NOT EXISTS roles (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     role tinytext not null,
                     description text not null,
                     unique key (role(25))
                     )
                     """);
             PreparedStatement prepStmtCreateTablePosts = connection.prepareStatement("""
                     CREATE TABLE IF NOT EXISTS posts (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     text text not null,
                     likes INT,
                     dislikes INT,
                     user_id INT not null,
                     index idx_user_id (user_id)
                     )
                     """);
             PreparedStatement prepStmtCreateTableUsersRoles = connection.prepareStatement("""
                     CREATE TABLE IF NOT EXISTS users_roles (
                     user_id INT,
                     role_id INT,
                     PRIMARY KEY (user_id, role_id),
                     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                     FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
                     )
                     """)
        ) {
            prepStmtCreateTableUsers.executeUpdate();
            prepStmtCreateTableRoles.executeUpdate();
            prepStmtCreateTablePosts.executeUpdate();
            prepStmtCreateTableUsersRoles.executeUpdate();
        }
    }

    @Test
    void createPost() throws SQLException {
        String actualText = "Hey";
        int actualUserId = 127;
        Post actualPost = postRepository.createPost(actualText, actualUserId);
        System.out.println(actualPost.getText());
    }

    @Test
    void findPostById() {
    }

    @Test
    void findPostByIdWithoutUser() {
    }

    @Test
    void updatePostById() {
    }

    @Test
    void deletePostById() {
    }
}