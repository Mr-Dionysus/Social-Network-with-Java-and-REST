package org.example.mappers;

import org.example.dtos.RoleDTO;
import org.example.entities.Role;
import org.example.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RoleMapperTest {
    private  RoleMapper roleMapper;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(RoleMapperImpl.class);
        context.refresh();
        roleMapper = context.getBean(RoleMapper.class);
    }

    @Test
    @DisplayName("Role to a Role DTO")
    void roleToRoleDTO() {
        int expectedRoleId = 1;
        String expectedRoleName = "admin";
        String expectedDescription = "manage stuff";

        int expectedUserId = 1;
        String expectedLogin = "login";
        String expectedPassword = "password";
        User expectedUser = new User(expectedUserId, expectedLogin, expectedPassword);
        ArrayList<User> expectedListUsers = new ArrayList<>();
        expectedListUsers.add(expectedUser);

        Role roleBeforeRoleDTO = new Role(expectedRoleId, expectedRoleName, expectedDescription, expectedListUsers);
        RoleDTO actualRoleDTO = roleMapper.roleToRoleDTO(roleBeforeRoleDTO);

        RoleDTO expectedRoleDTO = new RoleDTO();
        expectedRoleDTO.setRoleName(expectedRoleName);
        expectedRoleDTO.setDescription(expectedDescription);

        assertEquals(expectedRoleDTO, actualRoleDTO);
    }

    @Test
    @DisplayName("Role DTO to a Role")
    void roleDTOtoRole() {
        String expectedRoleName = "admin";
        String expectedDescription = "manage stuff";

        int expectedUserId = 1;
        String expectedLogin = "login";
        String expectedPassword = "password";
        User expectedUser = new User(expectedUserId, expectedLogin, expectedPassword);
        ArrayList<User> expectedListUsers = new ArrayList<>();
        expectedListUsers.add(expectedUser);

        RoleDTO roleDTObeforeRole = new RoleDTO();
        roleDTObeforeRole.setRoleName(expectedRoleName);
        roleDTObeforeRole.setDescription(expectedDescription);
        roleDTObeforeRole.setUsers(expectedListUsers);

        Role expectedRole = new Role();
        expectedRole.setRoleName(expectedRoleName);
        expectedRole.setDescription(expectedDescription);
        expectedRole.setUsers(expectedListUsers);

        Role actualRole = roleMapper.roleDTOtoRole(roleDTObeforeRole);

        assertEquals(expectedRole, actualRole);
    }
}