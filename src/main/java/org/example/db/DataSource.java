package org.example.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private void initializeDataSource(String propertiesPath) {
        String dbUser;
        String dbPassword;
        String jdbcUrl;
        System.out.println(propertiesPath);

        try (InputStream inputStream = DataSource.class.getClassLoader()
                                                       .getResourceAsStream(propertiesPath);
        ) {
            Properties props = new Properties();
            props.load(inputStream);
            dbUser = props.getProperty("dbUser");
            dbPassword = props.getProperty("dbPassword");
            jdbcUrl = props.getProperty("jdbcUrl");
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
        config.setJdbcUrl(jdbcURL);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public DataSource() {
        String propertiesPath = "/config.properties";
        initializeDataSource(propertiesPath);
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
