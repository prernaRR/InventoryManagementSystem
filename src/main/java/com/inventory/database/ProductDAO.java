package com.inventory.database;

import com.inventory.model.Product;
import java.sql.*;

// Handles all the CRUD operations for Product table
public class ProductDAO {

    // Add a product to the database
    public void addProduct(Product p) {
        String sql = "INSERT INTO products" + "(name, category, quantity, cost_price, selling_price, supplier_id, threshold)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getName());
            stmt.setString(2, p.getCategory());
            stmt.setInt(3, p.getQuantity());
            stmt.setDouble(4, p.getCostPrice());
            stmt.setDouble(5, p.getSellingPrice());
            stmt.setInt(6, p.getSupplierId());
            stmt.setInt(7, p.getThreshold());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }
}
