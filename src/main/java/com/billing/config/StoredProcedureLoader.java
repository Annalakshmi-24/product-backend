package com.billing.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class StoredProcedureLoader {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void loadProcedure() {
        // SQL to drop the procedure if it already exists
        String dropProcedure = "DROP PROCEDURE IF EXISTS product_getall";

        // SQL to create the procedure
        String createProcedure = """
            CREATE PROCEDURE product_getall()
            BEGIN
                SELECT * FROM product_table WHERE is_active = 1;
            END
            """;

        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {

            // Drop procedure if exists
            stmt.execute(dropProcedure);

            // Create the procedure
            stmt.execute(createProcedure);

            System.out.println("Stored procedure 'product_getall' created successfully.");

        } catch (SQLException e) {
            System.err.println("Error creating stored procedure: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
