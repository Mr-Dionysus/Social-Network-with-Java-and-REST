package org.example.controllers;

import org.example.dtos.PostDTO;
import org.example.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPost() {
        String text = "hello there";
        int likes = 0;
        int dislikes = 0;
        int userId = 1;

        PostDTO mockPostDTO = new PostDTO();
        mockPostDTO.setText(text);
        mockPostDTO.setLikes(likes);
        mockPostDTO.setDislikes(dislikes);

        when(postService.createPost(text, userId)).thenReturn(mockPostDTO);
        ResponseEntity<PostDTO> response = postController.createPost(userId, mockPostDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockPostDTO, response.getBody());
        verify(postService).createPost(text, userId);
    }

    @Test
    void getPostById() {
        int postId = 1;
        String text = "hello there";
        int likes = 0;
        int dislikes = 0;
        PostDTO mockPostDTO = new PostDTO(text, likes, dislikes);

        when(postService.getPostById(1)).thenReturn(mockPostDTO);
        ResponseEntity<PostDTO> response = postController.getPostById(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPostDTO, response.getBody());

        verify(postService).getPostById(postId);
    }

    @Test
    void updatePostById() {
        int postId = 1;
        String text = "hello there";
        int likes = 0;
        int dislikes = 0;

        PostDTO mockPostDTO = new PostDTO();
        mockPostDTO.setText(text);
        mockPostDTO.setLikes(likes);
        mockPostDTO.setDislikes(dislikes);

        when(postService.updatePostById(postId, text)).thenReturn(mockPostDTO);

        ResponseEntity<PostDTO> response = postController.updatePostById(postId, mockPostDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPostDTO, response.getBody());

        verify(postService).updatePostById(postId, text);
    }

    @Test
    void deletePostById() {
        int postId = 1;
        ResponseEntity<Void> response = postController.deletePostById(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(postService).deletePostById(postId);
    }
}