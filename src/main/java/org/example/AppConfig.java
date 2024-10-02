package org.example;

import org.example.mappers.*;
import org.example.repositories.*;
import org.example.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.example")
public class AppConfig {

    @Bean
    public UserService userService() {
        UserRepository userRepository = new UserRepositoryImpl();
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }

    @Bean
    public PostService postService() {
        PostRepository postRepository = new PostRepositoryImpl();
        return new PostServiceImpl(postRepository);
    }

    @Bean
    public PostMapper postMapper() {
        return new PostMapperImpl();
    }

    @Bean
    public RoleService roleService() {
        RoleRepository roleRepository = new RoleRepositoryImpl();
        return new RoleServiceImpl(roleRepository);
    }

    @Bean
    public RoleMapper roleMapper() {
        return new RoleMapperImpl();
    }
}
