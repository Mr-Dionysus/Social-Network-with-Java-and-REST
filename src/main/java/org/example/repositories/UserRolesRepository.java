package org.example.repositories;

import org.example.DataSource;
import org.example.entities.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRolesRepository {
    private static final String SQL_INSERT_USER_ID_AND_ROLE_ID = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";

    private static final String SQL_SELECT_ALL_ROLES_BY_USER_ID = "SELECT r.* FROM roles r JOIN users_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?";

    public void assignRoleToUser(int userId, int roleId) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtInsertUserIdAndRoleId = connection.prepareStatement(SQL_INSERT_USER_ID_AND_ROLE_ID)
        ) {
            prepStmtInsertUserIdAndRoleId.setInt(1, userId);
            prepStmtInsertUserIdAndRoleId.setInt(2, roleId);
            prepStmtInsertUserIdAndRoleId.executeUpdate();
        }
    }

    public ArrayList<Role> getRolesForUser(int userId) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtSelectAllRolesByUserId = connection.prepareStatement(SQL_SELECT_ALL_ROLES_BY_USER_ID)
        ) {
            prepStmtSelectAllRolesByUserId.setInt(1, userId);

            try (ResultSet rsAllFoundRoles = prepStmtSelectAllRolesByUserId.executeQuery()) {
                ArrayList<Role> roles = new ArrayList<>();

                while (rsAllFoundRoles.next()) {
                    int roleId = rsAllFoundRoles.getInt("id");
                    String roleName = rsAllFoundRoles.getString("role");
                    String description = rsAllFoundRoles.getString("description");
                    Role foundRole = new Role(roleId, roleName, description);
                    roles.add(foundRole);
                }

                return roles;
            }
        }
    }
}
