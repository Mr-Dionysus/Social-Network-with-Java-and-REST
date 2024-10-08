package org.example.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post {
    @NotNull(message = "ID can't be null")
    @Positive(message = "ID can't be less than 1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Text can't be null")
    @Column(name = "text")
    private String text;

    @Column(name = "likes")
    private int likes;

    @Column(name = "dislikes")
    private int dislikes;

    @NotNull(message = "Author can't be null")
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User author;

    public Post() {
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

    public Post(String text, User author) {
        this.text = text;
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
    public String toString() {
        return "Post{" + "id=" + id + ", text='" + text + '\'' + ", likes=" + likes + ", dislikes=" + dislikes + ", author=" + author + '}';
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
