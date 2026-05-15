package com.inventory.controller;

import com.inventory.database.SupplierDAO;
import com.inventory.model.Product;
import com.inventory.model.Supplier;

import java.util.List;

public class SupplierController {
    private final SupplierDAO supplierDAO = new SupplierDAO();

    // Gel all the suppliers
    public List<Supplier> getAllSuppliers() {
        return supplierDAO.getAllSuppliers();
    }

    // Add supplier


    // Update supplier
    // Delete supplier
    // Search supplier

}