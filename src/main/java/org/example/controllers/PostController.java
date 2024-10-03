package org.example.controllers;

import org.example.dtos.PostDTO;
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

    @PostMapping("/{id}")
    public ResponseEntity<PostDTO> createPost(@PathVariable("id") int userId, @RequestBody PostDTO postDTO) {
        try {
            PostDTO createdPostDTO = postService.createPost(postDTO.getText(), userId);

            return new ResponseEntity<>(createdPostDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") int postId) {
        try {
            PostDTO foundPostDTO = postService.getPostById(postId);

            return new ResponseEntity<>(foundPostDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePostById(@PathVariable("id") int postId, @RequestBody PostDTO postDTO) {
        try {
            PostDTO updatedPostDTO = postService.updatePostById(postId, postDTO.getText());

            return new ResponseEntity<>(updatedPostDTO, HttpStatus.OK);
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
