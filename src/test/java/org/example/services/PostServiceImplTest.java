package org.example.services;

import org.example.db.DataSource;
import org.example.entities.Post;
import org.example.entities.User;
import org.example.exceptions.RoleException;
import org.example.exceptions.PostNotFoundException;
import org.example.repositories.PostRepository;
import org.example.repositories.PostRepositoryImpl;
import org.example.validators.PostValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceImplTest {
    @Mock
    private PostRepository postRepositoryImpl;

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
        when(postRepositoryImpl.createPost(text, userId)).thenReturn(mockPost);

        Post actualPost = postService.createPost(text, userId);

        assertNotNull(actualPost);
        assertEquals(mockPost, actualPost);

        verify(postRepositoryImpl, times(1)).createPost(text, userId);
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
        when(postRepositoryImpl.getPostById(postId)).thenReturn(mockPost);

        Post actualPost = postService.getPostById(postId);

        assertNotNull(actualPost);
        assertEquals(mockPost, actualPost);

        verify(postRepositoryImpl, times(1)).getPostById(postId);
    }

    @Test
    @DisplayName("Get a Post by ID if ID is < 0")
    void getPostByIdWhichIsLessThanZero() throws SQLException {
        int postId = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.getPostById(postId);
        });

        assertEquals("Post's ID can't be 0 or less", exception.getMessage());

        verify(postRepositoryImpl, times(0)).getPostById(postId);
    }

    @Test
    @DisplayName("Get a Post by ID if SQL command is wrong")
    void getPostByIdWithWrongSQL() throws SQLException {
        int nonExistentPostId = 999;

        DataSource mockDataSource = mock(DataSource.class);
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockDataSource.connect()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        PostRepositoryImpl postRepository = new PostRepositoryImpl(mockDataSource);
        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> {
            postRepository.getPostById(nonExistentPostId);
        });

        // Verify the exception message
        assertEquals("Post with ID '999' not found", exception.getMessage());

        // Verify the methods were called the expected number of times
        verify(mockDataSource, times(1)).connect();
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStatement, times(1)).setInt(1, nonExistentPostId);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
    }

    @Test
    @DisplayName("Get a Post by ID without its Users")
    void getPostByIdWithoutItsUser() throws SQLException {
        int postId = 1;
        String text = "Hello there";
        int likes = 0;
        int dislikes = 0;

        Post mockPost = new Post(postId, text, likes, dislikes);
        when(postRepositoryImpl.getPostByIdWithoutItsUser(postId)).thenReturn(mockPost);

        Post actualPost = postService.getPostByIdWithoutItsUser(postId);

        assertNotNull(actualPost);
        assertEquals(mockPost, actualPost);

        verify(postRepositoryImpl, times(1)).getPostByIdWithoutItsUser(postId);
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
        when(postRepositoryImpl.updatePostById(postId, newText)).thenReturn(mockPost);

        Post actualPost = postService.updatePostById(postId, newText);

        assertNotNull(actualPost);
        assertEquals(mockPost, actualPost);

        verify(postRepositoryImpl, times(1)).updatePostById(postId, newText);
    }

    @Test
    @DisplayName("Delete a Post by ID")
    void deletePostById() throws SQLException {
        int postId = 1;

        String expectedMessage = "Post with ID '1' can't be found";
        doThrow(new PostNotFoundException(expectedMessage)).when(postRepositoryImpl)
                                                           .deletePostById(postId);

        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> postService.deletePostById(postId));

        assertEquals(expectedMessage, exception.getMessage());
    }
}