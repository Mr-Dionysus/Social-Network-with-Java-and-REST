package org.example.dtos;

import org.example.entities.Post;
import org.example.entities.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDTO {
    private String login;
    private List<Role> roles = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
    public String toString() {
        return "UserDTO{" + "login='" + login + '\'' + ", roles=" + roles + ", posts=" + posts + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(login, userDTO.login) && Objects.equals(roles, userDTO.roles) && Objects.equals(posts, userDTO.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, roles, posts);
    }
}
