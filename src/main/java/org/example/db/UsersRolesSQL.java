package org.example.db;

public enum UsersRolesSQL {
    INSERT_USER_ID_AND_ROLE_ID("INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)"),
    SELECT_ALL_USER_IDS_BY_ROLE_ID("SELECT user_id FROM users_roles WHERE role_id = ?"),
    SELECT_ALL_ROLE_IDS_BY_USER_ID("SELECT role_id FROM users_roles WHERE user_id = ?");

    private final String query;

    UsersRolesSQL(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
