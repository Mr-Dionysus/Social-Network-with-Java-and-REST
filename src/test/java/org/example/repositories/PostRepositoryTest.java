package org.example.repositories;

import org.example.config.TestConfig;
import org.example.entities.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@EnableJpaRepositories(basePackages = "org.example.repositories")
@ActiveProfiles("test")
class PostRepositoryTest {

    private final PostRepository postRepository;

    @Autowired
    public PostRepositoryTest(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Test
    @DisplayName("Create a Post")
    public void postPost() {
        Post expectedPost = new Post();
        expectedPost.setText("First Post");
        expectedPost.setLikes(0);
        expectedPost.setDislikes(0);
        expectedPost.getAuthor();
        expectedPost.toString();
        expectedPost.hashCode();
        Post actualPost = postRepository.save(expectedPost);

        assertEquals(expectedPost, actualPost);
    }

    @Test
    @DisplayName("Get a Post by ID")
    public void findById() {
        Post testPost = new Post();
        testPost.setText("First Post");
        testPost.setLikes(0);
        testPost.setDislikes(0);

        Post actualPost = postRepository.save(testPost);
        Post expectedPost = postRepository.findById(actualPost.getId())
                                          .get();

        assertEquals(expectedPost, actualPost);
    }

    @Test
    @DisplayName("Update a Post by ID")
    public void updateById() {
        Post testPost = new Post();
        testPost.setText("First Post");
        testPost.setLikes(0);
        testPost.setDislikes(0);

        Post actualPost = postRepository.save(testPost);
        actualPost.setText("Second Post");
        Post expectedPost = postRepository.save(actualPost);

        assertEquals(expectedPost, actualPost);
    }

    @Test
    @DisplayName("Delete a Post by ID")
    public void deleteById() {
        Post testPost = new Post();
        testPost.setText("First Post");
        testPost.setLikes(0);
        testPost.setDislikes(0);

        Post actualPost = postRepository.save(testPost);
        postRepository.deleteById(actualPost.getId());

        assertTrue(postRepository.findById(actualPost.getId())
                                 .isEmpty());
    }
}