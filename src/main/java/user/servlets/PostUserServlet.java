package user.servlets;

import entities.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.UserRepository;
import services.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "PostUserServlet", urlPatterns = "/insertData")
public class PostUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String cssTag = "<link href='style.css' rel='stylesheet' type='text/css'>";

        try (PrintWriter out = response.getWriter();) {
            out.println("<html><head><title>Insert Data</title>" + cssTag + "</head></html>");

            UserRepository userRepository = new UserRepository();
            UserServiceImpl userService = new UserServiceImpl(userRepository);
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            User user = new User(login, password);
            userService.createUser(user);
            userRepository.read("13");

            out.println("<html>");
            out.println("<head><title>Success</title>" + cssTag + "</head>");
            out.println("<body><b>Successfully Inserted" + "</b></body>");
            out.println("</html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
