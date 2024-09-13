package org.example.repositories;

import org.example.DataSource;
import org.example.entities.Post;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.services.PostServiceImpl;
import org.example.services.RoleServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepository {
    private static final RoleRepository ROLE_REPOSITORY = new RoleRepository();
    private static final RoleServiceImpl ROLE_SERVICE = new RoleServiceImpl(ROLE_REPOSITORY);

    private static final String SQL_INSERT_USER = "INSERT INTO users (login, password) VALUES(?,?)";
    private static final String SQL_SELECT_USER_ID_BY_LOGIN = "SELECT id FROM users WHERE login = ?";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";

    private static final String SQL_SELECT_ALL_ROLE_IDS_BY_USER_ID = "SELECT role_id FROM users_roles WHERE user_id = ?";

    private static final String SQL_SELECT_POST_ID_BY_USER_ID = "SELECT id FROM posts WHERE user_id = ?";

    public User createUser(String login, String password) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtInsertUser = connection.prepareStatement(SQL_INSERT_USER);
        ) {
            prepStmtInsertUser.setString(1, login);
            prepStmtInsertUser.setString(2, password);
            prepStmtInsertUser.executeUpdate();

            try (PreparedStatement prepStmtSelectUserIdByLogin = connection.prepareStatement(SQL_SELECT_USER_ID_BY_LOGIN);) {
                prepStmtSelectUserIdByLogin.setString(1, login);

                try (ResultSet rsFoundUser = prepStmtSelectUserIdByLogin.executeQuery()) {
                    if (rsFoundUser.next()) {
                        int userId = rsFoundUser.getInt("id");
                        User foundUser = new User(userId, login, password);

                        return foundUser;
                    }
                }
            }
        }

        return null;
    }

    public User findUserById(int userId) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtSelectUserById = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
        ) {
            prepStmtSelectUserById.setInt(1, userId);

            try (ResultSet rsFoundUser = prepStmtSelectUserById.executeQuery()) {
                if (rsFoundUser.next()) {
                    String login = rsFoundUser.getString("login");
                    String password = rsFoundUser.getString("password");

                    ArrayList<Role> listFoundRoles = new ArrayList<>();

                    try (PreparedStatement prepStmtSelectAllRoleIdsByUserId = connection.prepareStatement(SQL_SELECT_ALL_ROLE_IDS_BY_USER_ID)) {
                        prepStmtSelectAllRoleIdsByUserId.setInt(1, userId);

                        try (ResultSet rsFoundAllRoleIds = prepStmtSelectAllRoleIdsByUserId.executeQuery()) {
                            while (rsFoundAllRoleIds.next()) {
                                int roleId = rsFoundAllRoleIds.getInt("role_id");
                                Role foundRole = ROLE_SERVICE.getRoleByIdWithoutArr(roleId);
                                listFoundRoles.add(foundRole);
                            }
                        }
                    }

                    ArrayList<Post> listFoundPosts = new ArrayList<>();

                    try (PreparedStatement prepStmtSelectPostIdByUserId = connection.prepareStatement(SQL_SELECT_POST_ID_BY_USER_ID)) {
                        prepStmtSelectPostIdByUserId.setInt(1, userId);

                        try (ResultSet rsFoundAllPostIds = prepStmtSelectPostIdByUserId.executeQuery()) {
                            PostRepository postRepository = new PostRepository();
                            PostServiceImpl postService = new PostServiceImpl(postRepository);

                            while (rsFoundAllPostIds.next()) {
                                int postId = rsFoundAllPostIds.getInt("id");
                                Post foundPost = postService.getPostByIdWithoutUser(postId);
                                listFoundPosts.add(foundPost);
                            }
                        }
                    }

                    User foundUser = new User(userId, login, password, listFoundRoles, listFoundPosts);

                    return foundUser;
                }
            }
        }

        return null;
    }

    public User readUserWithoutRoles(int userId) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + "users " + "WHERE id = ?");
        ) {
            preparedStatement.setInt(1, userId);
            ResultSet rsAllFromUsers = preparedStatement.executeQuery();
            String login = null;
            String password = null;

            while (rsAllFromUsers.next()) {
                login = rsAllFromUsers.getString("login");
                password = rsAllFromUsers.getString("password");
            }

            User user = new User(userId, login, password);
            return user;
        }
    }

    public User update(int id, String newLogin, String newPassword) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("UPDATE users SET " + "login = ?, password = ? WHERE id = ?");
        ) {
            prepStmt.setString(1, newLogin);
            prepStmt.setString(2, newPassword);
            prepStmt.setInt(3, id);
            prepStmt.executeUpdate();

            User user = new User(id, newLogin, newPassword);
            return user;
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("DELETE from users WHERE " + "id = ?")
        ) {
            prepStmt.setInt(1, id);
            prepStmt.executeUpdate();
        }
    }
}
