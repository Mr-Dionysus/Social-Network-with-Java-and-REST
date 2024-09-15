package org.example.db;

public enum PostsSQL {
    INSERT("INSERT INTO posts (text, likes, dislikes, user_id) VALUES(?, ?, ?, ?)"),
    SELECT_POST_ID_BY_FIELDS("SELECT id FROM posts WHERE text = ? AND likes = ? AND dislikes = ? " + "AND user_id = ?"),
    SELECT_POST_ID_BY_USER_ID("SELECT id FROM posts WHERE user_id = ?"),
    SELECT_BY_ID("SELECT * FROM posts WHERE id = ?"),
    UPDATE_BY_ID("UPDATE posts SET text = ? WHERE id = ?"),
    DELETE_BY_ID("DELETE FROM posts WHERE id = ?"),
    DELETE_ALL_POSTS_BY_USER_ID("DELETE FROM posts where user_id = ?");

    private final String query;

    PostsSQL(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
