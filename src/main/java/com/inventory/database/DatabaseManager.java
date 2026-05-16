package com.inventory.database;

// https://www.google.com/search?q=how+to+include+SQLite+database+in+a+project+in+java+in+intelliJ+IDE&sca_esv=b2a63b90023cef6b&aep=10&sxsrf=ANbL-n44fOUriLdsXcmDknJIyTJXjs6EhQ:1777959417486&udm=7&fbs=ADc_l-aN0CWEZBOHjofHoaMMDiKpmAsnXCN5UBx17opt8eaTX5MJRoosnbembaWTjeNSquJHJg9nAqGuX2kHk_4M6lEg_7L2zWTpcB4B0dkfav5-j96htHTGs2M3Bx1YnVvQeJtODuHvQ-b9wnnaV5mGWtIKueBwIRgBcKPyU8LgTntNaXFiNXT9MPpQE_pPEvRR4c0YcG8FGHVef4YQINCDFtBQLlDFqw&sa=X&ved=2ahUKEwi1joT8tqGUAxVdX2wGHRGbDa0QtKgLegQIBBAK#fpstate=ive&vld=cid:8f559ee8,vid:jM4KnPiedK0,st:0
// https://www.sqlitetutorial.net/sqlite-java/create-table/

// Handles the connection to SQLite database

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    // SQLite database file name
    private static final String URL = "jdbc:sqlite:inventory.db";

    // Opens connection to database
    public static Connection connect() {
        try {
            // Connect database using JDBC
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Database connection failed, " + e.getMessage());

            return null;
        }
    }

    // Creates all tables
    public static void initDatabase() {

        // Creates supplier table
        String createSuppliers =
                "CREATE TABLE IF NOT EXISTS suppliers ("
                + "supplier_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "contact_number TEXT,"
                + "email TEXT"
                + ");";

        // Creates product table
        String createProducts =
                "CREATE TABLE IF NOT EXISTS products ("
                + "product_code INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "category TEXT,"
                + "quantity INTEGER DEFAULT 0,"
                + "cost_price REAL,"
                + "selling_price REAL,"
                + "supplier_id INTEGER,"
                + "threshold INTEGER DEFAULT 5,"
                + "FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id)"
                + ");";

        // Open database connection
        Connection conn = connect();
        // Check if connection failed
        if (conn == null) {
            System.out.println("Connection failed.");
            return;
        }

        // Create statement to execute SQL queries
        try(Statement stmt = conn.createStatement()) {
            // Create new tables
            stmt.execute(createSuppliers);
            stmt.execute(createProducts);

            System.out.println("Database initialized successfully.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}