package com.inventory.database;

import com.inventory.model.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    // Get all suppliers from database
    public List<Supplier> getAllSuppliers() {

        List<Supplier> suppliers = new ArrayList<>();

        String sql = "SELECT * FROM suppliers";

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Supplier s = new Supplier(
                        rs.getString("name"),
                        rs.getString("contact_number"),
                        rs.getString("email")
                );

                // Set the auto-generated ID from the database
                s.setSupplierId(rs.getInt("supplier_id"));
                suppliers.add(s);
            }

        } catch (Exception e) {
            System.out.println("Error displaying suppliers: " + e.getMessage());
        }
        return suppliers;
    }

    // Update supplier
    public void updateSupplier(Supplier s) {
        String sql = "UPDATE suppliers SET name=?, contact_number=?, email=? WHERE supplier_id =?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters
            stmt.setString(1, s.getName());
            stmt.setString(2, s.getContactNumber());
            stmt.setString(3, s.getEmail());
            stmt.setInt(4, s.getSupplierId());

            // Update
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error updating supplier: " + e.getMessage());
        }
    }

    // Delete supplier
    public void deleteSupplier(int id) {
        String sql = "DELETE FROM suppliers WHERE supplier_id=?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Supplier deleted.");

        } catch (Exception e) {
            System.out.println("Error deleting supplier: " + e.getMessage());
        }
    }

    // Search supplier by name or supplier id
    public List<Supplier> searchSupplier(String keyword) {
        List<Supplier> suppliers = new ArrayList<>();

        String sql = "SELECT * FROM suppliers WHERE name LIKE ? OR supplier_id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");

            // Handle for supplier id search
            try {
                stmt.setInt(2, Integer.parseInt(keyword));
            } catch (NumberFormatException e) {
                stmt.setInt(2, -1); // will never match anything
            }

            var rs = stmt.executeQuery();

            while (rs.next()) {
                Supplier s = new Supplier(
                        rs.getString("name"),
                        rs.getString("contact_number"),
                        rs.getString("email")
                );

                s.setSupplierId(rs.getInt("supplier_id"));
                suppliers.add(s);
            }

        } catch (Exception e) {
            System.out.println("Error searching supplier: " + e.getMessage());
        }
        return suppliers;
    }
}