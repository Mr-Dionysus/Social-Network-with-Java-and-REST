package org.example.controllers;

import org.example.dtos.PostDTO;
import org.example.entities.Post;
import org.example.mappers.PostMapper;
import org.example.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @PostMapping("/{id}")
    public ResponseEntity<PostDTO> createPost(@PathVariable("id") int userId, @RequestBody PostDTO postDTO) {
        try {
            Post createdPost = postService.createPost(postDTO.getText(), userId);
            postDTO = postMapper.postToPostDTO(createdPost);
            return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") int postId) {
        try {
            Post foundPost = postService.getPostById(postId);
            PostDTO postDTO = postMapper.postToPostDTO(foundPost);
            return new ResponseEntity<>(postDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePostById(@PathVariable("id") int postId, @RequestBody PostDTO postDTO) {
        try {
            Post updatedPost = postService.updatePostById(postId, postDTO.getText());
            postDTO = postMapper.postToPostDTO(updatedPost);
            return new ResponseEntity<>(postDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable("id") int postId) {
        try {
            postService.deletePostById(postId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
