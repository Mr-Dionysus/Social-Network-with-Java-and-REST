package org.example.services;

import org.example.dtos.PostDTO;
import org.example.entities.Post;
import org.example.exceptions.*;
import org.example.mappers.PostMapper;
import org.example.mappers.PostMapperImpl;
import org.example.repositories.PostRepository;
import org.example.validators.PostValidator;
import org.example.validators.UserValidator;

import java.sql.SQLException;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepositoryImpl;
    private final PostMapper postMapper = new PostMapperImpl();

    public PostServiceImpl(PostRepository postRepositoryImpl) {
        this.postRepositoryImpl = postRepositoryImpl;
    }

    @Override
    public PostDTO createPost(String text, int userId) {
        PostValidator.text(text);
        UserValidator.userId(userId);

        try {
            Post createdPost = postRepositoryImpl.createPost(text, userId);
            PostValidator.createdPost(createdPost, text);
            PostDTO createdPostDTO = postMapper.postToPostDTO(createdPost);

            return createdPostDTO;
        } catch (SQLException e) {
            throw new PostException("Error while creating a post", e);
        }
    }

    @Override
    public PostDTO getPostById(int postId) {
        PostValidator.postId(postId);

        try {
            Post foundPost = postRepositoryImpl.getPostById(postId);
            PostValidator.foundPost(foundPost, postId);
            PostDTO foundPostDTO = postMapper.postToPostDTO(foundPost);

            return foundPostDTO;
        } catch (SQLException e) {
            throw new PostException("Error while getting a post", e);
        }
    }

    @Override
    public PostDTO getPostByIdWithoutItsUser(int postId) {
        PostValidator.postId(postId);

        try {
            Post foundPost = postRepositoryImpl.getPostByIdWithoutItsUser(postId);
            PostValidator.foundPost(foundPost, postId);
            PostDTO foundPostDTO = postMapper.postToPostDTO(foundPost);

            return foundPostDTO;
        } catch (SQLException e) {
            throw new PostException("Error while getting a post without user", e);
        }
    }

    @Override
    public PostDTO updatePostById(int postId, String newText) {
        PostValidator.postId(postId);
        PostValidator.text(newText);

        try {
            Post updatedPost = postRepositoryImpl.updatePostById(postId, newText);
            PostValidator.foundPost(updatedPost, postId);
            PostDTO updatedPostDTO = postMapper.postToPostDTO(updatedPost);

            return updatedPostDTO;
        } catch (SQLException e) {
            throw new PostException("Error while updating a post", e);
        }
    }

    @Override
    public void deletePostById(int postId) {
        PostValidator.postId(postId);

        if (this.getPostById(postId) == null) {
            throw new PostNotFoundException("Error while deleting post. Post with ID '" + postId + "' can't be found");
        }

        try {
            postRepositoryImpl.deletePostById(postId);
        } catch (SQLException e) {
            throw new PostException("Error while deleting a post", e);
        }
    }
}
