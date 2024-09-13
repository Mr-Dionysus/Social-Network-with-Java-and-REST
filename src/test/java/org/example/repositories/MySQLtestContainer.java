package org.example.repositories;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MySQLtestContainer {
    private static final MySQLContainer<?> SELF_MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8.0");

    @BeforeAll
    static void setUp() {
        SELF_MY_SQL_CONTAINER.start();
    }

    @AfterAll
    static void tearDown() {
        SELF_MY_SQL_CONTAINER.stop();
    }

    @Test
    void connection() throws Exception {
        Connection connection = DriverManager.getConnection(SELF_MY_SQL_CONTAINER.getJdbcUrl(), SELF_MY_SQL_CONTAINER.getUsername(), SELF_MY_SQL_CONTAINER.getPassword());
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT 1");

        assertTrue(rs.next());
        assertEquals(1, rs.getInt(1));

        connection.close();
    }
}

