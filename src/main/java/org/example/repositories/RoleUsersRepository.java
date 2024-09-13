package org.example.repositories;

import org.example.DataSource;
import org.example.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleUsersRepository {
    private DataSource dataSource;

    public RoleUsersRepository() {
        this.dataSource = new DataSource();
    }

    public RoleUsersRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_INSERT_USER_ID_AND_ROLE_ID = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";
    private static final String SQL_SELECT_USERS_BY_ROLE_ID = "SELECT u.* FROM users u JOIN users_roles ur ON u.id = ur.role_id WHERE ur.role_id = ?";

    public void assignUserToRole(int userId, int roleId) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtInsertUserIdAndRoleId = connection.prepareStatement(SQL_INSERT_USER_ID_AND_ROLE_ID)
        ) {
            prepStmtInsertUserIdAndRoleId.setInt(1, userId);
            prepStmtInsertUserIdAndRoleId.setInt(2, roleId);
            prepStmtInsertUserIdAndRoleId.executeUpdate();
        }
    }

    public ArrayList<User> getUsersWithRole(int roleId) throws SQLException {
        ArrayList<User> listFoundUsers = new ArrayList<>();

        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectUsersByRoleId = connection.prepareStatement(SQL_SELECT_USERS_BY_ROLE_ID)
        ) {
            prepStmtSelectUsersByRoleId.setInt(1, roleId);

            try (ResultSet rsFoundUsers = prepStmtSelectUsersByRoleId.executeQuery()) {
                while (rsFoundUsers.next()) {
                    int userId = rsFoundUsers.getInt("id");
                    String login = rsFoundUsers.getString("login");
                    String password = rsFoundUsers.getString("password");
                    User foundUser = new User(userId, login, password);
                    listFoundUsers.add(foundUser);
                }
            }
        }

        return listFoundUsers;
    }
}
