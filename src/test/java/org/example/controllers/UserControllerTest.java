package org.example.controllers;

import org.example.dtos.UserCredentialsDTO;
import org.example.dtos.UserDTO;
import org.example.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create a User")
    void createUser() {
        String login = "admin4";
        String password = "password";

        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
        userCredentialsDTO.setLogin(login);
        userCredentialsDTO.setPassword(password);
        userCredentialsDTO.hashCode();

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setLogin(login);

        when(userService.createUser(login, password)).thenReturn(mockUserDTO);

        ResponseEntity<UserDTO> response = userController.createUser(userCredentialsDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockUserDTO, response.getBody());

        verify(userService).createUser(login, password);
    }

    @Test
    @DisplayName("Get a User by ID")
    void getUserById() {
        int userId = 1;
        String login = "admin4";

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setLogin(login);

        when(userService.getUserById(userId)).thenReturn(mockUserDTO);

        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUserDTO, response.getBody());

        verify(userService).getUserById(userId);
    }

    @Test
    @DisplayName("Update a User by ID")
    void updateUserById() {
        int userId = 1;
        String newLogin = "newLogin";
        String newPassword = "newPassword";

        UserCredentialsDTO mockUserCredentialsDTO = new UserCredentialsDTO();
        mockUserCredentialsDTO.setLogin(newLogin);
        mockUserCredentialsDTO.setPassword(newPassword);

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setLogin(newLogin);

        when(userService.updateUserById(userId, newLogin, newPassword)).thenReturn(mockUserDTO);
        ResponseEntity<UserDTO> response = userController.updateUserById(userId, mockUserCredentialsDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUserDTO, response.getBody());

        verify(userService).updateUserById(userId, mockUserCredentialsDTO.getLogin(), mockUserCredentialsDTO.getPassword());
        assertEquals(mockUserDTO.getLogin(), newLogin);
    }

    @Test
    @DisplayName("Delete a User by ID")
    void deleteUserById() {
        int userId = 1;
        String login = "admin4";
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setLogin(login);

        when(userService.getUserById(userId)).thenReturn(mockUserDTO);

        ResponseEntity<Void> response = userController.deleteUserById(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(userService).deleteUserById(userId);
    }
}