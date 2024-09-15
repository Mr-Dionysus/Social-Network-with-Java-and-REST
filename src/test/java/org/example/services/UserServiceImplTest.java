package org.example.services;

import org.example.entities.User;
import org.example.exceptions.UserNotFoundException;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create a User")
    void createUser() throws SQLException {
        int userId = 1;
        String login = "root";
        String password = "password";
        User mockUser = new User(userId, login, password);
        when(userRepository.createUser(login, password)).thenReturn(mockUser);

        User actualUser = userService.createUser(login, password);

        assertNotNull(actualUser);
        assertEquals(mockUser, actualUser);

        verify(userRepository, times(1)).createUser(login, password);
    }

    @Test
    @DisplayName("Get a User by ID")
    void getUserById() throws SQLException {
        int userId = 1;
        String login = "root";
        String password = "password";
        User mockUser = new User(userId, login, password);
        when(userRepository.getUserById(userId)).thenReturn(mockUser);

        User actualUser = userService.getUserById(userId);

        assertNotNull(actualUser);
        assertEquals(mockUser, actualUser);

        verify(userRepository, times(1)).getUserById(userId);
    }

    @Test
    @DisplayName("Get a User by ID without his roles")
    void getUserByIdWithoutHisRoles() throws SQLException {
        int userId = 1;
        String login = "root";
        String password = "password";
        User mockUser = new User(userId, login, password);
        when(userRepository.getUserWithoutHisRoles(userId)).thenReturn(mockUser);

        User actualUser = userService.getUserByIdWithoutHisRoles(userId);

        assertNotNull(actualUser);
        assertEquals(mockUser, actualUser);

        verify(userRepository, times(1)).getUserWithoutHisRoles(userId);
    }

    @Test
    @DisplayName("Update a User by ID")
    void updateUserById() throws SQLException {
        int userId = 1;
        String login = "root";
        String password = "password";
        User mockUser = new User(userId, login, password);

        String newLogin = "user";
        String newPassword = "p@ssw@rd";
        mockUser.setLogin(newLogin);
        mockUser.setPassword(newPassword);
        when(userRepository.updateUserById(userId, newLogin, newPassword)).thenReturn(mockUser);

        User actualUser = userService.updateUserById(userId, newLogin, newPassword);

        assertNotNull(actualUser);
        assertEquals(mockUser, actualUser);

        verify(userRepository, times(1)).updateUserById(userId, newLogin, newPassword);
    }

    @Test
    @DisplayName("Delete a User by ID")
    void deleteUserById() throws SQLException {
        int userId = 1;
        String expectedMessage = "User with ID '1' can't be found";
        doThrow(new UserNotFoundException(expectedMessage)).when(userRepository)
                                                           .deleteUserById(userId);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUserById(userId);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}