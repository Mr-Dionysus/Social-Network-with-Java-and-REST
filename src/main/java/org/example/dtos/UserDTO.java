package org.example.dtos;

import org.example.entities.Role;

import java.io.Serializable;
import java.util.ArrayList;

public class UserDTO implements Serializable {
    private String login;
    private transient ArrayList<Role> roles;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }
}
