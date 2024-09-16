package org.example.services;

import org.example.entities.Post;


public interface PostService {
    Post createPost(String text, int user_id);

    Post getPostById(int postId);

    Post getPostByIdWithoutItsUser(int postId);

    Post updatePostById(int postId, String newText);

    void deletePostById(int postId);
}
