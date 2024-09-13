package org.example.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dtos.PostDTO;
import org.example.entities.Post;
import org.example.mappers.PostMapper;
import org.example.mappers.PostMapperImpl;
import org.example.repositories.PostRepository;
import org.example.services.PostServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "PostServlet", urlPatterns = "/users/posts/*")
public class PostServlet extends HttpServlet {
    private static final PostRepository POST_REPOSITORY = new PostRepository();
    private static final PostServiceImpl POST_SERVICE = new PostServiceImpl(POST_REPOSITORY);
    private static final PostMapper POST_MAPPER = new PostMapperImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        Gson gson = new Gson();

        try {
            PrintWriter out = resp.getWriter();
            int userId = Integer.parseInt(path.split("/")[1]);

            PostDTO postDTO = gson.fromJson(req.getReader(), PostDTO.class);
            Post createdPost = POST_SERVICE.createPost(postDTO.getText(), userId);
            postDTO = POST_MAPPER.postToPostDTO(createdPost);

            out.println(gson.toJson(postDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
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
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        Gson gson = new Gson();

        try {
            PrintWriter out = resp.getWriter();
            int postId = Integer.parseInt(path.split("/")[1]);
            Post foundPost = POST_SERVICE.getPostById(postId);
            PostDTO postDTO = POST_MAPPER.postToPostDTO(foundPost);

            out.println(gson.toJson(postDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        Gson gson = new Gson();

        try {
            PrintWriter out = resp.getWriter();
            int postId = Integer.parseInt(path.split("/")[1]);
            PostDTO postDTO = gson.fromJson(req.getReader(), PostDTO.class);
            Post updatedPost = POST_SERVICE.updatePostById(postId, postDTO.getText());
            postDTO = POST_MAPPER.postToPostDTO(updatedPost);

            out.println(gson.toJson(postDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();

        try {
            int postId = Integer.parseInt(path.split("/")[1]);
            POST_SERVICE.deletePostById(postId);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
