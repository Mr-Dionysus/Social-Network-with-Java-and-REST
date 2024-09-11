package org.example.repositories;

import org.example.entities.User;
import org.example.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    public User create(String login, String password) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users " + "(login, password) values(?,?)");
        ) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            int id = -1;

            try (PreparedStatement prepStmt = connection.prepareStatement("SELECT id FROM users " + "WHERE login = ?");) {
                prepStmt.setString(1, login);
                ResultSet resultSet = prepStmt.executeQuery();

                if (resultSet.next()) {
                    id = resultSet.getInt("id");
                }

                resultSet.close();
            }

            User user = new User(id, login, password);
            return user;
        }
    }

    public User read(int id) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + "users " + "WHERE id = ?");
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            String login = null;
            String password = null;

            while (resultSet.next()) {
                login = resultSet.getString("login");
                password = resultSet.getString("password");
            }

            User user = new User(id, login, password);
            return user;
        }
    }

    public User update(int id, String newLogin, String newPassword) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt = connection.prepareStatement("UPDATE users SET " + "login = ?, password = ? WHERE id = ?");
        ) {
            prepStmt.setString(1, newLogin);
            prepStmt.setString(2, newPassword);
            prepStmt.setInt(3, id);
            prepStmt.executeUpdate();

            User user = new User(id, newLogin, newPassword);
            return user;
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection connection = DataSource.connect();
             PreparedStatement prepStmt =
                     connection.prepareStatement("DELETE from users WHERE " + "id = ?")
        ) {
            prepStmt.setInt(1, id);
            prepStmt.executeUpdate();
        }
    }
}
