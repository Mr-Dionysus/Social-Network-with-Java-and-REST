package org.example.dtos;

import org.example.entities.User;

import java.util.Objects;

public class PostDTO {
    private String text;
    private int likes;
    private int dislikes;
    private User user;

    public PostDTO() {
    }

    public PostDTO(String text, int likes, int dislikes) {
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public PostDTO(String text, int likes, int dislikes, User user) {
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
        this.user = user;
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
        return "Post{" + ", text='" + text + '\'' + ", likes=" + likes + ", dislikes=" + dislikes + ", user=" + user + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PostDTO postDTO = (PostDTO) o;
        return likes == postDTO.likes && dislikes == postDTO.dislikes && Objects.equals(text, postDTO.text) && Objects.equals(user, postDTO.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, likes, dislikes, user);
    }
}
