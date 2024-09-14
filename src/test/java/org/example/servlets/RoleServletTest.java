package org.example.servlets;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dtos.PostDTO;
import org.example.dtos.RoleDTO;
import org.example.entities.Post;
import org.example.entities.Role;
import org.example.mappers.RoleMapper;
import org.example.services.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import static org.mockito.Mockito.*;

class RoleServletTest {
    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private RoleMapper roleMapper;

    private Gson gson;
    private RoleServlet roleServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gson = new Gson();
        roleServlet = new RoleServlet(roleService, roleMapper);
    }

    @Test
    void doPost() throws IOException {
        int roleId = 1;
        String roleName = "admin";
        String description = "do stuff";

        when(req.getPathInfo()).thenReturn("/" + roleId);

        Role mockRole = new Role(roleId, roleName, description);
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(roleName);
        mockRoleDTO.setDescription(description);

        when(roleService.createRole(roleName, description)).thenReturn(mockRole);
        when(roleMapper.roleToRoleDTO(mockRole)).thenReturn(mockRoleDTO);

        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(gson.toJson(mockRoleDTO))));
        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        roleServlet.doPost(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_CREATED);
        verify(out).println(gson.toJson(mockRoleDTO));
    }

    @Test
    void doGet() throws IOException {
        int roleId = 1;
        String roleName = "admin";
        String description = "do stuff";

        when(req.getPathInfo()).thenReturn("/" + roleId);

        Role mockRole = new Role(roleId, roleName, description);
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(roleName);
        mockRoleDTO.setDescription(description);

        when(roleService.getRoleById(roleId)).thenReturn(mockRole);
        when(roleMapper.roleToRoleDTO(mockRole)).thenReturn(mockRoleDTO);

        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(gson.toJson(mockRoleDTO))));
        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        roleServlet.doGet(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
        verify(out).println(gson.toJson(mockRoleDTO));
    }

    @Test
    void doPut() throws IOException {
        int roleId = 1;
        String roleName = "admin";
        String description = "do stuff";

        when(req.getPathInfo()).thenReturn("/" + roleId);

        Role mockRole = new Role(roleId, roleName, description);
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(roleName);
        mockRoleDTO.setDescription(description);

        when(roleService.updateRoleById(roleId, roleName, description)).thenReturn(mockRole);
        when(roleMapper.roleToRoleDTO(mockRole)).thenReturn(mockRoleDTO);

        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(gson.toJson(mockRoleDTO))));
        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        roleServlet.doPut(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
        verify(out).println(gson.toJson(mockRoleDTO));
    }

    @Test
    void doDelete() {
        int roleId = 1;
        when(req.getPathInfo()).thenReturn("/" + roleId);

        roleServlet.doDelete(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
    }
}