package repositories;

import entities.User;
import org.example.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    public User create(User user) throws SQLException, ClassNotFoundException {
        try (Connection connection = DBConnection.initializeDB();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO data " + "(login, password) values(?,?)");
        ) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();

            return user;
        }
    }

    public User read(String login) throws SQLException, ClassNotFoundException {
        try (Connection connection = DBConnection.initializeDB();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " +
                     "data " + "WHERE login = ?");
        ) {
            ResultSet resultSet;
            preparedStatement.setString(1, "13");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("password"));
            }
            return null;
        }
    }
    //
    //    public void update(User user) {
    //
    //    }
    //
    //    public void delete(User user) {
    //
    //    }
}
