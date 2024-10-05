package org.example.dtos;

import org.example.entities.User;

import java.util.Objects;

public class PostDTO {
    private String text;
    private int likes;
    private int dislikes;
    private User author;

    public PostDTO() {
    }

    public PostDTO(String text, int likes, int dislikes) {
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
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
        PostDTO postDTO = (PostDTO) o;
        return likes == postDTO.likes && dislikes == postDTO.dislikes && Objects.equals(text, postDTO.text) && Objects.equals(author, postDTO.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, likes, dislikes, author);
    }
}
