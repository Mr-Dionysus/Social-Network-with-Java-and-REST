package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "HelloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try (Connection connection = DBConnection.initializeDB();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT login " + "FROM data");
        ) {
//            statement.setInt(1, Integer.parseInt(request.getParameter("login")));
//            statement.executeUpdate();

            PrintWriter out = response.getWriter();
            out.println("<html><body><b>Successfully Inserted" + "</b></body></html>");
            int login;

            while (resultSet.next()) {
                login = resultSet.getInt("login");
                System.out.println("login : " + login);
            }

            response.setContentType("text/html");
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Hello</h1>");
            response.getWriter().println("</body></html>");
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to SQL");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load Driver");
        } catch (IOException e) {
            throw new RuntimeException("Can't read a file");
        }
    }
}
