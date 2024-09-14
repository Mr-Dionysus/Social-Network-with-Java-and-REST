package org.example.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private ArrayList<Role> roles = new ArrayList<>();
    private ArrayList<Post> posts = new ArrayList<>();

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password, ArrayList<Role> roles) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public User(int id, String login, String password, ArrayList<Role> roles, ArrayList<Post> posts) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roles = roles;
        this.posts = posts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login='" + login + '\'' + ", password='" + password + '\'' + ", roles=" + roles + ", posts=" + posts + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User foundUser = (User) o;
        return id == foundUser.id && Objects.equals(login, foundUser.login) && Objects.equals(password, foundUser.password) && Objects.equals(roles, foundUser.roles) && Objects.equals(posts, foundUser.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, roles, posts);
    }
}
