package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("resourses/config.properties");) {
            Properties props = new Properties();
            props.load(fileInputStream);

            String dbUser = props.getProperty("dbUser");
            String dbPassword = props.getProperty("dbPassword");
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306" + "/aston-rest", dbUser, dbPassword);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT dataType " + "FROM data");
            ) {
                int dataType;

                while (resultSet.next()) {
                    dataType = resultSet.getInt("dataType");
                    System.out.println("dataType : " + dataType);
                }

            } catch (NullPointerException e) {
                System.out.println("NullPointerException: " + e.getMessage());
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
