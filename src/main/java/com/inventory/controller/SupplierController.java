package com.inventory.controller;

import com.inventory.database.ProductDAO;
import com.inventory.database.SupplierDAO;
import com.inventory.model.Product;
import com.inventory.model.Supplier;

import java.util.List;

public class SupplierController {
    private final SupplierDAO supplierDAO = new SupplierDAO();

    // Add supplier
    public void addSupplier(Supplier s) {
        supplierDAO.addSupplier(s);
    }

    // Update supplier
    public void updateSupplier(Supplier s) {
        supplierDAO.updateSupplier(s);
    }

    // Delete supplier
    public void deleteSupplier(int id) {
        supplierDAO.deleteSupplier(id);
    }

    // Gel all the suppliers
    public List<Supplier> getAllSuppliers() {
        return supplierDAO.getAllSuppliers();
    }

    // Search supplier
    public List<Supplier> searchSupplier(String keyword) {
        return supplierDAO.searchSupplier(keyword);
    }
}