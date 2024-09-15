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

@WebServlet(name = "PostServlet", urlPatterns = "/users/posts/*")
public class PostServlet extends HttpServlet {
    private final PostServiceImpl postService;
    private final PostMapper postMapper;

    public PostServlet() {
        this.postService = createPostService();
        this.postMapper = new PostMapperImpl();
    }

    protected PostServiceImpl createPostService() {
        return new PostServiceImpl(new PostRepository());
    }

    public PostServlet(PostServiceImpl postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

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
            Post createdPost = postService.createPost(postDTO.getText(), userId);
            postDTO = postMapper.postToPostDTO(createdPost);

            out.println(gson.toJson(postDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IOException | NumberFormatException | JsonSyntaxException | JsonIOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
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
            Post foundPost = postService.getPostById(postId);
            PostDTO postDTO = postMapper.postToPostDTO(foundPost);

            out.println(gson.toJson(postDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException | NumberFormatException | JsonSyntaxException | JsonIOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
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
            Post updatedPost = postService.updatePostById(postId, postDTO.getText());
            postDTO = postMapper.postToPostDTO(updatedPost);

            out.println(gson.toJson(postDTO));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException | NumberFormatException | JsonSyntaxException | JsonIOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();

        try {
            int postId = Integer.parseInt(path.split("/")[1]);
            postService.deletePostById(postId);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
