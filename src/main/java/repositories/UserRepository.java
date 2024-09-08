package repositories;

import entities.User;
import org.example.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    public User create(User user) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users " + "(login, password) values(?,?)");
        ) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();

            return user;
        }
    }

    public User read(String login) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + "users " + "WHERE login = ?");
        ) {
            ResultSet resultSet;
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            String password = null;
            while (resultSet.next()) {
                password = resultSet.getString("password");
            }

            User user = new User(login, password);
            return user;
        }
    }

    public User update(String oldLogin, String newLogin, String newPassword) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET " + "login = ?, password = ? WHERE login = ?");
        ) {
            preparedStatement.setString(1, newLogin);
            preparedStatement.setString(2, newPassword);
            preparedStatement.setString(3, oldLogin);
            preparedStatement.executeUpdate();

            User user = new User(newLogin, newPassword);
            return user;
        }
    }

    public void delete(String login) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("DELETE from users WHERE " + "login = ?")
        ) {
            prepStmt.setString(1, login);
            prepStmt.executeUpdate();
        }

    }
}
