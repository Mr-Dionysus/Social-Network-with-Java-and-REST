package services;

import entities.User;
import repositories.UserRepository;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) throws SQLException, ClassNotFoundException {
        user = userRepository.create(user);
    }

    @Override
    public User getUserByLogin(String login) throws SQLException, ClassNotFoundException {
        User user = userRepository.read(login);
        return user;
    }
}
