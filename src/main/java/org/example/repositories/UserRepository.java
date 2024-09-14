package org.example.repositories;

import org.example.DataSource;
import org.example.db.UsersRolesSQL;
import org.example.db.UsersSQL;
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
    private DataSource dataSource;

    public UserRepository() {
        this.dataSource = new DataSource();
    }

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private final RoleRepository roleRepository = new RoleRepository(dataSource);
    private final RoleServiceImpl roleService = new RoleServiceImpl(roleRepository);

    private static final String SQL_SELECT_POST_ID_BY_USER_ID = "SELECT id FROM posts WHERE user_id = ?";

    public User createUser(String login, String password) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtInsertUser = connection.prepareStatement(UsersSQL.INSERT.getQuery())
        ) {
            prepStmtInsertUser.setString(1, login);
            prepStmtInsertUser.setString(2, password);
            prepStmtInsertUser.executeUpdate();

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
        }

        return null;
    }

    public User findUserById(int userId) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectUserById = connection.prepareStatement(UsersSQL.SELECT_BY_ID.getQuery())
        ) {
            prepStmtSelectUserById.setInt(1, userId);

            try (ResultSet rsFoundUser = prepStmtSelectUserById.executeQuery()) {
                if (rsFoundUser.next()) {
                    String login = rsFoundUser.getString("login");
                    String password = rsFoundUser.getString("password");

                    ArrayList<Role> listFoundRoles = this.findListOfRoles(connection, userId);
                    ArrayList<Post> listFoundPosts = this.findListOfPosts(connection, userId);

                    User foundUser = new User(userId, login, password, listFoundRoles, listFoundPosts);

                    return foundUser;
                }
            }
        }

        return null;
    }

    private ArrayList<Role> findListOfRoles(Connection connection, int userId) throws SQLException {
        try (PreparedStatement prepStmtSelectAllRoleIdsByUserId =
                     connection.prepareStatement(UsersRolesSQL.SELECT_ALL_ROLE_IDS_BY_USER_ID.getQuery())) {
            prepStmtSelectAllRoleIdsByUserId.setInt(1, userId);

            try (ResultSet rsFoundAllRoleIds = prepStmtSelectAllRoleIdsByUserId.executeQuery()) {
                ArrayList<Role> listFoundRoles = new ArrayList<>();

                while (rsFoundAllRoleIds.next()) {
                    int roleId = rsFoundAllRoleIds.getInt("role_id");
                    Role foundRole = roleService.getRoleByIdWithoutUsers(roleId);
                    listFoundRoles.add(foundRole);
                }

                return listFoundRoles;
            }
        }
    }

    private ArrayList<Post> findListOfPosts(Connection connection, int userId) throws SQLException {
        try (PreparedStatement prepStmtSelectPostIdByUserId = connection.prepareStatement(SQL_SELECT_POST_ID_BY_USER_ID)) {
            prepStmtSelectPostIdByUserId.setInt(1, userId);

            try (ResultSet rsFoundAllPostIds = prepStmtSelectPostIdByUserId.executeQuery()) {
                PostRepository postRepository = new PostRepository(dataSource);
                PostServiceImpl postService = new PostServiceImpl(postRepository);
                ArrayList<Post> listFoundPosts = new ArrayList<>();

                while (rsFoundAllPostIds.next()) {
                    int postId = rsFoundAllPostIds.getInt("id");
                    Post foundPost = postService.getPostByIdWithoutUser(postId);
                    listFoundPosts.add(foundPost);
                }

                return listFoundPosts;
            }
        }
    }

    public User findUserWithoutHisRoles(int userId) throws SQLException {
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

        return null;
    }

    public User updateUser(int id, String newLogin, String newPassword) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtUpdateUserById = connection.prepareStatement(UsersSQL.SQL_UPDATE_USER_BY_ID.getQuery())
        ) {
            prepStmtUpdateUserById.setString(1, newLogin);
            prepStmtUpdateUserById.setString(2, newPassword);
            prepStmtUpdateUserById.setInt(3, id);
            prepStmtUpdateUserById.executeUpdate();

            User foundUser = new User(id, newLogin, newPassword);
            return foundUser;
        }
    }

    public void deleteUser(int id) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtDeleteUserById = connection.prepareStatement(UsersSQL.SQL_DELETE_USER_BY_ID.getQuery())
        ) {
            prepStmtDeleteUserById.setInt(1, id);
            prepStmtDeleteUserById.executeUpdate();
        }
    }
}
