package org.example.db;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DataSourceTest {
    private DataSource dataSource = new DataSource();
    private static MySQLContainer<?> mySQLcontainer;

    @Test
    void connect() throws SQLException {
        try (Connection expectedConnection = dataSource.connect()) {
            assertNotNull(expectedConnection);
        }
    }

    @Test
    void setTestConfiguration() throws SQLException {
        mySQLcontainer = new MySQLContainer<>("mysql:8.0");
        mySQLcontainer.start();
        dataSource = new DataSource(mySQLcontainer.getJdbcUrl(), mySQLcontainer.getUsername(), mySQLcontainer.getPassword());

        try (Connection expectedConnection = dataSource.connect()) {
            mySQLcontainer.stop();
            assertNotNull(expectedConnection);
        }
    }
}