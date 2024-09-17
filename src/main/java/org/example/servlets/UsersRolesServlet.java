package org.example.servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dtos.RoleDTO;
import org.example.entities.Role;
import org.example.mappers.RoleMapper;
import org.example.mappers.RoleMapperImpl;
import org.example.services.RoleService;
import org.example.services.RoleServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UsersRolesServlet", urlPatterns = "/roles/users/*")
public class UsersRolesServlet extends HttpServlet {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public UsersRolesServlet() {
        this.roleService = RoleServiceImpl.createRoleService();
        this.roleMapper = new RoleMapperImpl();
    }

    public UsersRolesServlet(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        String[] listPath = path.split("/");
        Gson gson = new Gson();

        try {
            PrintWriter out = resp.getWriter();
            int roleId = Integer.parseInt(listPath[1]);
            int userId = Integer.parseInt(listPath[2]);
            roleService.assignRoleToUser(userId, roleId);
            Role updatedRole = roleService.getRoleById(roleId);
            RoleDTO updatedRoleDTO = roleMapper.roleToRoleDTO(updatedRole);

            out.println(gson.toJson(updatedRoleDTO));
            out.println(gson.toJson(updatedRoleDTO.getUsers()));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException | IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
