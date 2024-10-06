package org.example.repositories;

import org.example.config.TestConfig;
import org.example.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@EnableJpaRepositories(basePackages = "org.example.repositories")
@ActiveProfiles("test")
class UserRepositoryTest {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("Create a User")
    public void createUser() {
        String login = "root";
        String password = "password";
        User user = new User(login, password);
        User createdUser = userRepository.save(user);
        assertEquals(user, createdUser);
    }

    @Test
    @DisplayName("Get a User by ID")
    public void getUserById() {
        int userId = 1;
        String login = "root";
        String password = "password";
        User user = new User(userId, login, password);
        userRepository.save(user);
        User foundUser = userRepository.findById(userId).get();

        assertEquals(user, foundUser);
    }

    @Test
    @DisplayName("Update a User by ID")
    public void updateUserById() {
        User testUser = new User();
        testUser.setLogin("root");
        testUser.setPassword("password");
        User actualUser = userRepository.save(testUser);
        actualUser.setPassword("p@ssw@rd");
        User expectedUser = userRepository.save(actualUser);

        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getLogin(), actualUser.getLogin());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }

    @Test
    @DisplayName("Delete a User by ID")
    public void deleteUserById() {
        User testUser = new User();
        testUser.setLogin("root");
        testUser.setPassword("password");
        User actualUser = userRepository.save(testUser);
        userRepository.deleteById(actualUser.getId());
        assertTrue(userRepository.findById(actualUser.getId())
                                 .isEmpty());
    }
}