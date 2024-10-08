package org.example.mappers;

import org.example.dtos.UserCredentialsDTO;
import org.example.dtos.UserDTO;
import org.example.entities.Post;
import org.example.entities.Role;
import org.example.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(UserMapperImpl.class);
        context.refresh();
        userMapper = context.getBean(UserMapper.class);
    }

    private ArrayList<Post> createExpectedListPosts() {
        int expectedPostId = 1;
        String expectedText = "test text";
        int expectedLikes = 0;
        int expectedDislikes = 0;
        Post expectedPost = new Post(expectedPostId, expectedText, expectedLikes, expectedDislikes);
        ArrayList<Post> expectedListPosts = new ArrayList<>();
        expectedListPosts.add(expectedPost);

        return expectedListPosts;
    }

    private ArrayList<Role> createExpectedListRoles() {
        int expectedRoleId = 1;
        String expectedRoleName = "admin";
        String expectedDescription = "manage stuff";
        Role expectedRole = new Role(expectedRoleId, expectedRoleName, expectedDescription);
        ArrayList<Role> expectedListRoles = new ArrayList<>();
        expectedListRoles.add(expectedRole);

        return expectedListRoles;
    }

    @Test
    @DisplayName("User to UserDTO")
    void userToUserDTO() {
        int expectedUserId = 1;
        String expectedLogin = "login";
        String expectedPassword = "password";

        ArrayList<Post> expectedListPosts = this.createExpectedListPosts();
        ArrayList<Role> expectedListRoles = this.createExpectedListRoles();

        User userBeforeUserDTO = new User(expectedUserId, expectedLogin, expectedPassword);
        userBeforeUserDTO.setRoles(expectedListRoles);
        userBeforeUserDTO.setPosts(expectedListPosts);

        UserDTO expectedUser = new UserDTO();
        expectedUser.setLogin(expectedLogin);

        UserDTO actualUser = userMapper.userToUserDTO(userBeforeUserDTO);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("UserDTO to User")
    void userDTOtoUser() {
        String expectedLogin = "login";

        ArrayList<Post> expectedListPosts = this.createExpectedListPosts();
        ArrayList<Role> expectedListRoles = this.createExpectedListRoles();

        UserDTO userDTObeforeUser = new UserDTO();
        userDTObeforeUser.setLogin(expectedLogin);
        userDTObeforeUser.setRoles(expectedListRoles);
        userDTObeforeUser.setPosts(expectedListPosts);

        User expectedUser = new User();
        expectedUser.setLogin(expectedLogin);
        expectedUser.setRoles(expectedListRoles);
        expectedUser.setPosts(expectedListPosts);

        User actualUser = userMapper.userDTOtoUser(userDTObeforeUser);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("User to UserCredentialsDTO")
    void userToUserCredentialsDTO() {
        int expectedUserId = 1;
        String expectedLogin = "login";
        String expectedPassword = "password";

        ArrayList<Post> expectedListPosts = this.createExpectedListPosts();
        ArrayList<Role> expectedListRoles = this.createExpectedListRoles();

        User userBeforeUserCredentialsDTO = new User(expectedUserId, expectedLogin, expectedPassword);
        userBeforeUserCredentialsDTO.setRoles(expectedListRoles);
        userBeforeUserCredentialsDTO.setPosts(expectedListPosts);

        UserCredentialsDTO expectedUser = new UserCredentialsDTO();
        expectedUser.setLogin(expectedLogin);
        expectedUser.setPassword(expectedPassword);

        UserCredentialsDTO actualUser = userMapper.userToUserCredentialsDTO(userBeforeUserCredentialsDTO);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("UserCredentialsDTO to User")
    void userCredentialsDTOtoUser() {
        String expectedLogin = "login";
        String expectedPassword = "password";

        UserCredentialsDTO userDTOcredentialsBeforeUser = new UserCredentialsDTO();
        userDTOcredentialsBeforeUser.setLogin(expectedLogin);
        userDTOcredentialsBeforeUser.setPassword(expectedPassword);

        User expectedUser = new User();
        expectedUser.setLogin(expectedLogin);
        expectedUser.setPassword(expectedPassword);

        User actualUser = userMapper.userCredentialsDTOtoUser(userDTOcredentialsBeforeUser);

        assertEquals(expectedUser, actualUser);
    }
}