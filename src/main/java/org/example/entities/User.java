package org.example.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String password;
    private List<Role> roles = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();

    public User() {
    }

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password, List<Role> roles, List<Post> posts) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roles = roles;
        this.posts = posts;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles) && Objects.equals(posts, user.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, roles, posts);
    }
}
