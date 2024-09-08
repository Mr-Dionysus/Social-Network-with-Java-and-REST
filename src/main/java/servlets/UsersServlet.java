package servlets;

import entities.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.UserRepository;
import services.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "UsersServlet", urlPatterns = "/users/*")
public class UsersServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String cssTag = "<link href='" + req.getContextPath() + "/css/style.css' " + "rel='stylesheet'" + " type='text/css'>";

        try (PrintWriter out = resp.getWriter();) {
            UserRepository userRepository = new UserRepository();
            UserServiceImpl userService = new UserServiceImpl(userRepository);
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            User user = new User(login, password);
            userService.createUser(user);
            resp.setStatus(HttpServletResponse.SC_CREATED);

            out.println("<html>");
            out.println("<head><title>Success</title>" + cssTag + "</head>");
            out.println("<body><b>Successfully Inserted</b></body>");
            out.println("</html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        String cssTag = "<link href='" + req.getContextPath() + "/css/style.css' " + "rel='stylesheet'" + " type='text/css'>";
        String path = req.getPathInfo();

        if (path == null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/index.jsp");
            dispatcher.forward(req, resp);
            resp.setStatus(HttpServletResponse.SC_OK);
            //        } else if (path.equals("/all")) {
            //            try (PrintWriter out = resp.getWriter();) {
            //                UserRepository userRepository = new UserRepository();
            //                UserServiceImpl userService = new UserServiceImpl(userRepository);
            //                System.out.println(login);
            //                User user = userService.getUserByLogin(login);
            //
            //                out.println("<html>");
            //                out.println("<head><title>Get User</title>" + cssTag + "</head>");
            //                out.println("<body>");
            //                out.println("<p>Login: <b>" + user.getLogin() + "</b></p>");
            //                out.println("<p>Password: <b>" + user.getPassword() + "</b></p>");
            //                out.println("</body>");
            //                out.println("</html>");
            //            }
            //        }
        } else {
            try (PrintWriter out = resp.getWriter();) {
                UserRepository userRepository = new UserRepository();
                UserServiceImpl userService = new UserServiceImpl(userRepository);
                String login = path.substring(1);
                User user = userService.getUserByLogin(login);
                resp.setStatus(HttpServletResponse.SC_OK);

                out.println("<html>");
                out.println("<head><title>Get User</title>" + cssTag + "</head>");
                out.println("<body>");
                out.println("<p>Login: <b>" + user.getLogin() + "</b></p>");
                out.println("<p>Password: <b>" + user.getPassword() + "</b></p>");
                out.println("</body>");
                out.println("</html>");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        String cssTag = "<link href='" + req.getContextPath() + "/css/style.css' " + "rel='stylesheet'" + " type='text/css'>";

        try (PrintWriter out = resp.getWriter();) {
            out.println("<head><title>Put</title>" + cssTag + "</head>");

            UserRepository userRepository = new UserRepository();
            UserServiceImpl userService = new UserServiceImpl(userRepository);
            String oldLogin = req.getParameter("oldLogin");
            String newLogin = req.getParameter("newLogin");
            String newPassword = req.getParameter("newPassword");
            userService.updateUserByLogin(oldLogin, newLogin, newPassword);
            resp.setStatus(HttpServletResponse.SC_OK);

            out.println("<html>");
            out.println("<head><title>Success</title>" + cssTag + "</head>");
            out.println("<body>");
            out.println("<p>Old Login: <b>" + oldLogin + "</b></p>");
            out.println("<p>New Login: <b>" + newLogin + "</b></p>");
            out.println("<p>New Password: <b>" + newPassword + "</b></p>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        String cssTag = "<link href='" + req.getContextPath() + "/css/style.css' " + "rel='stylesheet'" + " type='text/css'>";

        try (PrintWriter out = resp.getWriter();) {
            out.println("<head><title>Delete</title>" + cssTag + "</head>");

            UserRepository userRepository = new UserRepository();
            UserServiceImpl userService = new UserServiceImpl(userRepository);
            String path = req.getPathInfo();
            String login = null;

            if (path != null) {
                login = path.substring(1);
            }

            userService.deleteUserByLogin(login);
            resp.setStatus(HttpServletResponse.SC_OK);

            out.println("<html>");
            out.println("<head><title>Success</title>" + cssTag + "</head>");
            out.println("<body>");
            out.println("<p>" + login + ", your account was deleted.</p>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
