package org.example.repositories;

import org.example.DataSource;
import org.example.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleUsersRepository {
    public void assignUserToRole(int userId, int roleId) {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO users_roles " + "(user_id, role_id) VALUES (?, ?)")
        ) {
            prepStmt.setInt(1, userId);
            prepStmt.setInt(2, roleId);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<User> getUsersWithRole(int roleId) {
        ArrayList<User> listUsers = new ArrayList<>();

        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("SELECT u.* FROM users u JOIN " + "users_roles ur ON u.id = ur.role_id WHERE ur.role_id = ?")
        ) {
            prepStmt.setInt(1, roleId);

            try (ResultSet rs = prepStmt.executeQuery()) {
                while (rs.next()) {
                    int userId = rs.getInt("id");
                    String login = rs.getString("login");
                    String password = rs.getString("password");
                    User user = new User(userId, login, password);
                    listUsers.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listUsers;
    }
}
