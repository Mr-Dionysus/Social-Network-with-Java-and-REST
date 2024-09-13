package org.example.servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dtos.UserDTO;
import org.example.entities.User;
import org.example.mappers.UserMapper;
import org.example.mappers.UserMapperImpl;
import org.example.repositories.UserRepository;
import org.example.repositories.UserRolesRepository;
import org.example.services.UserRolesServiceImpl;
import org.example.services.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "UserRolesServlet", urlPatterns = "/users/roles/*")
public class UserRolesServlet extends HttpServlet {
    private static final UserRepository USER_REPOSITORY = new UserRepository();
    private static final UserServiceImpl USER_SERVICE = new UserServiceImpl(USER_REPOSITORY);

    private static final UserRolesRepository USER_ROLES_REPOSITORY = new UserRolesRepository();
    private static final UserRolesServiceImpl USER_ROLES_SERVICE = new UserRolesServiceImpl(USER_ROLES_REPOSITORY);
    private static final UserMapper USER_MAPPER = new UserMapperImpl();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        String[] listPath = path.split("/");
        Gson gson = new Gson();

        try (PrintWriter out = resp.getWriter()) {
            int userId = Integer.parseInt(listPath[1]);
            int roleId = Integer.parseInt(listPath[2]);
            USER_ROLES_SERVICE.addRoleToUser(userId, roleId);
            User updatedUser = USER_SERVICE.getUserById(userId);
            UserDTO updatedUserDTO = USER_MAPPER.userToUserDTO(updatedUser);

            out.println(gson.toJson(updatedUserDTO));
            out.println(gson.toJson(updatedUserDTO.getRoles()));
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
