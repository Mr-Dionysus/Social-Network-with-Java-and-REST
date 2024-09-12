package org.example.services;

import org.example.entities.Post;
import org.example.repositories.PostRepository;

import java.sql.SQLException;

public class PostServiceImpl implements PostService {
    private final PostRepository POST_REPOSITORY;

    public PostServiceImpl(PostRepository POST_REPOSITORY) {
        this.POST_REPOSITORY = POST_REPOSITORY;
    }

    @Override
    public Post createPost(String text, int user_id) throws SQLException {
        Post createdPost = POST_REPOSITORY.createPost(text, user_id);
        return createdPost;
    }

    @Override
    public Post getPostById(int postId) throws SQLException {
        Post foundPost = POST_REPOSITORY.findPostById(postId);
        return foundPost;
    }

    @Override
    public Post getPostByIdWithoutUser(int postId) throws SQLException {
        Post foundPost = POST_REPOSITORY.findPostByIdWithoutUser(postId);
        return foundPost;
    }

    @Override
    public Post updatePostById(int postId, String newText) throws SQLException {
        Post updatedPost = POST_REPOSITORY.updatePostById(postId, newText);
        return updatedPost;
    }

    @Override
    public void deletePostById(int postId) throws SQLException {
        POST_REPOSITORY.deletePostById(postId);
    }
}
