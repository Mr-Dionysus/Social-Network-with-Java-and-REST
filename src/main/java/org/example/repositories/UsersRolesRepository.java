package org.example.repositories;

import org.example.db.DataSource;
import org.example.db.UsersRolesSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsersRolesRepository {
    private final DataSource dataSource;

    public UsersRolesRepository() {
        this.dataSource = new DataSource();
    }

    public UsersRolesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void assignRoleToUser(int userId, int roleId) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtInsertUserIdAndRoleId = connection.prepareStatement(UsersRolesSQL.INSERT_USER_ID_AND_ROLE_ID.getQuery())
        ) {
            prepStmtInsertUserIdAndRoleId.setInt(1, userId);
            prepStmtInsertUserIdAndRoleId.setInt(2, roleId);
            prepStmtInsertUserIdAndRoleId.executeUpdate();
        }
    }

    //    public ArrayList<Role> getRolesForUser(int userId) throws SQLException {
    //        try (Connection connection = dataSource.connect();
    //             PreparedStatement prepStmtSelectAllRolesByUserId = connection.prepareStatement
    //             (UsersRolesSQL.SELECT_ALL_ROLES_BY_USER_ID.getQuery())
    //        ) {
    //            prepStmtSelectAllRolesByUserId.setInt(1, userId);
    //
    //            try (ResultSet rsAllFoundRoles = prepStmtSelectAllRolesByUserId.executeQuery()) {
    //                ArrayList<Role> roles = new ArrayList<>();
    //
    //                while (rsAllFoundRoles.next()) {
    //                    int roleId = rsAllFoundRoles.getInt("id");
    //                    String roleName = rsAllFoundRoles.getString("role");
    //                    String description = rsAllFoundRoles.getString("description");
    //                    Role foundRole = new Role(roleId, roleName, description);
    //                    roles.add(foundRole);
    //                }
    //
    //                return roles;
    //            }
    //        }
    //    }

    //    public ArrayList<User> getUsersWithRole(int roleId) throws SQLException {
    //        ArrayList<User> listFoundUsers = new ArrayList<>();
    //
    //        try (Connection connection = dataSource.connect();
    //             PreparedStatement prepStmtSelectUsersByRoleId = connection.prepareStatement
    //             (UsersSQL.SQL_SELECT_USERS_BY_ROLE_ID.getQuery())
    //        ) {
    //            prepStmtSelectUsersByRoleId.setInt(1, roleId);
    //
    //            try (ResultSet rsFoundUsers = prepStmtSelectUsersByRoleId.executeQuery()) {
    //                while (rsFoundUsers.next()) {
    //                    int userId = rsFoundUsers.getInt("id");
    //                    String login = rsFoundUsers.getString("login");
    //                    String password = rsFoundUsers.getString("password");
    //                    User foundUser = new User(userId, login, password);
    //                    listFoundUsers.add(foundUser);
    //                }
    //            }
    //        }
    //
    //        return listFoundUsers;
    //    }
}
