package org.example.services;

import org.example.dtos.PostDTO;
import org.example.entities.Post;
import org.example.entities.User;
import org.example.exceptions.PostNotFoundException;
import org.example.mappers.PostMapper;
import org.example.repositories.PostRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create a Post")
    void createPost() {
        String text = "Hello there";
        int likes = 0;
        int dislikes = 0;

        int userId = 1;
        String login = "root";
        String password = "password";
        User mockUser = new User(userId, login, password);
        Post mockPost = new Post(text, mockUser);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(postRepository.save(mockPost)).thenReturn(mockPost);

        PostDTO mockPostDTO = new PostDTO();
        mockPostDTO.setText(text);
        mockPostDTO.setLikes(likes);
        mockPostDTO.setDislikes(dislikes);
        mockPostDTO.getLikes();
        mockPostDTO.getDislikes();
        mockPostDTO.getAuthor();
        mockPostDTO.toString();
        mockPostDTO.hashCode();

        when(postMapper.postToPostDTO(any(Post.class))).thenReturn(mockPostDTO);
        PostDTO actualPost = postService.createPost(text, userId);

        assertNotNull(actualPost);
        assertEquals(mockPostDTO, actualPost);

        verify(postRepository, times(1)).save(mockPost);
    }

    @Test
    @DisplayName("Get a Post by ID")
    void getPostById() {
        int postId = 1;
        String text = "Hello there";
        int likes = 0;
        int dislikes = 0;

        int userId = 1;
        String login = "root";
        String password = "password";
        User user = new User(userId, login, password);
        Post mockPost = new Post(postId, text, likes, dislikes, user);
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));

        PostDTO mockPostDTO = new PostDTO();
        mockPostDTO.setText(text);
        mockPostDTO.setLikes(likes);
        mockPostDTO.setDislikes(dislikes);

        when(postMapper.postToPostDTO(any(Post.class))).thenReturn(mockPostDTO);
        PostDTO actualPost = postService.getPostById(postId);

        assertNotNull(actualPost);
        assertEquals(mockPostDTO, actualPost);

        verify(postRepository, times(2)).findById(postId);
    }

    @Test
    @DisplayName("Update a Post by ID")
    void updatePostById() {
        int postId = 1;
        String text = "Hello there";
        int likes = 0;
        int dislikes = 0;
        Post mockPost = new Post(postId, text, likes, dislikes);

        String newText = "new text";
        mockPost.setText(newText);
        when(postRepository.save(mockPost)).thenReturn(mockPost);
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));

        PostDTO mockPostDTO = new PostDTO();
        mockPostDTO.setText(newText);
        mockPostDTO.setLikes(likes);
        mockPostDTO.setDislikes(dislikes);

        when(postMapper.postToPostDTO(any(Post.class))).thenReturn(mockPostDTO);
        PostDTO actualPost = postService.updatePostById(postId, newText);

        assertNotNull(actualPost);
        assertEquals(mockPostDTO, actualPost);

        verify(postRepository, times(1)).save(mockPost);
    }

    @Test
    @DisplayName("Delete a Post by ID")
    void deletePostById() {
        int postId = 1;

        Post mockPost = new Post();
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));
        String expectedMessage = "Error while deleting the Post. Post with ID '1' can't be found";
        doThrow(new PostNotFoundException(expectedMessage)).when(postRepository)
                                                           .deleteById(postId);

        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> postService.deletePostById(postId));

        assertEquals(expectedMessage, exception.getMessage());
    }

}
