package org.example.services;

import org.example.dtos.PostDTO;

public interface PostService {
    PostDTO createPost(String text, int user_id);

    PostDTO getPostById(int postId);
//
//    PostDTO getPostByIdWithoutItsUser(int postId);

    PostDTO updatePostById(int postId, String newText);

    void deletePostById(int postId);
}
