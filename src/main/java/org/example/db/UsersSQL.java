package org.example.db;

public enum UsersSQL {
    INSERT("INSERT INTO users (login, password) VALUES(?, ?)"),
    SELECT_USER_ID_BY_LOGIN("SELECT id FROM users WHERE login = ?"),
    SELECT_BY_ID("SELECT * FROM users WHERE id = ?"),
    SQL_UPDATE_USER_BY_ID("UPDATE users SET login = ?, password = ? WHERE id = ?"),
    SQL_DELETE_USER_BY_ID("DELETE FROM users WHERE id = ?");

    private final String query;

    UsersSQL(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
