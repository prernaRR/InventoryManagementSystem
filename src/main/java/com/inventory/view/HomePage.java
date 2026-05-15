package com.inventory.view;

import com.inventory.controller.ProductController;
import com.inventory.model.Product;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
// Home Page - Main dashboard of the Inventory Management System

public class HomePage {

    private final ProductController productController = new ProductController();

    public void show(Stage stage) {

        // Header Title
        Label title = new Label("Hardware Store Inventory Management System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Welcome message
        Label welcome = new Label("Welcome Admin!");
        welcome.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Description text
        Label description = new Label("Manage products, monitor stock levels and track inventory efficiently.");
        description.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");

        VBox header = new VBox(5, title, welcome, description);
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #092e53; -fx-padding: 20; -fx-border-color: #061f36; -fx-border-width: 0 0 2 0;");

        // Navigation menu bar (horizontal)
        Button manageProductsBtn = new Button("Manage Products");
        Button supplierBtn = new Button("Manage Suppliers");
        Button updateStockBtn = new Button("Update Stock");
        Button alertBtn = new Button("View Alerts");

        // Button actions
        manageProductsBtn.setOnAction(e -> {
            ProductView productView = new ProductView();
            productView.show(stage);
        });

        /*supplierBtn.setOnAction(e -> {
            SupplierView supplierView = new SupplierView();
            supplierView.show(stage);
        });

        updateStockBtn.setOnAction(e -> {
            UpdateStockView updateStockView = new UpdateStockView();
            updateStockView.show(stage);
        }); */

        alertBtn.setOnAction(e -> {
            AlertView alertView = new AlertView();
            alertView.show(stage);
        });

        String navStyle = "-fx-background-color: #dcdcdc;" +
                "-fx-text-fill: #092e53;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20 10 20;";

        manageProductsBtn.setStyle(navStyle);
        supplierBtn.setStyle(navStyle);
        updateStockBtn.setStyle(navStyle);
        alertBtn.setStyle(navStyle);

        HBox navBar = new HBox(20,
                manageProductsBtn,
                supplierBtn,
                updateStockBtn,
                alertBtn
        );

        navBar.setAlignment(Pos.CENTER);
        navBar.setStyle("-fx-background-color: #092e53; -fx-padding: 15; -fx-border-color: #dcdcdc; -fx-border-width: 0 0 1 0;");

        // Recent products added table
        TableView<Product> table = new TableView<>();

        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableColumn.html
        TableColumn<Product, Integer> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getProductCode()).asObject());

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Product, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getCategory()));

        TableColumn<Product, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());

        TableColumn<Product, Double> costCol = new TableColumn<>("Cost Price");
        costCol.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getCostPrice()).asObject());

        TableColumn<Product, Double> sellCol = new TableColumn<>("Selling Price");
        sellCol.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getSellingPrice()).asObject());

        TableColumn<Product, Integer> supplierCol = new TableColumn<>("Supplier ID");
        supplierCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getSupplierId()).asObject());

        TableColumn<Product, Integer> thresholdCol = new TableColumn<>("Threshold");
        thresholdCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getThreshold()).asObject());

        // Add all columns
        table.getColumns().addAll(
                codeCol,
                nameCol,
                categoryCol,
                qtyCol,
                costCol,
                sellCol,
                supplierCol,
                thresholdCol
        );

        // Load products using controller
        List<Product> allProducts = productController.getAllProducts();

        List<Product> recentProducts = new ArrayList<>();

        int start = Math.max(allProducts.size() - 5, 0);

        for (int i = start; i < allProducts.size(); i++) {
            recentProducts.add(allProducts.get(i));
        }

        table.getItems().addAll(recentProducts);

        // Content of home page
        Label contentTitle = new Label("Recently Added Products");
        contentTitle.setStyle("-fx-font-size: 18px; -fx-text-fill: #092e53; -fx-font-weight: bold;");

        VBox content = new VBox(15, contentTitle, table);
        content.setStyle("-fx-background-color: white; -fx-padding: 20;");
        content.setAlignment(Pos.CENTER);


        VBox topContainer = new VBox(header, navBar);

        BorderPane root = new BorderPane();
        root.setTop(topContainer);
        root.setCenter(content);

        Scene scene = new Scene(root, 900, 550);

        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
}