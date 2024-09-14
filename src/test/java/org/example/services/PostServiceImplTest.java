package org.example.services;

import org.example.entities.Post;
import org.example.entities.User;
import org.example.exceptions.PostNotFoundException;
import org.example.exceptions.UserNotFoundException;
import org.example.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create a Post")
    void createPost() throws SQLException {
        int postId = 1;
        String text = "Hello there";
        int likes = 0;
        int dislikes = 0;

        int userId = 1;
        String login = "root";
        String password = "password";
        User user = new User(userId, login, password);
        Post mockPost = new Post(postId, text, likes, dislikes, user);
        when(postRepository.createPost(text, userId)).thenReturn(mockPost);

        Post actualPost = postService.createPost(text, userId);

        assertNotNull(actualPost);
        assertEquals(mockPost, actualPost);

        verify(postRepository, times(1)).createPost(text, userId);
    }

    @Test
    @DisplayName("Get a Post by ID")
    void getPostById() throws SQLException {
        int postId = 1;
        String text = "Hello there";
        int likes = 0;
        int dislikes = 0;

        int userId = 1;
        String login = "root";
        String password = "password";
        User user = new User(userId, login, password);
        Post mockPost = new Post(postId, text, likes, dislikes, user);
        when(postRepository.findPostById(postId)).thenReturn(mockPost);

        Post actualPost = postService.getPostById(postId);

        assertNotNull(actualPost);
        assertEquals(mockPost, actualPost);

        verify(postRepository, times(1)).findPostById(postId);
    }

    @Test
    @DisplayName("Get a Post by ID without its Users")
    void getPostByIdWithoutUser() throws SQLException {
        int postId = 1;
        String text = "Hello there";
        int likes = 0;
        int dislikes = 0;

        Post mockPost = new Post(postId, text, likes, dislikes);
        when(postRepository.findPostByIdWithoutUser(postId)).thenReturn(mockPost);

        Post actualPost = postService.getPostByIdWithoutUser(postId);

        assertNotNull(actualPost);
        assertEquals(mockPost, actualPost);

        verify(postRepository, times(1)).findPostByIdWithoutUser(postId);
    }

    @Test
    @DisplayName("Update a Post by ID")
    void updatePostById() throws SQLException {
        int postId = 1;
        String text = "Hello there";
        int likes = 0;
        int dislikes = 0;
        Post mockPost = new Post(postId, text, likes, dislikes);

        String newText = "new text";
        mockPost.setText(newText);
        when(postRepository.updatePostById(postId, newText)).thenReturn(mockPost);

        Post actualPost = postService.updatePostById(postId, newText);

        assertNotNull(actualPost);
        assertEquals(mockPost, actualPost);

        verify(postRepository, times(1)).updatePostById(postId, newText);
    }

    @Test
    @DisplayName("Delete a Post by ID")
    void deletePostById() throws SQLException {
        int postId = 1;

        String expectedMessage = "Post with ID '1' can't be found";
        doThrow(new PostNotFoundException(expectedMessage)).when(postRepository)
                                                           .deletePostById(postId);

        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> {
            postService.deletePostById(postId);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}