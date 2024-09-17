package org.example.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dtos.UserCredentialsDTO;
import org.example.dtos.UserDTO;
import org.example.entities.User;
import org.example.mappers.UserMapper;
import org.example.mappers.UserMapperImpl;
import org.example.services.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet", urlPatterns = "/users/*")
public class UserServlet extends HttpServlet {
    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    public UserServlet() {
        this.userService = UserServiceImpl.createUserService();
        this.userMapper = new UserMapperImpl();
    }

    public UserServlet(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();

        try {
            PrintWriter out = resp.getWriter();
            UserCredentialsDTO userCredentialsDTO = gson.fromJson(req.getReader(), UserCredentialsDTO.class);
            User createdUser = userService.createUser(userCredentialsDTO.getLogin(), userCredentialsDTO.getPassword());
            UserDTO createdUserDTO = userMapper.userToUserDTO(createdUser);

            out.println(gson.toJson(createdUserDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IOException | JsonSyntaxException | JsonIOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        Gson gson = new Gson();

        try {
            PrintWriter out = resp.getWriter();
            int id = Integer.parseInt(path.split("/")[1]);
            User foundUser = userService.getUserById(id);
            UserDTO foundUserDTO = userMapper.userToUserDTO(foundUser);

            out.println(gson.toJson(foundUserDTO));
            out.flush();

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException | IOException e) {
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
            UserCredentialsDTO userCredentialsDTO = gson.fromJson(req.getReader(), UserCredentialsDTO.class);
            User updatedUser = userService.updateUserById(id, userCredentialsDTO.getLogin(), userCredentialsDTO.getPassword());
            UserDTO updatedUserDTO = userMapper.userToUserDTO(updatedUser);

            out.println(gson.toJson(updatedUserDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException | JsonSyntaxException | JsonIOException | NumberFormatException e) {
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
            userService.deleteUserById(id);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
