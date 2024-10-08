package org.example.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class UserCredentialsDTO {

    @NotNull(message = "Login can't be null")
    private String login;

    @NotNull(message = "Password can't be null")
    private String password;

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
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserCredentialsDTO that = (UserCredentialsDTO) o;
        return Objects.equals(login, that.login) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
