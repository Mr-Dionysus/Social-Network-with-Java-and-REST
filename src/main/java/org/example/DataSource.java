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
    private static final HikariConfig CONFIG = new HikariConfig();
    private static final HikariDataSource DS;

    static {
        String dbUser = null;
        String dbPassword = null;

        try (FileInputStream fileInputStream = new FileInputStream("C:/Denis/Projects/Java" + "/Aston_REST/src/main/resources/config.properties");) {
            Properties props = new Properties();
            props.load(fileInputStream);

            dbUser = props.getProperty("dbUser");
            dbPassword = props.getProperty("dbPassword");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CONFIG.setJdbcUrl("jdbc:mysql://localhost:3306/aston-rest");
        CONFIG.setDriverClassName("com.mysql.cj.jdbc.Driver");
        CONFIG.setUsername(dbUser);
        CONFIG.setPassword(dbPassword);
        CONFIG.addDataSourceProperty("cachePrepStmts", "true");
        CONFIG.addDataSourceProperty("prepStmtCacheSize", "250");
        CONFIG.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        DS = new HikariDataSource(CONFIG);
    }

    private DataSource() {
    }

    public static Connection connect() throws SQLException {
        return DS.getConnection();
    }
}
