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




}
