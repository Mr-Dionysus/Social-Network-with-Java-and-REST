package org.example.repositories;

import org.example.db.DataSource;
import org.example.db.PostsSQL;
import org.example.entities.Post;
import org.example.entities.User;
import org.example.services.UserServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRepository {
    private final DataSource dataSource;

    public PostRepository() {
        this.dataSource = new DataSource();
    }

    public PostRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Post createPost(String text, int user_id) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtCreatePost = connection.prepareStatement(PostsSQL.INSERT.getQuery())
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
        try (PreparedStatement prepStmtSelectCreatedPostId = connection.prepareStatement(PostsSQL.SELECT_POST_ID_BY_FIELDS.getQuery())) {
            prepStmtSelectCreatedPostId.setString(1, text);
            prepStmtSelectCreatedPostId.setInt(2, 0);
            prepStmtSelectCreatedPostId.setInt(3, 0);
            prepStmtSelectCreatedPostId.setInt(4, user_id);

            try (ResultSet rsFoundPostId = prepStmtSelectCreatedPostId.executeQuery()) {
                if (rsFoundPostId.next()) {
                    int postId = rsFoundPostId.getInt("id");

                    UserRepository userRepository = new UserRepository(dataSource);
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
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectPostById = connection.prepareStatement(PostsSQL.SELECT_BY_ID.getQuery())
        ) {
            prepStmtSelectPostById.setInt(1, postId);

            try (ResultSet rsFoundPost = prepStmtSelectPostById.executeQuery()) {
                if (rsFoundPost.next()) {
                    String text = rsFoundPost.getString("text");
                    int likes = rsFoundPost.getInt("likes");
                    int dislikes = rsFoundPost.getInt("dislikes");
                    int userId = rsFoundPost.getInt("user_id");

                    UserRepository userRepository = new UserRepository(dataSource);
                    UserServiceImpl userService = new UserServiceImpl(userRepository);
                    User userOfPost = userService.getUserById(userId);
                    Post foundPost = new Post(postId, text, likes, dislikes, userOfPost);

                    return foundPost;
                }
            }
        }

        return null;
    }

    public Post getPostByIdWithoutUser(int postId) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectPostById = connection.prepareStatement(PostsSQL.SELECT_BY_ID.getQuery())
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
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtUpdatePostById = connection.prepareStatement(PostsSQL.UPDATE_BY_ID.getQuery())
        ) {
            prepStmtUpdatePostById.setString(1, newText);
            prepStmtUpdatePostById.setInt(2, postId);
            prepStmtUpdatePostById.executeUpdate();
            Post updatedPost = this.findPostById(postId);

            return updatedPost;
        }
    }

    public void deletePostById(int postId) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtDeletePostById = connection.prepareStatement(PostsSQL.DELETE_BY_ID.getQuery())
        ) {
            prepStmtDeletePostById.setInt(1, postId);
            prepStmtDeletePostById.executeUpdate();
        }
    }
}
