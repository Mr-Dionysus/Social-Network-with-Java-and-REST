package org.example.repositories;

import org.example.DataSource;
import org.example.entities.Post;
import org.example.entities.User;
import org.example.services.UserServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRepository {
    private final UserRepository USER_REPOSITORY = new UserRepository();

    private final UserServiceImpl USER_SERVICE = new UserServiceImpl(USER_REPOSITORY);

    public Post createPost(String text, int user_id) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtCreatePost = connection.prepareStatement("INSERT INTO " + "posts (text, likes, dislikes, user_id) VALUES(?, ?, ?, ?)")
        ) {
            prepStmtCreatePost.setString(1, text);
            prepStmtCreatePost.setInt(2, 0);
            prepStmtCreatePost.setInt(3, 0);
            prepStmtCreatePost.setInt(4, user_id);
            prepStmtCreatePost.executeUpdate();
            int postId = -1;

            try (PreparedStatement prepStmtFindCreatedPostId = connection.prepareStatement("SELECT id FROM posts WHERE text = ? AND likes = ? AND dislikes = ? AND " + "user_id = ?")) {
                prepStmtFindCreatedPostId.setString(1, text);
                prepStmtFindCreatedPostId.setInt(2, 0);
                prepStmtFindCreatedPostId.setInt(3, 0);
                prepStmtFindCreatedPostId.setInt(4, user_id);
                ResultSet rsFoundPostId = prepStmtFindCreatedPostId.executeQuery();

                if (rsFoundPostId.next()) {
                    postId = rsFoundPostId.getInt("id");
                }

                rsFoundPostId.close();
            }

            User userOfPost = USER_SERVICE.getUserById(user_id);
            Post createdPost = new Post(postId, text, userOfPost);
            return createdPost;
        }
    }

    public Post findPostById(int postId) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtFindPostById = connection.prepareStatement("SELECT * FROM posts" + " WHERE id = ?")
        ) {
            prepStmtFindPostById.setInt(1, postId);
            String text = "";
            int likes = -1;
            int dislikes = -1;
            int userId = -1;
            User userOfPost = null;

            try (ResultSet rsFoundPost = prepStmtFindPostById.executeQuery()) {
                if (rsFoundPost.next()) {
                    text = rsFoundPost.getString("text");
                    likes = rsFoundPost.getInt("likes");
                    dislikes = rsFoundPost.getInt("dislikes");
                    userId = rsFoundPost.getInt("user_id");
                    userOfPost = USER_SERVICE.getUserById(userId);
                }
            }

            Post foundPost = new Post(postId, text, likes, dislikes, userOfPost);
            return foundPost;
        }
    }

    public Post findPostByIdWithoutUser(int postId) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtFindPostById = connection.prepareStatement("SELECT * FROM posts" + " WHERE id = ?")
        ) {
            prepStmtFindPostById.setInt(1, postId);
            String text = "";
            int likes = -1;
            int dislikes = -1;

            try (ResultSet rsFoundPost = prepStmtFindPostById.executeQuery()) {
                if (rsFoundPost.next()) {
                    text = rsFoundPost.getString("text");
                    likes = rsFoundPost.getInt("likes");
                    dislikes = rsFoundPost.getInt("dislikes");
                }
            }

            Post foundPost = new Post(postId, text, likes, dislikes);
            return foundPost;
        }
    }

    public Post updatePostById(int postId, String text) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtUpdatePostById = connection.prepareStatement("UPDATE posts SET " + "text = ? WHERE id = ?")
        ) {
            prepStmtUpdatePostById.setString(1, text);
            prepStmtUpdatePostById.setInt(2, postId);
            prepStmtUpdatePostById.executeUpdate();

            Post updatedPost = this.findPostById(postId);
            return updatedPost;
        }
    }

    public void deletePostById(int postId) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtDeletePostById = connection.prepareStatement("DELETE FROM posts" + " WHERE id = ?")
        ) {
            prepStmtDeletePostById.setInt(1, postId);
            prepStmtDeletePostById.executeUpdate();
        }
    }

}
