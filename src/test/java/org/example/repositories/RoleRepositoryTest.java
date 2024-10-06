package org.example.repositories;

import org.example.connection.TestConfig;
import org.example.entities.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.accessibility.AccessibleTable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@EnableJpaRepositories(basePackages = "org.example.repositories")
@ActiveProfiles("test")
class RoleRepositoryTest {
    @Container
    public static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0").withDatabaseName("testdb")
                                                                                      .withUsername("user")
                                                                                      .withPassword("password");

    private final RoleRepository roleRepository;

    @Autowired
    public RoleRepositoryTest(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Test
    @DisplayName("Create a Role")
    public void createRole() {
        Role expectedRole = new Role();
        expectedRole.setRoleName("admin");
        expectedRole.setDescription("manage stuff");
        Role actualRole = roleRepository.save(expectedRole);
        assertEquals(expectedRole, actualRole);
    }

    @Test
    @DisplayName("Get a Role by ID")
    public void findById() {
        Role testRole = new Role();
        testRole.setRoleName("admin");
        testRole.setDescription("manage stuff");
        testRole.setUsers(new ArrayList<>());
        Role actualRole = roleRepository.save(testRole);
        Role expectedRole = roleRepository.findById(actualRole.getId())
                                          .get();
        assertEquals(expectedRole.getId(), actualRole.getId());
        assertEquals(expectedRole.getRoleName(), actualRole.getRoleName());
        assertEquals(expectedRole.getDescription(), actualRole.getDescription());
    }

    @Test
    @DisplayName("Get all Roles")
    public void getAllRoles() {
        Role testRole = new Role();
        testRole.setRoleName("admin");
        testRole.setDescription("manage stuff");
        Role actualRole = roleRepository.save(testRole);
        Role expectedRole = roleRepository.findAll()
                                        .get(1);
        assertEquals(expectedRole.getId(), actualRole.getId());
        assertEquals(expectedRole.getRoleName(), actualRole.getRoleName());
        assertEquals(expectedRole.getDescription(), actualRole.getDescription());
    }

    @Test
    @DisplayName("Update a Role by ID")
    public void updateRoleById() {
        Role testRole = new Role();
        testRole.setRoleName("admin");
        testRole.setDescription("manage stuff");
        Role actualRole = roleRepository.save(testRole);
        actualRole.setDescription("test");
        Role expectedRole = roleRepository.save(actualRole);

        assertEquals(expectedRole.getId(), actualRole.getId());
        assertEquals(expectedRole.getRoleName(), actualRole.getRoleName());
        assertEquals(expectedRole.getDescription(), actualRole.getDescription());
    }

    @Test
    @DisplayName("Delete a Role by ID")
    public void deleteRoleById() {
        Role testRole = new Role();
        testRole.setRoleName("admin");
        testRole.setDescription("manage stuff");
        Role actualRole = roleRepository.save(testRole);
        roleRepository.deleteById(actualRole.getId());
        assertTrue(roleRepository.findById(actualRole.getId())
                                 .isEmpty());
    }
}