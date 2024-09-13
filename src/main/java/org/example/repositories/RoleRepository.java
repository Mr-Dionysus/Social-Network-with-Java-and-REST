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
    private static final String SQL_INSERT_ROLE = "INSERT INTO roles (role, description) VALUES (?, ?)";
    private static final String SQL_SELECT_ROLE_ID_BY_ROLE = "SELECT id FROM roles WHERE role = ?";
    private static final String SQL_SELECT_ROLE_BY_ID = "SELECT * FROM roles WHERE id = ?";
    private static final String SQL_SELECT_ROLES = "SELECT * FROM roles";
    private static final String SQL_UPDATE_ROLE_BY_ID = "UPDATE roles SET role = ?, description = ? WHERE id = ?";
    private static final String SQL_DELETE_ROLE_BY_ID = "DELETE FROM roles WHERE id = ?";

    private static final String SQL_SELECT_ALL_USER_IDS_BY_ROLE_ID = "SELECT user_id FROM users_roles WHERE role_id = ?";

    public Role createRole(String roleName, String description) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement(SQL_INSERT_ROLE)
        ) {
            prepStmt.setString(1, roleName);
            prepStmt.setString(2, description);
            prepStmt.executeUpdate();

            try (PreparedStatement prepStmtFindId = connection.prepareStatement(SQL_SELECT_ROLE_ID_BY_ROLE);) {
                prepStmtFindId.setString(1, roleName);

                try (ResultSet rs = prepStmtFindId.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        Role createdRole = new Role(id, roleName, description);

                        return createdRole;
                    }
                }
            }
        }

        return null;
    }

    public Role readRole(int roleId) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtSelectRoleById = connection.prepareStatement(SQL_SELECT_ROLE_BY_ID)
        ) {
            prepStmtSelectRoleById.setInt(1, roleId);

            try (ResultSet rsFoundRole = prepStmtSelectRoleById.executeQuery()) {
                if (rsFoundRole.next()) {
                    String roleName = rsFoundRole.getString("role");
                    String description = rsFoundRole.getString("description");

                    ArrayList<User> listUsers = new ArrayList<>();

                    try (PreparedStatement prepStmtSelectAllUserIdsByRoleId = connection.prepareStatement(SQL_SELECT_ALL_USER_IDS_BY_ROLE_ID)) {
                        prepStmtSelectAllUserIdsByRoleId.setInt(1, roleId);
                        UserRepository userRepository = new UserRepository();
                        UserServiceImpl userService = new UserServiceImpl(userRepository);

                        try (ResultSet rsFoundUserIds = prepStmtSelectAllUserIdsByRoleId.executeQuery()) {
                            while (rsFoundUserIds.next()) {
                                int userId = rsFoundUserIds.getInt("user_id");
                                User user = userService.getUserByIdWithoutRoles(userId);
                                listUsers.add(user);
                            }

                            Role foundRole = new Role(roleId, roleName, description, listUsers);

                            return foundRole;
                        }
                    }
                }
            }
        }

        return null;
    }

    public Role readRoleWithoutArray(int roleId) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtSelectRoleById = connection.prepareStatement(SQL_SELECT_ROLE_BY_ID);
        ) {
            prepStmtSelectRoleById.setInt(1, roleId);
            ResultSet rsFoundRole = prepStmtSelectRoleById.executeQuery();

            if (rsFoundRole.next()) {
                String roleName = rsFoundRole.getString("role");
                String description = rsFoundRole.getString("description");
                Role foundRole = new Role(roleId, roleName, description);

                return foundRole;
            }
        }

        return null;
    }

    public ArrayList<Role> readAllRoles() throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtSelectRoles = connection.prepareStatement(SQL_SELECT_ROLES);
             ResultSet rsFoundRoles = prepStmtSelectRoles.executeQuery()
        ) {
            ArrayList<Role> listFoundRoles = new ArrayList<>();

            while (rsFoundRoles.next()) {
                int id = rsFoundRoles.getInt(1);
                String roleName = rsFoundRoles.getString(2);
                String description = rsFoundRoles.getString(3);
                Role role = new Role(id, roleName, description);
                listFoundRoles.add(role);
            }

            return listFoundRoles;
        }
    }

    public Role updateRole(int id, String newRoleName, String newDescription) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtUpdateRoleById = connection.prepareStatement(SQL_UPDATE_ROLE_BY_ID);
        ) {
            prepStmtUpdateRoleById.setString(1, newRoleName);
            prepStmtUpdateRoleById.setString(2, newDescription);
            prepStmtUpdateRoleById.setInt(3, id);
            prepStmtUpdateRoleById.executeUpdate();

            RoleRepository roleRepository = new RoleRepository();
            RoleServiceImpl roleService = new RoleServiceImpl(roleRepository);
            Role updatedRole = roleService.getRoleById(id);

            return updatedRole;
        }
    }

    public void deleteRoleById(int id) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmtDeleteRoleById = connection.prepareStatement(SQL_DELETE_ROLE_BY_ID);
        ) {
            prepStmtDeleteRoleById.setInt(1, id);
            prepStmtDeleteRoleById.executeUpdate();
        }
    }
}
