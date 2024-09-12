package org.example.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dtos.PostDTO;
import org.example.entities.Post;
import org.example.mappers.PostMapper;
import org.example.mappers.PostMapperImpl;
import org.example.mappers.UserMapper;
import org.example.mappers.UserMapperImpl;
import org.example.repositories.PostRepository;
import org.example.repositories.UserRepository;
import org.example.services.PostServiceImpl;
import org.example.services.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "PostServlet", urlPatterns = "/users/posts/*")
public class PostServlet extends HttpServlet {
    private final transient PostRepository POST_REPOSITORY = new PostRepository();
    private final transient PostServiceImpl POST_SERVICE = new PostServiceImpl(POST_REPOSITORY);
    private final transient PostMapper POST_MAPPER = new PostMapperImpl();
    private final transient UserRepository USER_REPOSITORY = new UserRepository();
    private final transient UserServiceImpl USER_SERVICE = new UserServiceImpl(USER_REPOSITORY);
    private final transient UserMapper USER_MAPPER = new UserMapperImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            String path = req.getPathInfo();
            int userId = Integer.parseInt(path.split("/")[1]);

            Gson gson = new Gson();
            PostDTO postDTO = gson.fromJson(req.getReader(), PostDTO.class);
            Post createdPost = POST_SERVICE.createPost(postDTO.getText(), userId);
            postDTO = POST_MAPPER.postToPostDTO(createdPost);

            out.println(gson.toJson(postDTO));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            String path = req.getPathInfo();
            int postId = Integer.parseInt(path.split("/")[1]);
            Post foundPost = POST_SERVICE.getPostById(postId);
            PostDTO postDTO = POST_MAPPER.postToPostDTO(foundPost);
            Gson gson = new Gson();

            out.println(gson.toJson(postDTO));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            String path = req.getPathInfo();
            int postId = Integer.parseInt(path.split("/")[1]);
            Gson gson = new Gson();
            PostDTO postDTO = gson.fromJson(req.getReader(), PostDTO.class);
            Post updatedPost = POST_SERVICE.updatePostById(postId, postDTO.getText());
            postDTO = POST_MAPPER.postToPostDTO(updatedPost);

            out.println(gson.toJson(postDTO));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String path = req.getPathInfo();
            int postId = Integer.parseInt(path.split("/")[1]);
            POST_SERVICE.deletePostById(postId);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
