package org.example.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dtos.UserCredentialsDTO;
import org.example.dtos.UserDTO;
import org.example.entities.Post;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.mappers.UserMapper;
import org.example.mappers.UserMapperImpl;
import org.example.repositories.UserRepository;
import org.example.services.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet", urlPatterns = "/users/*")
public class UserServlet extends HttpServlet {
    private static final UserRepository USER_REPOSITORY = new UserRepository();
    private final transient UserServiceImpl userService;
    private final transient UserMapper userMapper;

    public UserServlet() {
        this.userService = new UserServiceImpl(USER_REPOSITORY);
        this.userMapper = new UserMapperImpl();
    }

    public UserServlet(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login != null && password != null) {
            this.createUserByURL(req, resp, login, password);
        } else {
            this.createUserByJSON(req, resp);
        }
    }

    private void createUserByURL(HttpServletRequest req, HttpServletResponse resp, String login, String password) {
        resp.setContentType("text/html");
        String cssTag = "<link href='" + req.getContextPath() + "/css/style.css' rel='stylesheet' type='text/css'>";

        try (PrintWriter out = resp.getWriter();) {
            User createdUser = userService.createUser(login, password);
            UserDTO createdUserDTO = userMapper.userToUserDTO(createdUser);

            out.println("<html>");
            out.println("<head><title>Post User</title>" + cssTag + "</head>");
            out.println("<body><b>" + createdUserDTO.getLogin() + ", your account was created!</b></body>");
            out.println("</html>");
            out.flush();
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    private void createUserByJSON(HttpServletRequest req, HttpServletResponse resp) {
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
        resp.setContentType("text/html");
        String path = req.getPathInfo();

        if (path == null) {
            this.showWebpageForLogin(req, resp);
        } else {
            this.getUserInJSON(req, resp, path);
        }
    }

    private void showWebpageForLogin(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/index.jsp");

        try {
            dispatcher.forward(req, resp);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (ServletException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    private void getUserInJSON(HttpServletRequest req, HttpServletResponse resp, String path) {
        String cssTag = "<link href='" + req.getContextPath() + "/css/style.css' rel='stylesheet' type='text/css'>";
        Gson gson = new Gson();

        try {
            PrintWriter out = resp.getWriter();
            int id = Integer.parseInt(path.split("/")[1]);
            User foundUser = userService.getUserById(id);
            UserDTO foundUserDTO = userMapper.userToUserDTO(foundUser);

            out.println("<html>");
            out.println("<head><title>Get User</title>" + cssTag + "</head>");
            out.println("<body>");
            out.println("<p><b>Login:</b>" + foundUser.getLogin() + "</p>");
            out.println("<p>Your Roles:</p>");

            if (foundUserDTO.getRoles() != null) {
                for (Role role : foundUserDTO.getRoles()) {
                    out.println("<p>" + role + "</p>");
                }
            }

            out.println("<p>Your Posts:</p>");

            if (foundUserDTO.getPosts() != null) {
                for (Post post : foundUserDTO.getPosts()) {
                    out.println("<p>" + post + "</p>");
                }
            }

            out.println("</body>");
            out.println("</html>");
            out.println(gson.toJson(foundUserDTO));
            out.println(gson.toJson(foundUserDTO.getRoles()));
            out.println(gson.toJson(foundUserDTO.getPosts()));
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
        String newLogin = req.getParameter("newLogin");
        String newPassword = req.getParameter("newPassword");
        String path = req.getPathInfo();

        if (newLogin != null && newPassword != null) {
            this.putUserByURL(req, resp, newLogin, newPassword, path);
        } else {
            this.putUserByJSON(req, resp, path);
        }
    }

    private void putUserByURL(HttpServletRequest req, HttpServletResponse resp, String newLogin, String newPassword, String path) {
        resp.setContentType("text/html");
        String cssTag = "<link href='" + req.getContextPath() + "/css/style.css' rel='stylesheet' type='text/css'>";
        Gson gson = new Gson();

        try {
            PrintWriter out = resp.getWriter();
            int id = Integer.parseInt(path.split("/")[1]);
            User updatedUser = userService.updateUserById(id, newLogin, newPassword);
            UserDTO updatedUserDTO = userMapper.userToUserDTO(updatedUser);

            out.println("<html>");
            out.println("<head><title>Put User</title>" + cssTag + "</head>");
            out.println("<body>");
            out.println("<p><b>New Login:</b> " + newLogin + "</p>");
            out.println("</body>");
            out.println("</html>");
            out.println(gson.toJson(updatedUserDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    private void putUserByJSON(HttpServletRequest req, HttpServletResponse resp, String path) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
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
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (JsonIOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        String cssTag = "<link href='" + req.getContextPath() + "/css/style.css' " + "rel='stylesheet'" + " type='text/css'>";
        String path = req.getPathInfo();

        try {
            PrintWriter out = resp.getWriter();
            int id = Integer.parseInt(path.split("/")[1]);
            String login = userService.getUserById(id)
                                      .getLogin();
            userService.deleteUserById(id);

            out.println("<html>");
            out.println("<head><title>Delete User</title>" + cssTag + "</head>");
            out.println("<body>");
            out.println("<p><b>" + login + "</b>, your account was deleted.</p>");
            out.println("</body>");
            out.println("</html>");
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
