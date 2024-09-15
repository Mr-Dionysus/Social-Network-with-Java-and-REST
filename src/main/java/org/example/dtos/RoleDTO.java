package org.example.dtos;

import org.example.entities.User;

import java.util.ArrayList;
import java.util.Objects;

public class RoleDTO {
    private String roleName;
    private String description;
    private ArrayList<User> users = new ArrayList<>();

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return Objects.equals(roleName, roleDTO.roleName) && Objects.equals(description, roleDTO.description) && Objects.equals(users, roleDTO.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName, description, users);
    }
}
