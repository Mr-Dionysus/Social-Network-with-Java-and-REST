package org.example.services;

import org.example.entities.Post;
import org.example.repositories.PostRepository;

import java.sql.SQLException;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(String text, int user_id) throws SQLException {
        Post createdPost = postRepository.createPost(text, user_id);
        return createdPost;
    }

    @Override
    public Post getPostById(int postId) throws SQLException {
        Post foundPost = postRepository.findPostById(postId);
        return foundPost;
    }

    @Override
    public Post getPostByIdWithoutUser(int postId) throws SQLException {
        Post foundPost = postRepository.findPostByIdWithoutUser(postId);
        return foundPost;
    }

    @Override
    public Post updatePostById(int postId, String newText) throws SQLException {
        Post updatedPost = postRepository.updatePostById(postId, newText);
        return updatedPost;
    }

    @Override
    public void deletePostById(int postId) throws SQLException {
        postRepository.deletePostById(postId);
    }
}
