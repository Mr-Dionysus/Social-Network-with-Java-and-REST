package org.example.services;

import org.example.entities.Post;
import org.example.exceptions.*;
import org.example.repositories.PostRepository;
import org.example.repositories.PostRepositoryImpl;
import org.example.validators.PostValidator;
import org.example.validators.UserValidator;

import java.sql.SQLException;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepositoryImpl;

    public PostServiceImpl(PostRepository postRepositoryImpl) {
        this.postRepositoryImpl = postRepositoryImpl;
    }

    public static PostServiceImpl createPostService() {
        return new PostServiceImpl(new PostRepositoryImpl());
    }

    @Override
    public Post createPost(String text, int userId) {
        PostValidator.text(text);
        UserValidator.userId(userId);

        try {
            Post createdPost = postRepositoryImpl.createPost(text, userId);
            PostValidator.createdPost(createdPost, text);

            return createdPost;
        } catch (SQLException e) {
            throw new PostException("Error while creating a post", e);
        }
    }

    @Override
    public Post getPostById(int postId) {
        PostValidator.postId(postId);

        try {
            Post foundPost = postRepositoryImpl.getPostById(postId);
            PostValidator.foundPost(foundPost, postId);

            return foundPost;
        } catch (SQLException e) {
            throw new RoleException("Error while getting a post", e);
        }
    }

    @Override
    public Post getPostByIdWithoutItsUser(int postId) {
        PostValidator.postId(postId);

        try {
            Post foundPost = postRepositoryImpl.getPostByIdWithoutItsUser(postId);
            PostValidator.foundPost(foundPost, postId);

            return foundPost;
        } catch (SQLException e) {
            throw new RoleException("Error while getting a post without user", e);
        }
    }

    @Override
    public Post updatePostById(int postId, String newText) {
        PostValidator.postId(postId);
        PostValidator.text(newText);

        try {
            Post updatedPost = postRepositoryImpl.updatePostById(postId, newText);
            PostValidator.foundPost(updatedPost, postId);

            return updatedPost;
        } catch (SQLException e) {
            throw new RoleException("Error while updating a post", e);
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
            throw new RoleException("Error while deleting a post", e);
        }
    }
}
