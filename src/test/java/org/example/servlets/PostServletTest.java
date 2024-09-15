package org.example.servlets;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dtos.PostDTO;
import org.example.entities.Post;
import org.example.mappers.PostMapper;
import org.example.services.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;

import static org.mockito.Mockito.*;

class PostServletTest {
    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private PostServiceImpl postService;

    @Mock
    private PostMapper postMapper;

    private Gson gson;
    private PostServlet postServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gson = new Gson();
        postServlet = new PostServlet(postService, postMapper);
    }

    @Test
    @DisplayName("Create a Post")
    void doPost() throws IOException {
        int postId = 1;
        String text = "hello there";
        int likes = 0;
        int dislikes = 0;
        int userId = 1;

        when(req.getPathInfo()).thenReturn("/" + userId);

        Post mockPost = new Post(postId, text, likes, dislikes);
        PostDTO postDTO = new PostDTO();
        postDTO.setText(text);
        postDTO.setLikes(likes);
        postDTO.setDislikes(dislikes);

        when(postService.createPost(text, userId)).thenReturn(mockPost);
        when(postMapper.postToPostDTO(mockPost)).thenReturn(postDTO);

        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(gson.toJson(postDTO))));
        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        postServlet.doPost(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_CREATED);
        verify(out).println(gson.toJson(postDTO));
    }

    @Test
    @DisplayName("Get a Post")
    void doGet() throws IOException {
        int postId = 1;
        String text = "hello there";
        int likes = 0;
        int dislikes = 0;

        when(req.getPathInfo()).thenReturn("/1");
        Post mockPost = new Post(postId, text, likes, dislikes);
        PostDTO mockPostDTO = new PostDTO(text, likes, dislikes);

        when(postService.getPostById(1)).thenReturn(mockPost);
        when(postMapper.postToPostDTO(mockPost)).thenReturn(mockPostDTO);

        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        postServlet.doGet(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
        verify(out).println(gson.toJson(mockPostDTO));
    }

    @Test
    @DisplayName("Update a Post")
    void doPut() throws IOException {
        int postId = 1;
        String text = "hello there";
        int likes = 0;
        int dislikes = 0;
        int userId = 1;

        when(req.getPathInfo()).thenReturn("/" + userId);

        Post mockPost = new Post(postId, text, likes, dislikes);
        PostDTO postDTO = new PostDTO();
        postDTO.setText(text);
        postDTO.setLikes(likes);
        postDTO.setDislikes(dislikes);

        when(postService.updatePostById(postId, text)).thenReturn(mockPost);
        when(postMapper.postToPostDTO(mockPost)).thenReturn(postDTO);

        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(gson.toJson(postDTO))));
        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        postServlet.doPut(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
        verify(out).println(gson.toJson(postDTO));
    }

    @Test
    @DisplayName("Delete a Post")
    void doDelete() {
        int userId = 1;

        when(req.getPathInfo()).thenReturn("/" + userId);

        postServlet.doDelete(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
    }
}