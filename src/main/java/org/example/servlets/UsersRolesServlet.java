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
import org.example.repositories.RoleRepository;
import org.example.repositories.UsersRolesRepository;
import org.example.services.RoleServiceImpl;
import org.example.services.UsersRolesServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UsersRolesServlet", urlPatterns = "/roles/users/*")
public class UsersRolesServlet extends HttpServlet {
    private static final RoleRepository ROLE_REPOSITORY = new RoleRepository();
    private final transient RoleServiceImpl roleService;
    private final transient RoleMapper roleMapper;

    private static final UsersRolesRepository USERS_ROLES_REPOSITORY = new UsersRolesRepository();
    private final transient UsersRolesServiceImpl usersRolesService;

    public UsersRolesServlet() {
        this.roleService = new RoleServiceImpl(ROLE_REPOSITORY);
        this.usersRolesService = new UsersRolesServiceImpl(USERS_ROLES_REPOSITORY);
        this.roleMapper = new RoleMapperImpl();
    }

    public UsersRolesServlet(RoleServiceImpl roleService, UsersRolesServiceImpl usersRolesService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.usersRolesService = usersRolesService;
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
            usersRolesService.assignRoleToUser(userId, roleId);
            Role updatedRole = roleService.getRoleById(roleId);
            RoleDTO updatedRoleDTO = roleMapper.roleToRoleDTO(updatedRole);

            out.println(gson.toJson(updatedRoleDTO));
            out.println(gson.toJson(updatedRoleDTO.getUsers()));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
