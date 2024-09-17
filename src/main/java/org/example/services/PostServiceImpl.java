package org.example.services;

import org.example.entities.Post;
import org.example.exceptions.*;
import org.example.repositories.PostRepository;
import org.example.validators.PostValidator;
import org.example.validators.UserValidator;

import java.sql.SQLException;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public static PostServiceImpl createPostService() {
        return new PostServiceImpl(new PostRepository());
    }

    @Override
    public Post createPost(String text, int userId) {
        PostValidator.text(text);
        UserValidator.userId(userId);

        try {
            Post createdPost = postRepository.createPost(text, userId);
            PostValidator.createdPost(createdPost, text);

            return createdPost;
        } catch (SQLException e) {
            throw new CreatePostException("Error while creating a post", e);
        }
    }

    @Override
    public Post getPostById(int postId) {
        PostValidator.postId(postId);

        try {
            Post foundPost = postRepository.getPostById(postId);
            PostValidator.foundPost(foundPost, postId);

            return foundPost;
        } catch (SQLException e) {
            throw new GetPostException("Error while getting a post", e);
        }
    }

    @Override
    public Post getPostByIdWithoutItsUser(int postId) {
        PostValidator.postId(postId);

        try {
            Post foundPost = postRepository.getPostByIdWithoutItsUser(postId);
            PostValidator.foundPost(foundPost, postId);

            return foundPost;
        } catch (SQLException e) {
            throw new GetPostException("Error while getting a post without user", e);
        }
    }

    @Override
    public Post updatePostById(int postId, String newText) {
        PostValidator.postId(postId);
        PostValidator.text(newText);

        try {
            Post updatedPost = postRepository.updatePostById(postId, newText);
            PostValidator.foundPost(updatedPost, postId);

            return updatedPost;
        } catch (SQLException e) {
            throw new UpdatePostException("Error while updating a post", e);
        }
    }

    @Override
    public void deletePostById(int postId) {
        PostValidator.postId(postId);

        if (this.getPostById(postId) == null) {
            throw new PostNotFoundException("Error while deleting post. Post with ID '" + postId + "' can't be found");
        }

        try {
            postRepository.deletePostById(postId);
        } catch (SQLException e) {
            throw new DeletePostException("Error while deleting a post", e);
        }
    }
}
