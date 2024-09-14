package org.example.db;

public enum RolesSQL {
    INSERT("INSERT INTO roles (role, description) VALUES (?, ?)"),
    SELECT_ROLE_ID_BY_ROLE("SELECT id FROM roles WHERE role = ?"),
    SELECT_BY_ID("SELECT * FROM roles WHERE id = ?"), SELECT_ALL_ROLES("SELECT * FROM roles"),
    UPDATE_BY_ID("UPDATE roles SET role = ?, description = ? WHERE id = ?"),
    DELETE_BY_ID("DELETE FROM roles WHERE id = ?");

    private final String query;

    RolesSQL(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
