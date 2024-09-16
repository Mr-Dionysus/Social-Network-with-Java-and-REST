package org.example.validators;

import org.example.entities.Role;
import org.example.exceptions.RoleNotFoundException;

import java.util.List;

public class RoleValidator {
    private RoleValidator() {
        throw new IllegalStateException("Utility Class");
    }

    public static void roleId(int roleId) {
        if (roleId <= 0) {
            throw new IllegalArgumentException("Role's ID can't be 0 or less");
        }
    }

    public static void foundRole(Role foundRole, int roleId) {
        if (foundRole == null) {
            throw new RoleNotFoundException("Role with ID '" + roleId + "' can't be found");
        }
    }

    public static void createdRole(Role createdRole, String roleName) {
        if (createdRole == null) {
            throw new RoleNotFoundException("Role with roleName '" + roleName + "' can't be found");
        }
    }

    public static void listFoundRoles(List<Role> listFoundRoles) {
        if (listFoundRoles == null) {
            throw new RoleNotFoundException("Can't find any roles");
        }
    }

    public static void roleName(String roleName) {
        if (roleName == null || roleName.trim()
                                        .isEmpty()) {
            throw new IllegalArgumentException("Role's name can't be null or empty");
        }
    }

    public static void description(String description) {
        if (description == null || description.trim()
                                              .isEmpty()) {
            throw new IllegalArgumentException("Role's description can't be null or empty");
        }
    }
}
