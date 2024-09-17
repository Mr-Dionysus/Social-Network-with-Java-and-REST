package org.example.repositories;

import org.example.db.DataSource;
import org.example.db.PostsSQL;
import org.example.entities.Post;
import org.example.entities.User;
import org.example.exceptions.PostNotFoundException;
import org.example.services.UserServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRepositoryImpl implements PostRepository {
    private final DataSource dataSource;

    public PostRepositoryImpl() {
        this.dataSource = new DataSource();
    }

    public PostRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Post createPost(String text, int userId) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtCreatePost = connection.prepareStatement(PostsSQL.INSERT.getQuery())
        ) {
            prepStmtCreatePost.setString(1, text);
            prepStmtCreatePost.setInt(2, 0);
            prepStmtCreatePost.setInt(3, 0);
            prepStmtCreatePost.setInt(4, userId);
            prepStmtCreatePost.executeUpdate();

            Post createdPost = this.getPostId(connection, text, userId);

            return createdPost;
        }
    }

    private Post getPostId(Connection connection, String text, int user_id) throws SQLException {
        try (PreparedStatement prepStmtSelectCreatedPostId = connection.prepareStatement(PostsSQL.SELECT_POST_ID_BY_FIELDS.getQuery())) {
            prepStmtSelectCreatedPostId.setString(1, text);
            prepStmtSelectCreatedPostId.setInt(2, 0);
            prepStmtSelectCreatedPostId.setInt(3, 0);
            prepStmtSelectCreatedPostId.setInt(4, user_id);

            try (ResultSet rsFoundPostId = prepStmtSelectCreatedPostId.executeQuery()) {
                if (rsFoundPostId.next()) {
                    int postId = rsFoundPostId.getInt("id");

                    UserRepository userRepository = new UserRepositoryImpl(dataSource);
                    UserServiceImpl userService = new UserServiceImpl(userRepository);
                    User userOfPost = userService.getUserById(user_id);
                    Post foundPost = new Post(postId, text, userOfPost);

                    return foundPost;
                }
            }
        }

        throw new PostNotFoundException("Post ID not found");
    }

    public Post getPostById(int postId) throws SQLException {
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

                    UserRepository userRepository = new UserRepositoryImpl(dataSource);
                    UserServiceImpl userService = new UserServiceImpl(userRepository);
                    User userOfPost = userService.getUserById(userId);
                    Post foundPost = new Post(postId, text, likes, dislikes, userOfPost);

                    return foundPost;
                }
            }
        }
        throw new PostNotFoundException("Post with ID '" + postId + "' not found");
    }

    public Post getPostByIdWithoutItsUser(int postId) throws SQLException {
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

        throw new PostNotFoundException("Post with ID '" + postId + "' not found");
    }

    public Post updatePostById(int postId, String newText) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtUpdatePostById = connection.prepareStatement(PostsSQL.UPDATE_BY_ID.getQuery())
        ) {

            this.isPostFound(postId);

            prepStmtUpdatePostById.setString(1, newText);
            prepStmtUpdatePostById.setInt(2, postId);
            prepStmtUpdatePostById.executeUpdate();
            Post updatedPost = this.getPostById(postId);

            return updatedPost;
        }
    }

    private void isPostFound(int postId) throws SQLException {
        Post foundPost = this.getPostById(postId);

        if (foundPost == null) {
            throw new PostNotFoundException("Error while updating a Post. Can't find a Post " + "with ID '" + postId + "'.");
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
