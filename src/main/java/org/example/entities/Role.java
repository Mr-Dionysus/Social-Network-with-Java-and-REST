package org.example.entities;

import java.util.ArrayList;

public class Role {
    private int id;
    private String roleName;
    private String description;
    private User user;
    private ArrayList<User> users = new ArrayList<>();

    public Role() {
    }

    public Role(int id) {
        this.id = id;
    }

    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Role(int id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", roleName='" + roleName + '\'' + ", description='" + description + '\'' + '}';
    }
}
