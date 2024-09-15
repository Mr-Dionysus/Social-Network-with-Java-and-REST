package org.example.connection;

import org.example.db.DataSource;
import org.example.db.TablesSQL;
import org.example.repositories.PostRepository;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestSQL {
    public static void createAllTablesWithTestEntities(Connection connection, DataSource dataSource) throws SQLException {
        try (PreparedStatement prepStmtCreateTableUsers = connection.prepareStatement(TablesSQL.CREATE_USERS.getQuery());
             PreparedStatement prepStmtCreateTableRoles = connection.prepareStatement(TablesSQL.CREATE_ROLES.getQuery());
             PreparedStatement prepStmtCreateTablePosts = connection.prepareStatement(TablesSQL.CREATE_POSTS.getQuery());
             PreparedStatement prepStmtCreateTableUsersRoles = connection.prepareStatement(TablesSQL.CREATE_USERS_ROLES.getQuery())
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
