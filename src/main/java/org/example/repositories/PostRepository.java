package org.example.repositories;

import org.example.entities.Post;

import java.sql.SQLException;

public interface PostRepository {
    Post createPost(String text, int userId) throws SQLException;

    Post getPostById(int postId) throws SQLException;

    Post getPostByIdWithoutItsUser(int postId) throws SQLException;

    Post updatePostById(int postId, String newText) throws SQLException;

    void deletePostById(int postId) throws SQLException;
}
