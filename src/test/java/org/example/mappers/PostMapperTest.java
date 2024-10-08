package org.example.mappers;

import org.example.dtos.PostDTO;
import org.example.entities.Post;
import org.example.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class PostMapperTest {
    private PostMapper postMapper;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(PostMapperImpl.class);
        context.refresh();
        postMapper = context.getBean(PostMapper.class);
    }

    @Test
    @DisplayName("Post to PostDTO")
    void postToPostDTO() {
        String expectedText = "test text";
        int expectedLikes = 0;
        int expectedDislikes = 0;

        int expectedUserId = 1;
        String expectedLogin = "login";
        String expectedPassword = "password";
        User expectedUser = new User(expectedUserId, expectedLogin, expectedPassword);

        Post beforeDTOpost = new Post();
        beforeDTOpost.setText(expectedText);
        beforeDTOpost.setLikes(expectedLikes);
        beforeDTOpost.setDislikes(expectedDislikes);
        beforeDTOpost.setAuthor(expectedUser);

        PostDTO expectedPost = new PostDTO();
        expectedPost.setText(expectedText);
        expectedPost.setLikes(expectedLikes);
        expectedPost.setDislikes(expectedDislikes);

        PostDTO actualPost = postMapper.postToPostDTO(beforeDTOpost);

        assertEquals(expectedPost, actualPost);
    }
}