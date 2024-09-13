package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        initializeDataSource("jdbc:mysql://localhost:3306/aston-rest", "C:/Denis/Projects/Java/Aston_REST/src/main/resources/config.properties");
    }

    private static void initializeDataSource(String jdbcUrl, String propertiesPath) {
        String dbUser;
        String dbPassword;

        try (FileInputStream fileInputStream = new FileInputStream(propertiesPath)) {
            Properties props = new Properties();
            props.load(fileInputStream);

            dbUser = props.getProperty("dbUser");
            dbPassword = props.getProperty("dbPassword");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File with login and password isn't found", e);
        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        }

        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setUsername(dbUser);
        config.setPassword(dbPassword);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public DataSource(String jdbcURL, String username, String password) {
        config = new HikariConfig();
        config.setJdbcUrl(jdbcURL);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public DataSource() {
    }

    public Connection connect() throws SQLException {
        return ds.getConnection();
    }

    public static void setTestConfiguration(String jdbcUrl, String username, String password) {
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        ds = new HikariDataSource(config);
    }
}
