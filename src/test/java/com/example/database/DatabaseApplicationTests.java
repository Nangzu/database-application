package com.example.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static oracle.sql.DatumWithConnection.assertNotNull;

@SpringBootTest
class DatabaseApplicationTests {
    @Autowired
    private DataSource dataSource;
    @Test
    void contextLoads() {
        try (Connection conn = dataSource.getConnection()) {
            assertNotNull(conn);
            System.out.println("Oracle Database connected successfully.");
        } catch (SQLException e) {
            fail("Failed to connect to Oracle Database.");
        }
    }

}
