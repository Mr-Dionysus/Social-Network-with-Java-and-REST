package org.example.repositories;

import org.example.DataSource;
import org.example.entities.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRolesRepository {
    public void assignRoleToUser(int userId, int roleId) {
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

    public ArrayList<Role> getRolesForUser(int userId) {
        ArrayList<Role> roles = new ArrayList<>();

        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("SELECT r.* FROM roles r " + "JOIN users_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?")
        ) {
            prepStmt.setInt(1, userId);

            try (ResultSet rs = prepStmt.executeQuery()) {
                while (rs.next()) {
                    int roleId = rs.getInt("id");
                    String roleName = rs.getString("role");
                    String description = rs.getString("description");
                    Role role = new Role(roleId, roleName, description);
                    roles.add(role);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return roles;
    }
}
