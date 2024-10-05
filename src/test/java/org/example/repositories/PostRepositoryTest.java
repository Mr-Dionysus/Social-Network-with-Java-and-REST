package org.example.repositories;

import org.example.connection.TestConfig;
import org.example.entities.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@EnableJpaRepositories(basePackages = "org.example.repositories")
@ActiveProfiles("test")
class PostRepositoryTest {

    @Container
    public static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0").withDatabaseName("testdb")
                                                                                      .withUsername("user")
                                                                                      .withPassword("password");

    @Autowired
    private PostRepository postRepository;

//    @BeforeEach
//    public void createTable() throws SQLException {
//        try (Connection connection = DriverManager.getConnection(mysqlContainer.getJdbcUrl(), mysqlContainer.getUsername(), mysqlContainer.getPassword())) {
//            connection.createStatement()
//                      .execute("""
//                              CREATE TABLE IF NOT EXISTS posts (
//                              id INT AUTO_INCREMENT PRIMARY KEY,
//                              text text not null,
//                              likes INT,
//                              dislikes INT,
//                              user_id INT not null,
//                              index idx_user_id (user_id)
//                              )
//                              """);
//        }
//    }

    @Test
    public void testFindByIdWithoutUser() throws SQLException {
        mysqlContainer.start();

        Post post = new Post();
        post.setText("First Post"); // Set the text for assertion
        postRepository.save(post);

        // Test custom repository method
        Post foundPost = postRepository.findByIdWithoutUser(1);
//        assertNotNull(foundPost);
//        assertEquals("First Post", foundPost.getText()); // Check the text

    }

}