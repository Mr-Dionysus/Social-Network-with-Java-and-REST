package org.example.connection;

import org.example.DataSource;
import org.example.repositories.PostRepository;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLtest {
    public static final String SQL_CREATE_TABLE_USERS = """
            CREATE TABLE IF NOT EXISTS users (
            id INT AUTO_INCREMENT PRIMARY KEY,
            login tinytext not null,
            password tinytext not null,
            unique key (login(25))
            )
            """;
    public static final String SQL_CREATE_TABLE_ROLES = """
            CREATE TABLE IF NOT EXISTS roles (
            id INT AUTO_INCREMENT PRIMARY KEY,
            role tinytext not null,
            description text not null,
            unique key (role(25))
            )
            """;
    public static final String SQL_CREATE_TABLE_POSTS = """
            CREATE TABLE IF NOT EXISTS posts (
            id INT AUTO_INCREMENT PRIMARY KEY,
            text text not null,
            likes INT,
            dislikes INT,
            user_id INT not null,
            index idx_user_id (user_id)
            )
            """;
    public static final String SQL_CREATE_TABLE_USERS_ROLES = """
            CREATE TABLE IF NOT EXISTS users_roles (
            user_id INT,
            role_id INT,
            PRIMARY KEY (user_id, role_id),
            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
            FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
            )
            """;
    public static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    public static final String SQL_SELECT_POST_BY_ID = "SELECT * FROM posts WHERE id = ?";
    public static final String SQL_SELECT_ALL_ROLE_IDS_BY_USER_ID = "SELECT role_id FROM " + "users_roles" + " WHERE user_id = ?";

    public static void createAllTablesWithTestEntities(Connection connection, DataSource dataSource) throws SQLException {
        try (PreparedStatement prepStmtCreateTableUsers = connection.prepareStatement(SQL_CREATE_TABLE_USERS);
             PreparedStatement prepStmtCreateTableRoles = connection.prepareStatement(SQL_CREATE_TABLE_ROLES);
             PreparedStatement prepStmtCreateTablePosts = connection.prepareStatement(SQL_CREATE_TABLE_POSTS);
             PreparedStatement prepStmtCreateTableUsersRoles = connection.prepareStatement(SQL_CREATE_TABLE_USERS_ROLES)
        ) {
            prepStmtCreateTableUsers.executeUpdate();
            prepStmtCreateTableRoles.executeUpdate();
            prepStmtCreateTablePosts.executeUpdate();
            prepStmtCreateTableUsersRoles.executeUpdate();

            UserRepository userRepository = new UserRepository(dataSource);
            String testLogin = "testLogin";
            String testPassword = "testPassword";
            userRepository.createUser(testLogin, testPassword);

            String testLogin2 = "testLogin2";
            String testPassword2 = "testPassword2";
            userRepository.createUser(testLogin2, testPassword2);

            PostRepository postRepository = new PostRepository(dataSource);
            String testText = "test text";
            int testUserId = 1;
            postRepository.createPost(testText, testUserId);

            RoleRepository roleRepository = new RoleRepository(dataSource);
            String expectedRoleName = "admin";
            String expectedDescription = "manage stuff";
            roleRepository.createRole(expectedRoleName, expectedDescription);
        }
    }
}
