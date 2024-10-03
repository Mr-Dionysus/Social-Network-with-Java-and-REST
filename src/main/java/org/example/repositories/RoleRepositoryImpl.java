package org.example.repositories;

import org.example.db.DataSource;
import org.example.db.RolesSQL;
import org.example.db.UsersRolesSQL;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.exceptions.PostNotFoundException;
import org.example.exceptions.RoleNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleRepositoryImpl implements RoleRepository {
    private final DataSource dataSource;

    public RoleRepositoryImpl() {
        this.dataSource = new DataSource();
    }

    public RoleRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Role createRole(String roleName, String description) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtInsertRole = connection.prepareStatement(RolesSQL.INSERT.getQuery())
        ) {
            prepStmtInsertRole.setString(1, roleName);
            prepStmtInsertRole.setString(2, description);
            prepStmtInsertRole.executeUpdate();

            Role createdRole = this.getRoleId(connection, roleName, description);

            return createdRole;
        }
    }

    private Role getRoleId(Connection connection, String roleName, String description) throws SQLException {
        try (PreparedStatement prepStmtFindId = connection.prepareStatement(RolesSQL.SELECT_ROLE_ID_BY_ROLE.getQuery())) {
            prepStmtFindId.setString(1, roleName);

            try (ResultSet rsFoundRoleId = prepStmtFindId.executeQuery()) {
                if (rsFoundRoleId.next()) {
                    int id = rsFoundRoleId.getInt("id");
                    Role foundRole = new Role(id, roleName, description);

                    return foundRole;
                }
            }
        }
        throw new RoleNotFoundException("Role ID with a name '" + roleName + "' isn't found");
    }

    public Role getRoleById(int roleId) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectRoleById = connection.prepareStatement(RolesSQL.SELECT_BY_ID.getQuery())
        ) {
            prepStmtSelectRoleById.setInt(1, roleId);

            try (ResultSet rsFoundRole = prepStmtSelectRoleById.executeQuery()) {
                if (rsFoundRole.next()) {
                    String roleName = rsFoundRole.getString("role");
                    String description = rsFoundRole.getString("description");
                    Role foundRole = this.getRoleWithItsUsers(connection, roleId, roleName, description);

                    return foundRole;
                }
            }
        }
        throw new RoleNotFoundException("Role with ID '" + roleId + "' isn't found");
    }

    private Role getRoleWithItsUsers(Connection connection, int roleId, String roleName, String description) throws SQLException {
        ArrayList<User> listUsers = new ArrayList<>();

        try (PreparedStatement prepStmtSelectAllUserIdsByRoleId = connection.prepareStatement(UsersRolesSQL.SELECT_ALL_USER_IDS_BY_ROLE_ID.getQuery())) {
            prepStmtSelectAllUserIdsByRoleId.setInt(1, roleId);
            UserRepository userRepository = new UserRepositoryImpl(dataSource);

            try (ResultSet rsFoundUserIds = prepStmtSelectAllUserIdsByRoleId.executeQuery()) {
                while (rsFoundUserIds.next()) {
                    int userId = rsFoundUserIds.getInt("user_id");
                    User user = userRepository.getUserWithoutHisRoles(userId);
                    listUsers.add(user);
                }

                Role foundRole = new Role(roleId, roleName, description, listUsers);

                return foundRole;
            }
        }
    }

    public Role getRoleWithoutItsUsers(int roleId) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectRoleById = connection.prepareStatement(RolesSQL.SELECT_BY_ID.getQuery())
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
        throw new RoleNotFoundException("Role with ID '" + roleId + "' isn't found");
    }

    public List<Role> getAllRoles() throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtSelectRoles = connection.prepareStatement(RolesSQL.SELECT_ALL_ROLES.getQuery());
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

    public Role updateRoleById(int roleId, String newRoleName, String newDescription) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtUpdateRoleById = connection.prepareStatement(RolesSQL.UPDATE_BY_ID.getQuery())
        ) {
            this.isRoleFound(roleId);

            prepStmtUpdateRoleById.setString(1, newRoleName);
            prepStmtUpdateRoleById.setString(2, newDescription);
            prepStmtUpdateRoleById.setInt(3, roleId);
            prepStmtUpdateRoleById.executeUpdate();

            RoleRepository roleRepository = new RoleRepositoryImpl(dataSource);
            Role updatedRole = roleRepository.getRoleById(roleId);

            return updatedRole;
        }
    }

    private void isRoleFound(int roleId) throws SQLException {
        Role foundRole = this.getRoleById(roleId);

        if (foundRole == null) {
            throw new PostNotFoundException("Error while updating a Role. Can't find a Role " + "with ID '" + roleId + "'.");
        }
    }

    public void deleteRoleById(int id) throws SQLException {
        try (Connection connection = dataSource.connect();
             PreparedStatement prepStmtDeleteRoleById = connection.prepareStatement(RolesSQL.DELETE_BY_ID.getQuery())
        ) {
            prepStmtDeleteRoleById.setInt(1, id);
            prepStmtDeleteRoleById.executeUpdate();
        }
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
