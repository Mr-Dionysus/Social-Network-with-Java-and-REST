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

@WebServlet(name = "RoleUsersServlet", urlPatterns = "/roles/users/*")
public class RoleUsersServlet extends HttpServlet {
    private static final RoleRepository ROLE_REPOSITORY = new RoleRepository();
    private RoleServiceImpl ROLE_SERVICE = new RoleServiceImpl(ROLE_REPOSITORY);
    private RoleMapper ROLE_MAPPER = new RoleMapperImpl();

    private static final UsersRolesRepository USERS_ROLES_REPOSITORY = new UsersRolesRepository();
    private UsersRolesServiceImpl USERS_ROLES_SERVICE = new UsersRolesServiceImpl(USERS_ROLES_REPOSITORY);

    public RoleUsersServlet() {
        this.ROLE_SERVICE = new RoleServiceImpl(ROLE_REPOSITORY);
        this.USERS_ROLES_SERVICE = new UsersRolesServiceImpl(USERS_ROLES_REPOSITORY);
        this.ROLE_MAPPER = new RoleMapperImpl();
    }

    public RoleUsersServlet(RoleServiceImpl ROLE_SERVICE, UsersRolesServiceImpl USERS_ROLES_SERVICE, RoleMapper ROLE_MAPPER) {
        this.ROLE_SERVICE = ROLE_SERVICE;
        this.USERS_ROLES_SERVICE = USERS_ROLES_SERVICE;
        this.ROLE_MAPPER = ROLE_MAPPER;
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
            USERS_ROLES_SERVICE.assignRoleToUser(userId, roleId);
            Role updatedRole = ROLE_SERVICE.getRoleById(roleId);
            RoleDTO updatedRoleDTO = ROLE_MAPPER.roleToRoleDTO(updatedRole);

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
