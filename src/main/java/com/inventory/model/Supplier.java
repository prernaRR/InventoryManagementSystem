package com.inventory.model;

// Supplier represents a company or a person that supplies products to the hardware store
// This class is used to store and manage supplier information

public class Supplier {
    private int supplierId;
    private String name;
    private String contactNumber;
    private String email;

    // Constructor - supplierId not included because SQLite auto-generates it
    public Supplier(String name, String contactNumber, String email) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    // Getters
    public int getSupplierId() {
        return supplierId;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    //Setters

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Returns a string representation
    @Override
    public  String toString() {
        return "Supplier{" + "supplierId=" + supplierId + ", name=" + name + ", contactNumber=" + contactNumber + ", email=" + email + '}';
    }
}