package org.example.servlets;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dtos.PostDTO;
import org.example.dtos.UserCredentialsDTO;
import org.example.dtos.UserDTO;
import org.example.entities.Post;
import org.example.entities.User;
import org.example.mappers.PostMapper;
import org.example.mappers.UserMapper;
import org.example.services.PostService;
import org.example.services.UserService;
import org.example.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServletTest {
    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private PostService postService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PostMapper postMapper;

    private Gson gson;
    private UserServlet userServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gson = new Gson();
        userServlet = new UserServlet(userService, userMapper);
    }

    @Test
    void doPost() throws IOException {
        int userId = 1;
        String login = "admin4";
        String password = "password";

        when(req.getPathInfo()).thenReturn("/" + userId);

        User mockUser = new User(userId, login, password);
        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
        userCredentialsDTO.setLogin(login);
        userCredentialsDTO.setPassword(password);

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setLogin(login);

        when(userService.createUser(login, password)).thenReturn(mockUser);
        when(userMapper.userToUserDTO(mockUser)).thenReturn(mockUserDTO);

        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(gson.toJson(userCredentialsDTO))));
        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        userServlet.doPost(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_CREATED);
        verify(out).println(gson.toJson(mockUserDTO));
    }

    @Test
    void doGet() throws IOException {
        int userId = 1;
        String login = "admin4";
        String password = "password";

        when(req.getPathInfo()).thenReturn("/" + userId);

        User mockUser = new User(userId, login, password);

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setLogin(login);

        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(userMapper.userToUserDTO(mockUser)).thenReturn(mockUserDTO);

        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        userServlet.doGet(req, resp);

        verify(resp).setContentType("text/html");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
        verify(out).println(gson.toJson(mockUserDTO));
    }

    @Test
    void doPut() throws IOException {
        int userId = 1;
        String newLogin = "newLogin";
        String newPassword = "newPassword";
        when(req.getPathInfo()).thenReturn("/" + userId);

        User mockUser = new User(userId, newLogin, newPassword);
        UserCredentialsDTO mockUserCredentialsDTO = new UserCredentialsDTO();
        mockUserCredentialsDTO.setLogin(newLogin);
        mockUserCredentialsDTO.setPassword(newPassword);

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setLogin(newLogin);

        when(userService.updateUserById(userId, newLogin, newPassword)).thenReturn(mockUser);
        when(userMapper.userToUserDTO(mockUser)).thenReturn(mockUserDTO);
        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(gson.toJson(mockUserCredentialsDTO))));
        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        userServlet.doPut(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
        verify(out).println(gson.toJson(mockUserDTO));
        assertEquals(mockUserDTO.getLogin(), newLogin);
    }

    @Test
    void doDelete() throws IOException {
        int userId = 1;
        String login = "admin4";
        String password = "password";
        User mockUser = new User(userId, login, password);

        when(req.getPathInfo()).thenReturn("/" + userId);
        when(userService.getUserById(userId)).thenReturn(mockUser);
        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        userServlet.doDelete(req, resp);

        verify(resp).setContentType("text/html");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
    }
}