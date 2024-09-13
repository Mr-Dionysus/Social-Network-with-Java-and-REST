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
import org.example.repositories.RoleUsersRepository;
import org.example.services.RoleServiceImpl;
import org.example.services.RoleUsersServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "RoleUsersServlet", urlPatterns = "/roles/users/*")
public class RoleUsersServlet extends HttpServlet {
    private static final RoleRepository ROLE_REPOSITORY = new RoleRepository();
    private static final RoleServiceImpl ROLE_SERVICE = new RoleServiceImpl(ROLE_REPOSITORY);

    private static final RoleUsersRepository ROLE_USERS_REPOSITORY = new RoleUsersRepository();
    private static final RoleUsersServiceImpl ROLE_USERS_SERVICE = new RoleUsersServiceImpl(ROLE_USERS_REPOSITORY);
    private static final RoleMapper ROLE_MAPPER = new RoleMapperImpl();

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
            ROLE_USERS_SERVICE.addUserToRole(userId, roleId);
            Role updatedRole = ROLE_SERVICE.getRoleById(roleId);
            RoleDTO updatedRoleDTO = ROLE_MAPPER.roleToRoleDTO(updatedRole);

            out.println(gson.toJson(updatedRoleDTO));
            out.println(gson.toJson(updatedRoleDTO.getUsers()));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
