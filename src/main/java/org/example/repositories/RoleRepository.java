package org.example.repositories;

import org.example.DataSource;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.services.RoleServiceImpl;
import org.example.services.UserServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleRepository {
    public Role createRole(String roleName, String description) {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("INSERT into roles (role, " + "description) values (?, ?)");
        ) {
            prepStmt.setString(1, roleName);
            prepStmt.setString(2, description);
            prepStmt.executeUpdate();
            int id = -1;

            try (PreparedStatement prepStmtFindId = connection.prepareStatement("SELECT id FROM " + "roles WHERE role = ?");) {
                prepStmtFindId.setString(1, roleName);
                ResultSet rs = prepStmtFindId.executeQuery();

                while (rs.next()) {
                    id = rs.getInt("id");
                }
            }

            Role role = new Role(id, roleName, description);
            return role;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Role readRole(int roleId) {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("SELECT * FROM roles WHERE " + "id " + "= ?");
        ) {
            prepStmt.setInt(1, roleId);
            ResultSet rsAllFromRoles = prepStmt.executeQuery();
            String roleName = null;
            String description = null;

            while (rsAllFromRoles.next()) {
                roleName = rsAllFromRoles.getString("role");
                description = rsAllFromRoles.getString("description");
            }

            ArrayList<User> listUsers = new ArrayList<>();

            try (PreparedStatement prepStmtFindUsers = connection.prepareStatement("SELECT " + "user_id FROM users_roles WHERE role_id = ?")) {
                prepStmtFindUsers.setInt(1, roleId);
                UserRepository userRepository = new UserRepository();
                UserServiceImpl userService = new UserServiceImpl(userRepository);

                try (ResultSet rsFoundUsers = prepStmtFindUsers.executeQuery()) {
                    while (rsFoundUsers.next()) {
                        int userId = rsFoundUsers.getInt("user_id");
                        User user = userService.getUserByIdWithoutArr(userId);
                        listUsers.add(user);
                    }
                }
            }

            Role role = new Role(roleId, roleName, description, listUsers);
            return role;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Role readRoleWithoutArray(int roleId) {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("SELECT * FROM roles WHERE " + "id " + "= ?");
        ) {
            prepStmt.setInt(1, roleId);
            ResultSet rsAllFromRoles = prepStmt.executeQuery();
            String roleName = null;
            String description = null;

            while (rsAllFromRoles.next()) {
                roleName = rsAllFromRoles.getString("role");
                description = rsAllFromRoles.getString("description");
            }


            Role role = new Role(roleId, roleName, description);
            return role;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Role> readAllRoles() throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("SELECT * FROM roles");
             ResultSet resultSet = prepStmt.executeQuery();
        ) {
            int id = -1;
            String roleName = null;
            String description = null;
            ArrayList<Role> arrayList = new ArrayList<>();

            while (resultSet.next()) {
                id = resultSet.getInt(1);
                roleName = resultSet.getString(2);
                description = resultSet.getString(3);

                Role role = new Role(id, roleName, description);
                arrayList.add(role);
            }

            return arrayList;
        }
    }

    public Role updateRole(int id, String newRoleName, String newDescription) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("UPDATE roles SET role = ?, " + "description = ? WHERE id = ?");
        ) {
            prepStmt.setString(1, newRoleName);
            prepStmt.setString(2, newDescription);
            prepStmt.setInt(3, id);
            prepStmt.executeUpdate();

            RoleRepository roleRepository = new RoleRepository();
            RoleServiceImpl roleService = new RoleServiceImpl(roleRepository);
            Role role = roleService.getRoleById(id);
            return role;
        }
    }

    public void deleteRoleById(int id) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("DELETE FROM roles WHERE id = ?");
        ) {
            prepStmt.setInt(1, id);
            prepStmt.executeUpdate();
        }
    }
}
