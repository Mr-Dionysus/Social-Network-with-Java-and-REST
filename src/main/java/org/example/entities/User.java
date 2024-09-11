package org.example.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private Role role;
    private ArrayList<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String login) {
        this.login = login;
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

    public long getId() {
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

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login='" + login + '\'' + ", password='" + password + '\'' + '}';
    }
}
