package org.example.validators;

import org.example.entities.Post;
import org.example.exceptions.PostNotFoundException;

public class PostValidator {
    private PostValidator() {
        throw new IllegalStateException("Utility Class");
    }

    public static void postId(int postId) {
        if (postId <= 0) {
            throw new IllegalArgumentException("Post's ID can't be 0 or less");
        }
    }

    public static void foundPost(Post foundPost, int postId) {
        if (foundPost == null) {
            throw new PostNotFoundException("Post with ID '" + postId + "' can't be found");
        }
    }

    public static void createdPost(Post createdPost, String text) {
        if (createdPost == null) {
            throw new PostNotFoundException("Created Post with the text '" + text + "' can't " + "be found");
        }
    }

    public static void text(String text) {
        if (text == null || text.trim()
                                .isEmpty()) {
            throw new IllegalArgumentException("Post's text can't be null or empty");
        }
    }
}
