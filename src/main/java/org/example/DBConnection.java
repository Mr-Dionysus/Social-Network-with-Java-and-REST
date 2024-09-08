package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnection {
    public static Connection initializeDB() throws SQLException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream("C:/Denis/Projects/Java" + "/Aston_REST/src/main/resources/config.properties");) {
            Properties props = new Properties();
            props.load(fileInputStream);

            String dbUser = props.getProperty("dbUser");
            String dbPassword = props.getProperty("dbPassword");
            String dbDriver = "com.mysql.cj.jdbc.Driver";
            Class.forName(dbDriver);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306" + "/aston-rest", dbUser, dbPassword);

            return connection;
        } catch (IOException e) {
            throw new RuntimeException(e + "\nCan't read a file");
        } catch (SQLException e) {
            throw new RuntimeException(e + "\nCan't connect to SQL");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e + "\nCan't load Driver");
        }
    }
}
