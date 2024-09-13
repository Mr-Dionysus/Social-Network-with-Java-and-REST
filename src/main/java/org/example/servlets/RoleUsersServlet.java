package org.example.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
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
    private final transient RoleRepository ROLE_REPOSITORY = new RoleRepository();
    private final transient RoleServiceImpl ROLE_SERVICE = new RoleServiceImpl(ROLE_REPOSITORY);
    private final transient RoleUsersRepository ROLE_USERS_REPOSITORY = new RoleUsersRepository();
    private final transient RoleUsersServiceImpl ROLE_USERS_SERVICE = new RoleUsersServiceImpl(ROLE_USERS_REPOSITORY);
    private final transient RoleMapper ROLE_MAPPER = new RoleMapperImpl();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        String[] listPath = path.split("/");

        try (PrintWriter out = resp.getWriter()) {
            int roleId = Integer.parseInt(listPath[1]);
            int userId = Integer.parseInt(listPath[2]);
            ROLE_USERS_SERVICE.addUserToRole(userId, roleId);
            Role updatedRole = ROLE_SERVICE.getRoleById(roleId);
            RoleDTO roleDTO = ROLE_MAPPER.roleToRoleDTO(updatedRole);
            Gson gson = new Gson();

            out.println(gson.toJson(roleDTO));
            out.println(gson.toJson(roleDTO.getUsers()));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
