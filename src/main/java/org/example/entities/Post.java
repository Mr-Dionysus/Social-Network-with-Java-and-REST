package org.example.entities;

import java.util.Objects;

public class Post {
    private int id;
    private String text;
    private int likes;
    private int dislikes;
    private User user;

    public Post() {
    }

    public Post(int id, String text, User user) {
        this.id = id;
        this.text = text;
        this.user = user;
    }

    public Post(String text, int likes, int dislikes) {
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Post(String text, int likes, int dislikes, User user) {
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
        this.user = user;
    }

    public Post(int id, String text, int likes, int dislikes) {
        this.id = id;
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Post(int id, String text, int likes, int dislikes, User user) {
        this.id = id;
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", text='" + text + '\'' + ", likes=" + likes + ", dislikes=" + dislikes + ", user=" + user + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Post post = (Post) o;
        return id == post.id && likes == post.likes && dislikes == post.dislikes && Objects.equals(text, post.text) && Objects.equals(user, post.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, likes, dislikes, user);
    }
}
