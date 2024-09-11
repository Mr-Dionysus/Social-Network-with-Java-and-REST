package org.example.repositories;

import org.example.entities.Role;
import org.example.DataSource;
import org.example.services.RoleServiceImpl;

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

    public Role readRole(int id) {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("SELECT * FROM roles WHERE " + "id " + "= ?");
        ) {
            prepStmt.setInt(1, id);
            ResultSet rs = prepStmt.executeQuery();
            String roleName = null;
            String description = null;

            while (rs.next()) {
                roleName = rs.getString("role");
                description = rs.getString("description");
            }

            Role role = new Role(id, roleName, description);
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
