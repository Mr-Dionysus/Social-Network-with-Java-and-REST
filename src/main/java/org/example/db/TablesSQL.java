package org.example.db;

public enum TablesSQL {
    CREATE_USERS("""
            CREATE TABLE IF NOT EXISTS users (
            id INT AUTO_INCREMENT PRIMARY KEY,
            login tinytext not null,
            password tinytext not null,
            unique key (login(25))
            )
            """),
    CREATE_ROLES("""
            CREATE TABLE IF NOT EXISTS roles (
            id INT AUTO_INCREMENT PRIMARY KEY,
            role tinytext not null,
            description text not null,
            unique key (role(25))
            )
            """),
    CREATE_POSTS("""
            CREATE TABLE IF NOT EXISTS posts (
            id INT AUTO_INCREMENT PRIMARY KEY,
            text text not null,
            likes INT,
            dislikes INT,
            user_id INT not null,
            index idx_user_id (user_id)
            )
            """),
    CREATE_USERS_ROLES("""
            CREATE TABLE IF NOT EXISTS users_roles (
            user_id INT,
            role_id INT,
            PRIMARY KEY (user_id, role_id),
            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
            FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
            )
            """);

    private final String query;

    TablesSQL(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
