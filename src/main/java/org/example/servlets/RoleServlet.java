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
    private static final RoleMapper ROLE_MAPPER = new RoleMapperImpl();
    private static final RoleRepository ROLE_REPOSITORY = new RoleRepository();
    private static final RoleServiceImpl ROLE_SERVICE = new RoleServiceImpl(ROLE_REPOSITORY);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();

        try {
            PrintWriter out = resp.getWriter();
            RoleDTO createdRoleDTO = gson.fromJson(req.getReader(), RoleDTO.class);
            Role createdRole = ROLE_MAPPER.roleDTOtoRole(createdRoleDTO);
            createdRole = ROLE_SERVICE.createRole(createdRole.getRoleName(), createdRole.getDescription());
            createdRoleDTO = ROLE_MAPPER.roleToRoleDTO(createdRole);

            out.print(gson.toJson(createdRoleDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (JsonIOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
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
            ArrayList<Role> listRoles = ROLE_SERVICE.getAllRoles();
            ArrayList<RoleDTO> listRolesDTO = new ArrayList<>();

            for (Role role : listRoles) {
                listRolesDTO.add(ROLE_MAPPER.roleToRoleDTO(role));
            }

            out.println(gson.toJson(listRolesDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    private void getRole(HttpServletResponse resp, String path) {
        try {
            Gson gson = new Gson();
            PrintWriter out = resp.getWriter();
            int id = Integer.parseInt(path.split("/")[1]);
            Role foundUser = ROLE_SERVICE.getRoleById(id);
            RoleDTO foundRoleDTO = ROLE_MAPPER.roleToRoleDTO(foundUser);

            out.println(gson.toJson(foundRoleDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
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
            Role updatedRole = ROLE_SERVICE.updateRoleById(id, updatedRoleDTO.getRoleName(), updatedRoleDTO.getDescription());
            updatedRoleDTO = ROLE_MAPPER.roleToRoleDTO(updatedRole);

            out.println(gson.toJson(updatedRoleDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (JsonIOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();

        try {
            int id = Integer.parseInt(path.split("/")[1]);
            ROLE_SERVICE.deleteRoleById(id);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
