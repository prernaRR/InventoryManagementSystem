package com.inventory.model;

public class Product {
    private int productId;
    private String name;
    private String category;
    private int quantity;
    private double costPrice;
    private double sellingPrice;
    private int supplierId;

    // Constructor, did not add productId because SQL will auto-generate IDs
    public Product(String name, String category, int quantity, double costPrice, double sellingPrice, int supplierId) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.supplierId = supplierId;
    }

    // Getters
    public int getProductId() {
        return productId;
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

    // Setters

    public void setProductId(int productId) {
        this.productId = productId;
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
}
