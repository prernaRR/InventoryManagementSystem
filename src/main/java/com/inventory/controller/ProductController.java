package com.inventory.controller;

import com.inventory.database.ProductDAO;
import com.inventory.model.Product;

import java.util.List;

// Handles stock rules, alerts and logic for product

public class ProductController {

    private ProductDAO productDAO;

    // Constructor
    public ProductController() {
        this.productDAO = new ProductDAO();
    }

    // Add product
    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }

    // Update product
    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
    }

    // Delete product
    public void deleteProduct(int productCode) {
        productDAO.deleteProduct(productCode);
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public List<Product> searchProduct(String keyword) {
        return productDAO.searchProduct(keyword);
    }

    // Manage Stock
    public void addStock(int productCode, int amount) {
        if (amount > 0) {
            productDAO.addStock(productCode, amount);
        } else {
            System.out.println("Invalid amount of stock");
            return;
        }
    }

    public void reduceStock(Product product, int amount) {

        // Prevents negative values
        if (amount <= 0) {
            System.out.println("Invalid amount");
            return;
        }

        // Prevents stock from going below zero
        if (amount > product.getQuantity()) {
            System.out.println("Not enough stock available");
            return;
        }

        productDAO.reduceStock(product.getProductCode(), amount);
    }

    // Alert
    public boolean isLowStock(Product product) {
        if (product.getQuantity() <= product.getThreshold()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOutOfStock(Product product) {
        if (product.getQuantity() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
