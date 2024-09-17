package org.example.connection;

import org.example.db.DataSource;
import org.example.db.TablesSQL;
import org.example.repositories.*;

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

            UserRepository userRepository = new UserRepositoryImpl(dataSource);
            String testLogin = "testLogin";
            String testPassword = "testPassword";
            userRepository.createUser(testLogin, testPassword);

            String testLogin2 = "testLogin2";
            String testPassword2 = "testPassword2";
            userRepository.createUser(testLogin2, testPassword2);

            PostRepository postRepositoryImpl = new PostRepositoryImpl(dataSource);
            String testText = "test text";
            int testUserId = 1;
            postRepositoryImpl.createPost(testText, testUserId);

            RoleRepository roleRepository = new RoleRepositoryImpl(dataSource);
            String expectedRoleName = "admin";
            String expectedDescription = "manage stuff";
            roleRepository.createRole(expectedRoleName, expectedDescription);
        }
    }
}
