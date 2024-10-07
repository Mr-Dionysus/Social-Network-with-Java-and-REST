package org.example.services;

import org.example.dtos.UserDTO;
import org.example.entities.User;
import org.example.exceptions.UserNotFoundException;
import org.example.mappers.UserMapper;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create a User")
    void createUser() {
        String login = "root";
        String password = "password";
        User mockUser = new User(login, password);
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setLogin(login);

        when(userMapper.userToUserDTO(any(User.class))).thenReturn(mockUserDTO);
        UserDTO actualUser = userService.createUser(login, password);

        assertNotNull(actualUser);
        assertEquals(mockUserDTO, actualUser);

        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    @DisplayName("Get a User by ID")
    void getUserById() {
        int userId = 1;
        String login = "root";
        String password = "password";
        User mockUser = new User(userId, login, password);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setLogin(login);

        when(userMapper.userToUserDTO(any(User.class))).thenReturn(mockUserDTO);
        UserDTO actualUser = userService.getUserById(userId);

        assertNotNull(actualUser);
        assertEquals(mockUserDTO, actualUser);

        verify(userRepository, times(2)).findById(userId);
    }

    @Test
    @DisplayName("Update a User by ID")
    void updateUserById() {
        int userId = 1;
        String login = "root";
        String password = "password";
        User mockUser = new User(userId, login, password);

        String newLogin = "user";
        String newPassword = "p@ssw@rd";
        mockUser.setLogin(newLogin);
        mockUser.setPassword(newPassword);

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setLogin(newLogin);

        when(userMapper.userToUserDTO(any(User.class))).thenReturn(mockUserDTO);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        UserDTO actualUser = userService.updateUserById(userId, newLogin, newPassword);

        assertNotNull(actualUser);
        assertEquals(mockUserDTO, actualUser);

        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    @DisplayName("Delete a User by ID")
    void deleteUserById() {
        int userId = 1;
        String expectedMessage = "Error while deleting the User. User with ID '1' can't be found";
        doThrow(new UserNotFoundException(expectedMessage)).when(userRepository)
                                                           .deleteById(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(userId));

        assertEquals(expectedMessage, exception.getMessage());
    }
}