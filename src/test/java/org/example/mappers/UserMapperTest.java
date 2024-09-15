package org.example.mappers;

import org.example.dtos.UserCredentialsDTO;
import org.example.dtos.UserDTO;
import org.example.entities.Post;
import org.example.entities.Role;
import org.example.entities.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private final UserMapper userMapper = new UserMapperImpl();

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
    void userToUserDTO() {
        int expectedUserId = 1;
        String expectedLogin = "login";
        String expectedPassword = "password";

        ArrayList<Post> expectedListPosts = this.createExpectedListPosts();
        ArrayList<Role> expectedListRoles = this.createExpectedListRoles();

        User userBeforeUserDTO = new User();
        userBeforeUserDTO.setId(expectedUserId);
        userBeforeUserDTO.setLogin(expectedLogin);
        userBeforeUserDTO.setPassword(expectedPassword);
        userBeforeUserDTO.setRoles(expectedListRoles);
        userBeforeUserDTO.setPosts(expectedListPosts);

        UserDTO expectedUser = new UserDTO();
        expectedUser.setLogin(expectedLogin);
        expectedUser.setRoles(expectedListRoles);
        expectedUser.setPosts(expectedListPosts);

        UserDTO actualUser = userMapper.userToUserDTO(userBeforeUserDTO);

        assertEquals(expectedUser, actualUser);
    }

    @Test
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
    void userToUserCredentialsDTO() {
        int expectedUserId = 1;
        String expectedLogin = "login";
        String expectedPassword = "password";

        ArrayList<Post> expectedListPosts = this.createExpectedListPosts();
        ArrayList<Role> expectedListRoles = this.createExpectedListRoles();

        User userBeforeUserCredentialsDTO = new User();
        userBeforeUserCredentialsDTO.setId(expectedUserId);
        userBeforeUserCredentialsDTO.setLogin(expectedLogin);
        userBeforeUserCredentialsDTO.setPassword(expectedPassword);
        userBeforeUserCredentialsDTO.setRoles(expectedListRoles);
        userBeforeUserCredentialsDTO.setPosts(expectedListPosts);

        UserCredentialsDTO expectedUser = new UserCredentialsDTO();
        expectedUser.setLogin(expectedLogin);
        expectedUser.setPassword(expectedPassword);

        UserCredentialsDTO actualUser = userMapper.userToUserCredentialsDTO(userBeforeUserCredentialsDTO);

        assertEquals(expectedUser, actualUser);
    }

    @Test
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