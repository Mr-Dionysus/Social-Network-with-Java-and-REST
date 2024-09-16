package org.example.repositories;

import org.example.db.DataSource;
import org.example.db.PostsSQL;
import org.example.db.UsersRolesSQL;
import org.example.db.UsersSQL;
import org.example.entities.Post;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.exceptions.UserNotFoundException;
import org.example.services.PostServiceImpl;
import org.example.services.RoleServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepository {
    private final DataSource dataSource;

    public UserRepository() {
        this.dataSource = new DataSource();
    }

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void isUserFound(int userId) throws SQLException {
        User foundUser = this.getUserById(userId);

        if (foundUser == null) {
            throw new UserNotFoundException("Error while updating a User. Can't find a User " + "with ID '" + userId + "'.");
        }
    }

    public User createUser(String login, String password) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtInsertUser = connection.prepareStatement(UsersSQL.INSERT.getQuery())
        ) {
            prepStmtInsertUser.setString(1, login);
            prepStmtInsertUser.setString(2, password);
            prepStmtInsertUser.executeUpdate();

            User foundUser = this.getUserByLogin(login, password, connection);

            return foundUser;
        }
    }

    private User getUserByLogin(String login, String password, Connection connection) throws SQLException {
        try (PreparedStatement prepStmtSelectUserIdByLogin = connection.prepareStatement(UsersSQL.SELECT_USER_ID_BY_LOGIN.getQuery())) {

            prepStmtSelectUserIdByLogin.setString(1, login);

            try (ResultSet rsFoundUser = prepStmtSelectUserIdByLogin.executeQuery()) {
                if (rsFoundUser.next()) {
                    int userId = rsFoundUser.getInt("id");
                    User foundUser = new User(userId, login, password);

                    return foundUser;
                }
            }
        }
        throw new UserNotFoundException("User with a login '" + login + "' can't be created");
    }

    public User getUserById(int userId) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectUserById = connection.prepareStatement(UsersSQL.SELECT_BY_ID.getQuery())
        ) {
            prepStmtSelectUserById.setInt(1, userId);

            try (ResultSet rsFoundUser = prepStmtSelectUserById.executeQuery()) {
                if (rsFoundUser.next()) {
                    String login = rsFoundUser.getString("login");
                    String password = rsFoundUser.getString("password");

                    ArrayList<Role> listFoundRoles = this.getListOfRoles(connection, userId);
                    ArrayList<Post> listFoundPosts = this.getListOfPosts(connection, userId);

                    User foundUser = new User(userId, login, password, listFoundRoles, listFoundPosts);

                    return foundUser;
                }
            }
        }
        throw new UserNotFoundException("User with ID '" + userId + "' isn't found");
    }

    private ArrayList<Role> getListOfRoles(Connection connection, int userId) throws SQLException {
        try (PreparedStatement prepStmtSelectAllRoleIdsByUserId = connection.prepareStatement(UsersRolesSQL.SELECT_ALL_ROLE_IDS_BY_USER_ID.getQuery())) {
            prepStmtSelectAllRoleIdsByUserId.setInt(1, userId);

            try (ResultSet rsFoundAllRoleIds = prepStmtSelectAllRoleIdsByUserId.executeQuery()) {
                ArrayList<Role> listFoundRoles = new ArrayList<>();

                while (rsFoundAllRoleIds.next()) {
                    int roleId = rsFoundAllRoleIds.getInt("role_id");
                    RoleRepository roleRepository = new RoleRepository(dataSource);
                    RoleServiceImpl roleService = new RoleServiceImpl(roleRepository);
                    Role foundRole = roleService.getRoleByIdWithoutItsUsers(roleId);
                    listFoundRoles.add(foundRole);
                }

                return listFoundRoles;
            }
        }
    }

    private ArrayList<Post> getListOfPosts(Connection connection, int userId) throws SQLException {
        try (PreparedStatement prepStmtSelectPostIdByUserId = connection.prepareStatement(PostsSQL.SELECT_POST_ID_BY_USER_ID.getQuery())) {
            prepStmtSelectPostIdByUserId.setInt(1, userId);

            try (ResultSet rsFoundAllPostIds = prepStmtSelectPostIdByUserId.executeQuery()) {
                PostRepository postRepository = new PostRepository(dataSource);
                PostServiceImpl postService = new PostServiceImpl(postRepository);
                ArrayList<Post> listFoundPosts = new ArrayList<>();

                while (rsFoundAllPostIds.next()) {
                    int postId = rsFoundAllPostIds.getInt("id");
                    Post foundPost = postService.getPostByIdWithoutItsUser(postId);
                    listFoundPosts.add(foundPost);
                }

                return listFoundPosts;
            }
        }
    }

    public User getUserWithoutHisRoles(int userId) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectUserById = connection.prepareStatement(UsersSQL.SELECT_BY_ID.getQuery())
        ) {
            prepStmtSelectUserById.setInt(1, userId);

            try (ResultSet rsFoundUser = prepStmtSelectUserById.executeQuery()) {
                if (rsFoundUser.next()) {
                    String login = rsFoundUser.getString("login");
                    String password = rsFoundUser.getString("password");
                    User foundUser = new User(userId, login, password);

                    return foundUser;
                }
            }
        }
        throw new UserNotFoundException("User with ID '" + userId + "' isn't found");
    }

    public User updateUserById(int userId, String newLogin, String newPassword) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtUpdateUserById = connection.prepareStatement(UsersSQL.SQL_UPDATE_USER_BY_ID.getQuery())
        ) {
            isUserFound(userId);

            prepStmtUpdateUserById.setString(1, newLogin);
            prepStmtUpdateUserById.setString(2, newPassword);
            prepStmtUpdateUserById.setInt(3, userId);
            prepStmtUpdateUserById.executeUpdate();

            User foundUser = new User(userId, newLogin, newPassword);

            return foundUser;
        }
    }

    public void deleteUserById(int userId) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtDeleteUserById = connection.prepareStatement(UsersSQL.SQL_DELETE_USER_BY_ID.getQuery())
        ) {
            this.deleteAllPostsByUserId(connection, userId);

            prepStmtDeleteUserById.setInt(1, userId);
            prepStmtDeleteUserById.executeUpdate();
        }
    }

    private void deleteAllPostsByUserId(Connection connection, int userId) throws SQLException {
        try (PreparedStatement prepStmtDeleteAllPostsByUserId = connection.prepareStatement(PostsSQL.DELETE_ALL_POSTS_BY_USER_ID.getQuery())) {

            prepStmtDeleteAllPostsByUserId.setInt(1, userId);
            prepStmtDeleteAllPostsByUserId.executeUpdate();
        }
    }
}
