package org.example.dtos;

import jakarta.validation.constraints.NotNull;
import org.example.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoleDTO {

    @NotNull(message = "Role's name can't be null")
    private String roleName;

    @NotNull(message = "Description can't be null")
    private String description;

    private List<User> users = new ArrayList<>();

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
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
