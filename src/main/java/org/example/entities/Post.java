package org.example.entities;

import java.util.Objects;

public class Post {
    private int id;
    private String text;
    private int likes;
    private int dislikes;
    private User author;

    public Post() {
    }

    public Post(int id, String text, User author) {
        this.id = id;
        this.text = text;
        this.author = author;
    }

    public Post(int id, String text, int likes, int dislikes) {
        this.id = id;
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Post(int id, String text, int likes, int dislikes, User author) {
        this.id = id;
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
        this.author = author;
    }

    public int getId() {
        return id;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Post post = (Post) o;
        return id == post.id && likes == post.likes && dislikes == post.dislikes && Objects.equals(text, post.text) && Objects.equals(author, post.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, likes, dislikes, author);
    }
}
