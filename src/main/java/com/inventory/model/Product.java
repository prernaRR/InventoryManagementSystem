package com.inventory.model;

// Product represents an item

public class Product {
    private int productCode;
    private String name;
    private String category;
    private int quantity;
    private double costPrice;
    private double sellingPrice;
    private int supplierId;
    private int threshold;

    // Constructor, did not add productId because SQL will auto-generate IDs
    public Product(String name, String category, int quantity, double costPrice, double sellingPrice, int supplierId, int threshold) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.supplierId = supplierId;
        this.threshold = threshold;
    }

    // Getters
    public int getProductCode() {

        return productCode;
    }

    public String getName() {

        return name;
    }

    public String getCategory() {

        return category;
    }

    public int getQuantity() {

        return quantity;
    }

    public double getCostPrice() {

        return costPrice;
    }

    public double getSellingPrice() {

        return sellingPrice;
    }

    public int getSupplierId() {

        return supplierId;
    }

    public int getThreshold() {

        return threshold;
    }

    // Setters
    public void setProductCode(int productCode) {

        this.productCode = productCode;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setCategory(String category) {

        this.category = category;
    }

    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }

    public void setCostPrice(double costPrice) {

        this.costPrice = costPrice;
    }

    public void setSellingPrice(double sellingPrice) {

        this.sellingPrice = sellingPrice;
    }

    public void setSupplierId(int supplierId) {

        this.supplierId = supplierId;
    }

    public void setThreshold(int threshold) {

        this.threshold = threshold;
    }

    // Checks if the product stock is low based on the threshold
    public boolean isLowStock() {
        if (quantity <= threshold) {
            return true;
        }
        return false;
    }

    // Checks if the product is completely out of stock
    public boolean isOutOfStock() {
        if (quantity == 0) {
            return true;
        }
        return false;
    }

    // Returns a string representation
    @Override
    public String toString() {
        return "Product{" + "productCode=" + productCode + ", name=" + name + ", category=" + category + ", quantity=" + quantity + ", Cost Price=" + costPrice + ", Selling Price=" + sellingPrice + ", SupplierId=" + supplierId + ", threshold=" + threshold + '}';
    }

}