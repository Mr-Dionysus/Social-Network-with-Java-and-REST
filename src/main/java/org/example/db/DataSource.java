package org.example.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private void initializeDataSource(String propertiesPath) {
        try (InputStream inputStream = DataSource.class.getClassLoader()
                                                       .getResourceAsStream(propertiesPath)
        ) {
            Properties props = new Properties();
            props.load(inputStream);

            String dbUser = props.getProperty("dbUser");
            String dbPassword = props.getProperty("dbPassword");
            String jdbcUrl = props.getProperty("jdbcUrl");
            String driverClassName = props.getProperty("driverClassName");

            String cachePrepStmtsName = props.getProperty("cachePrepStmtsName");
            String cachePrepStmtsValue = props.getProperty("cachePrepStmtsValue");
            String[] cachePrepStmts = new String[]{cachePrepStmtsName, cachePrepStmtsValue};

            String prepStmtCacheSizeName = props.getProperty("prepStmtCacheSizeName");
            String prepStmtCacheSizeValue = props.getProperty("prepStmtCacheSizeValue");
            String[] prepStmtCacheSize = new String[]{prepStmtCacheSizeName, prepStmtCacheSizeValue};

            String prepStmtCacheSqlLimitName = props.getProperty("prepStmtCacheSqlLimitName");
            String prepStmtCacheSqlLimitValue = props.getProperty("prepStmtCacheSqlLimitValue");
            String[] prepStmtCacheSqlLimit = new String[]{prepStmtCacheSqlLimitName, prepStmtCacheSqlLimitValue};

            config.setJdbcUrl(jdbcUrl);
            config.setDriverClassName(driverClassName);
            config.setUsername(dbUser);
            config.setPassword(dbPassword);
            config.addDataSourceProperty(cachePrepStmts[0], cachePrepStmts[1]);
            config.addDataSourceProperty(prepStmtCacheSize[0], prepStmtCacheSize[1]);
            config.addDataSourceProperty(prepStmtCacheSqlLimit[0], prepStmtCacheSqlLimit[1]);
            ds = new HikariDataSource(config);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File with login and password isn't found", e);
        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        }
    }

    public DataSource(String jdbcURL, String username, String password) {
        config.setJdbcUrl(jdbcURL);
        config.setUsername(username);
        config.setPassword(password);
        ds = new HikariDataSource(config);
    }

    public DataSource() {
        String propertiesPath = "/config.properties";
        initializeDataSource(propertiesPath);
    }

    public Connection connect() throws SQLException {
        return ds.getConnection();
    }
}
