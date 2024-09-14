package org.example.servlets;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dtos.RoleDTO;
import org.example.dtos.UserCredentialsDTO;
import org.example.dtos.UserDTO;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.mappers.RoleMapper;
import org.example.services.RoleServiceImpl;
import org.example.services.UsersRolesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class RoleUsersServletTest {
    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private UsersRolesServiceImpl usersRolesService;

    private Gson gson;
    private RoleUsersServlet roleUsersServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gson = new Gson();
        roleUsersServlet = new RoleUsersServlet(roleService, usersRolesService, roleMapper);
    }

    @Test
    void doPut() throws IOException {
        int userId = 1;
        String login = "admin";
        String password = "password";
        User mockUser = new User(userId, login, password);

        int roleId = 1;
        String roleName = "admin";
        String description = "manage stuff";
        Role mockRole = new Role(roleId, roleName, description);
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(roleName);
        mockRoleDTO.setDescription(description);
        mockRoleDTO.setUsers(new ArrayList<>(List.of(mockUser)));

        when(req.getPathInfo()).thenReturn("/" + roleId + "/" + userId);
        when(roleService.getRoleById(roleId)).thenReturn(mockRole);
        when(roleMapper.roleToRoleDTO(mockRole)).thenReturn(mockRoleDTO);
        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        roleUsersServlet.doPut(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
        verify(out).println(gson.toJson(mockRoleDTO));
        verify(out).println(gson.toJson(mockRoleDTO.getUsers()));
    }
}