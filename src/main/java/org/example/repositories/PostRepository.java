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
    private static final String SQL_INSERT_POST = "INSERT INTO posts (text, likes, dislikes, user_id) VALUES(?, ?, ?, ?)";
    private static final String SQL_SELECT_POST_ID_BY_FIELDS = "SELECT id FROM posts WHERE text = ? AND likes = ? AND dislikes = ? AND user_id = ?";
    private static final String SQL_SELECT_POST_BY_ID = "SELECT * FROM posts WHERE id = ?";
    private static final String SQL_UPDATE_POST_BY_ID = "UPDATE posts SET text = ? WHERE id = ?";
    private static final String SQL_DELETE_POST_BY_ID = "DELETE FROM posts WHERE id = ?";

    public Post createPost(String text, int user_id) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtCreatePost = connection.prepareStatement(SQL_INSERT_POST)
        ) {
            prepStmtCreatePost.setString(1, text);
            prepStmtCreatePost.setInt(2, 0);
            prepStmtCreatePost.setInt(3, 0);
            prepStmtCreatePost.setInt(4, user_id);
            prepStmtCreatePost.executeUpdate();

            Post createdPost = this.findPostId(connection, text, user_id);

            return createdPost;
        }
    }

    private Post findPostId(Connection connection, String text, int user_id) throws SQLException {
        try (PreparedStatement prepStmtSelectCreatedPostId = connection.prepareStatement(SQL_SELECT_POST_ID_BY_FIELDS)) {
            prepStmtSelectCreatedPostId.setString(1, text);
            prepStmtSelectCreatedPostId.setInt(2, 0);
            prepStmtSelectCreatedPostId.setInt(3, 0);
            prepStmtSelectCreatedPostId.setInt(4, user_id);

            try (ResultSet rsFoundPostId = prepStmtSelectCreatedPostId.executeQuery()) {
                if (rsFoundPostId.next()) {
                    int postId = rsFoundPostId.getInt("id");

                    UserRepository userRepository = new UserRepository();
                    UserServiceImpl userService = new UserServiceImpl(userRepository);
                    User userOfPost = userService.getUserById(user_id);
                    Post foundPost = new Post(postId, text, userOfPost);

                    return foundPost;
                }
            }
        }

        return null;
    }

    public Post findPostById(int postId) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtSelectPostById = connection.prepareStatement(SQL_SELECT_POST_BY_ID)
        ) {
            prepStmtSelectPostById.setInt(1, postId);

            try (ResultSet rsFoundPost = prepStmtSelectPostById.executeQuery()) {
                if (rsFoundPost.next()) {
                    String text = rsFoundPost.getString("text");
                    int likes = rsFoundPost.getInt("likes");
                    int dislikes = rsFoundPost.getInt("dislikes");
                    int userId = rsFoundPost.getInt("user_id");

                    UserRepository userRepository = new UserRepository();
                    UserServiceImpl userService = new UserServiceImpl(userRepository);
                    User userOfPost = userService.getUserById(userId);
                    Post foundPost = new Post(postId, text, likes, dislikes, userOfPost);

                    return foundPost;
                }
            }
        }

        return null;
    }

    public Post findPostByIdWithoutUser(int postId) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtSelectPostById = connection.prepareStatement(SQL_SELECT_POST_BY_ID)
        ) {
            prepStmtSelectPostById.setInt(1, postId);

            try (ResultSet rsFoundPost = prepStmtSelectPostById.executeQuery()) {
                if (rsFoundPost.next()) {
                    String text = rsFoundPost.getString("text");
                    int likes = rsFoundPost.getInt("likes");
                    int dislikes = rsFoundPost.getInt("dislikes");
                    Post foundPost = new Post(postId, text, likes, dislikes);

                    return foundPost;
                }
            }
        }

        return null;
    }

    public Post updatePostById(int postId, String newText) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtUpdatePostById = connection.prepareStatement(SQL_UPDATE_POST_BY_ID)
        ) {
            prepStmtUpdatePostById.setString(1, newText);
            prepStmtUpdatePostById.setInt(2, postId);
            prepStmtUpdatePostById.executeUpdate();
            Post updatedPost = this.findPostById(postId);

            return updatedPost;
        }
    }

    public void deletePostById(int postId) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtDeletePostById = connection.prepareStatement(SQL_DELETE_POST_BY_ID)
        ) {
            prepStmtDeletePostById.setInt(1, postId);
            prepStmtDeletePostById.executeUpdate();
        }
    }
}
