package org.example.services;

import org.example.entities.Post;

import java.sql.SQLException;

public interface PostService {
    Post createPost(String text, int user_id) throws SQLException;

    Post getPostById(int postId) throws SQLException;

    Post getPostByIdWithoutUser(int postId) throws SQLException;

    Post updatePostById(int postId, String newText) throws SQLException;

    void deletePostById(int postId) throws SQLException;
}
