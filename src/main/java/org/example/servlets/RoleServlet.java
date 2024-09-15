package org.example.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dtos.RoleDTO;
import org.example.entities.Role;
import org.example.mappers.RoleMapper;
import org.example.mappers.RoleMapperImpl;
import org.example.repositories.RoleRepository;
import org.example.services.RoleServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "RoleServlet", urlPatterns = "/roles/*")
public class RoleServlet extends HttpServlet {
    private static final RoleRepository ROLE_REPOSITORY = new RoleRepository();
    private final transient RoleServiceImpl roleService;
    private final transient RoleMapper roleMapper;

    public RoleServlet() {
        this.roleService = new RoleServiceImpl(ROLE_REPOSITORY);
        this.roleMapper = new RoleMapperImpl();
    }

    public RoleServlet(RoleServiceImpl roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();

        try {
            PrintWriter out = resp.getWriter();
            RoleDTO createdRoleDTO = gson.fromJson(req.getReader(), RoleDTO.class);
            Role createdRole = roleService.createRole(createdRoleDTO.getRoleName(), createdRoleDTO.getDescription());
            createdRoleDTO = roleMapper.roleToRoleDTO(createdRole);

            out.println(gson.toJson(createdRoleDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IOException | JsonIOException | JsonSyntaxException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();

        if (path == null) {
            this.getAllRoles(resp);
        } else {
            this.getRole(resp, path);
        }
    }

    private void getAllRoles(HttpServletResponse resp) {
        Gson gson = new Gson();

        try {
            PrintWriter out = resp.getWriter();
            ArrayList<Role> listRoles = roleService.getAllRoles();
            ArrayList<RoleDTO> listRolesDTO = new ArrayList<>();

            for (Role role : listRoles) {
                listRolesDTO.add(roleMapper.roleToRoleDTO(role));
            }

            out.println(gson.toJson(listRolesDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    private void getRole(HttpServletResponse resp, String path) {
        try {
            Gson gson = new Gson();
            PrintWriter out = resp.getWriter();
            int id = Integer.parseInt(path.split("/")[1]);
            Role foundUser = roleService.getRoleById(id);
            RoleDTO foundRoleDTO = roleMapper.roleToRoleDTO(foundUser);

            out.println(gson.toJson(foundRoleDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException | IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        Gson gson = new Gson();

        try {
            PrintWriter out = resp.getWriter();
            int id = Integer.parseInt(path.split("/")[1]);
            RoleDTO updatedRoleDTO = gson.fromJson(req.getReader(), RoleDTO.class);
            Role updatedRole = roleService.updateRoleById(id, updatedRoleDTO.getRoleName(), updatedRoleDTO.getDescription());
            updatedRoleDTO = roleMapper.roleToRoleDTO(updatedRole);

            out.println(gson.toJson(updatedRoleDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException | IOException | JsonSyntaxException | JsonIOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();

        try {
            int id = Integer.parseInt(path.split("/")[1]);
            roleService.deleteRoleById(id);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
