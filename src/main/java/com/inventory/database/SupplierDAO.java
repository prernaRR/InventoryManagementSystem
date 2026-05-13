package com.inventory.database;

import com.inventory.model.Supplier;
import java.sql.*;

// Handles all the CRUD operation for Supplier table
public class SupplierDAO {
    // Add a supplier to database
    public void addSupplier(Supplier s) {
        String sql = "INSERT INTO suppliers" + "(name, contact_number, email)"
                + "VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getName());
            stmt.setString(2, s.getContactNumber());
            stmt.setString(3, s.getEmail());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }
}
