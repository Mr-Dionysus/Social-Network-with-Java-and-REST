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
import org.example.entities.User;
import org.example.mappers.UserMapper;
import org.example.mappers.UserMapperImpl;
import org.example.repositories.UserRepository;
import org.example.services.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "UserServlet", urlPatterns = "/users/*")
public class UserServlet extends HttpServlet {
    private final transient UserRepository userRepository = new UserRepository();
    private final transient UserServiceImpl userService = new UserServiceImpl(userRepository);
    private final transient UserMapper userMapper = new UserMapperImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter("login") != null && req.getParameter("password") != null) {
            resp.setContentType("text/html");
            String cssTag = "<link href='" + req.getContextPath() + "/css/style.css' " + "rel='stylesheet'" + " type='text/css'>";

            try (PrintWriter out = resp.getWriter();) {
                String login = req.getParameter("login");
                String password = req.getParameter("password");
                User newUser = userService.createUser(login, password);
                UserDTO userDTO = userMapper.userToUserDTO(newUser);

                out.println("<html>");
                out.println("<head><title>Post User</title>" + cssTag + "</head>");
                out.println("<body><b>" + userDTO.getLogin() + ", your accout was " + "created!</b></body>");
                out.println("</html>");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            try (PrintWriter out = resp.getWriter();
            ) {
                Gson gson = new Gson();
                UserCredentialsDTO userCredentialsDTO = gson.fromJson(req.getReader(), UserCredentialsDTO.class);
                User newUser = userService.createUser(userCredentialsDTO.getLogin(), userCredentialsDTO.getPassword());
                UserDTO userDTO = userMapper.userToUserDTO(newUser);

                out.println(gson.toJson(userDTO));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (JsonSyntaxException e) {
                throw new RuntimeException(e);
            } catch (JsonIOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        String cssTag = "<link href='" + req.getContextPath() + "/css/style.css' " + "rel='stylesheet'" + " type='text/css'>";
        String path = req.getPathInfo();

        if (path == null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/index.jsp");

            try {
                dispatcher.forward(req, resp);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            try (PrintWriter out = resp.getWriter();) {
                int id = Integer.parseInt(path.split("/")[1]);
                User foundUser = userService.getUserById(id);
                UserDTO userDTO = userMapper.userToUserDTO(foundUser);
                Gson gson = new Gson();
                String userAsJSON = gson.toJson(userDTO);

                out.println("<html>");
                out.println("<head><title>Get User</title>" + cssTag + "</head>");
                out.println("<body>");
                out.println("<p><b>Login:</b>" + foundUser.getLogin() + "</p>");
                out.println("</body>");
                out.println("</html>");
                out.println(userAsJSON);

                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String newLogin = req.getParameter("newLogin");
        String newPassword = req.getParameter("newPassword");
        String path = req.getPathInfo();
        int id = -1;

        try {
            id = Integer.parseInt(path.split("/")[1]);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        if (newLogin != null && newPassword != null) {
            resp.setContentType("text/html");
            String cssTag = "<link href='" + req.getContextPath() + "/css/style.css' " + "rel='stylesheet'" + " type='text/css'>";

            try (PrintWriter out = resp.getWriter();) {
                User updatedUser = userService.updateUserById(id, newLogin, newPassword);
                UserDTO userDTO = userMapper.userToUserDTO(updatedUser);
                Gson gson = new Gson();

                out.println("<html>");
                out.println("<head><title>Put User</title>" + cssTag + "</head>");
                out.println("<body>");
                out.println("<p><b>New Login:</b> " + newLogin + "</p>");
                out.println("</body>");
                out.println("</html>");
                out.println(gson.toJson(userDTO));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            try (PrintWriter out = resp.getWriter()) {
                Gson gson = new Gson();
                UserCredentialsDTO userCredentialsDTO = gson.fromJson(req.getReader(), UserCredentialsDTO.class);
                User updatedUser = userService.updateUserById(id, userCredentialsDTO.getLogin(), userCredentialsDTO.getPassword());
                UserDTO userDTO = userMapper.userToUserDTO(updatedUser);

                out.println(gson.toJson(userDTO));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (JsonSyntaxException e) {
                throw new RuntimeException(e);
            } catch (JsonIOException e) {
                throw new RuntimeException(e);
            }
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        String cssTag = "<link href='" + req.getContextPath() + "/css/style.css' " + "rel='stylesheet'" + " type='text/css'>";

        try (PrintWriter out = resp.getWriter();) {
            String path = req.getPathInfo();
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
