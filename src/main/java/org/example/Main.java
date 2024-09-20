package org.example;

import org.apache.catalina.startup.Tomcat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception {
//        String jdbcUrl = System.getenv("DATABASE_URL");
//        String username = System.getenv("DATABASE_USER");
//        String password = System.getenv("DATABASE_PASSWORD");
//
//        int attempts = 0;
//        Connection connection = null;
//
//        while (attempts < 10) {
//            try {
//                connection = DriverManager.getConnection(jdbcUrl, username, password);
//                System.out.println("Connected to the database!");
//                createTable(connection);
//                break; // Exit after successful creation of the table
//            } catch (SQLException e) {
//                attempts++;
//                System.out.println("Connection attempt failed: " + e.getMessage());
//                Thread.sleep(5000);
//            }
//        }
//
//        // Start the Tomcat server
//        startTomcat();
    }
//
//    private static void createTable(Connection connection) {
//        try (Statement statement = connection.createStatement()) {
//            String createTableQuery = "CREATE TABLE IF NOT EXISTS example (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50))";
//            statement.execute(createTableQuery);
//            System.out.println("Table created!");
//        } catch (SQLException e) {
//            System.out.println("Error creating table: " + e.getMessage());
//        }
//    }
//
//    private static void startTomcat() throws Exception {
//        Tomcat tomcat = new Tomcat();
//        tomcat.setPort(8080);
//        tomcat.addWebapp("/aston-rest", "src/main/webapp"); // Specify the path to your webapp
//
//        tomcat.start();
//        System.out.println("Tomcat started on http://localhost:8080/aston-rest");
//        tomcat.getServer()
//              .await();
//    }

}
