package org.example.dtos;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
