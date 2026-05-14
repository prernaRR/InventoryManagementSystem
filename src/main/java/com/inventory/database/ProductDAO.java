package com.inventory.database;

import com.inventory.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Handles all the CRUD operations for Product table
public class ProductDAO {

    // Add product to the database
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

    // Get all products from database
    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("cost_price"),
                        rs.getDouble("selling_price"),
                        rs.getInt("supplier_id"),
                        rs.getInt("threshold")
                );

                // Set the auto-generated ID from the database
                p.setProductCode(rs.getInt("product_code"));

                products.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
        }

        return products;
    }

    // Update product
    public void updateProduct(Product p) {
        String sql = "UPDATE products SET name = ?, category = ?, quantity = ?, cost_price = ?, selling_price = ?, supplier_id = ?, threshold = ? WHERE product_code = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getCategory());
            stmt.setInt(3, p.getQuantity());
            stmt.setDouble(4, p.getCostPrice());
            stmt.setDouble(5, p.getSellingPrice());
            stmt.setInt(6, p.getSupplierId());
            stmt.setInt(7, p.getThreshold());
            stmt.setInt(8, p.getProductCode());

            // Update
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
        }

    }

    // Delete product
    public void deleteProduct(int productCode) {
        String sql = "DELETE FROM products WHERE product_code = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productCode);
            stmt.executeUpdate();
            System.out.println("Product deleted.");

        } catch (Exception e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }

    // Search products by name or product code
    public List<Product> searchProduct(String keyword) {
        String sql = "SELECT * FROM products WHERE name LIKE ? OR product_code = ?";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");

            // Handle for product code search
            try {
                stmt.setInt(2, Integer.parseInt(keyword));
            } catch (NumberFormatException e) {
                stmt.setInt(2, -1); // will never match anything
            }

            var rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("cost_price"),
                        rs.getDouble("selling_price"),
                        rs.getInt("supplier_id"),
                        rs.getInt("threshold")
                );

                p.setProductCode(rs.getInt("product_code"));
                products.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error searching product: " + e.getMessage());
        }
        return products;
    }

    // Adds stock quantity
    public void addStock(int productCode, int amount) {

        String sql = "UPDATE products "
                + "SET quantity = quantity + ? "
                + "WHERE product_code = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, amount);
            stmt.setInt(2, productCode);

            // Update
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reduces stock quantity
    public void reduceStock(int productCode, int amount) {

        String sql = "UPDATE products "
                + "SET quantity = quantity - ? "
                + "WHERE product_code = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, amount);
            stmt.setInt(2, productCode);

            // Update
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}