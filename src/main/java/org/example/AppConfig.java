package org.example;

import org.example.mappers.UserMapper;
import org.example.mappers.UserMapperImpl;
import org.example.repositories.UserRepository;
import org.example.repositories.UserRepositoryImpl;
import org.example.services.UserService;
import org.example.services.UserServiceImpl;
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
}
