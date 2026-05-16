package com.inventory.view;

import com.inventory.controller.ProductController;
import com.inventory.model.Product;
import com.inventory.view.components.ActionButton;
import com.inventory.view.components.AppHeader;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// ProductView handles product management screen

public class ProductView {

    private final ProductController controller = new ProductController();

    public void show(Stage stage) {

        // Header
        VBox header = AppHeader.create(stage, "Manage Products");

        // Search bar
        Label search = new Label("Search Product");
        search.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // https://stackoverflow.com/questions/47559491/making-a-search-bar-in-javafx
        TextField searchField = new TextField();
        searchField.setPromptText("Enter product name or code");
        searchField.setPrefWidth(200);
        Button searchBtn = ActionButton.create("Search");

        HBox searchBox = new HBox(10, search, searchField, searchBtn);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        // Product Table
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

        // Load products from database
        table.getItems().addAll(controller.getAllProducts());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Search button action
        searchBtn.setOnAction(e -> {
            table.getItems().clear();
            String keyword = searchField.getText();
            table.getItems().addAll(
                    controller.searchProduct(keyword)
            );
        });

        // Left section of content that will be search box and table
        VBox leftSection = new VBox(15, searchBox, table);

        // Right section of content that will be the form for product
        Label formTitle = new Label("Product Details");
        formTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #092e53;");

        TextField nameField = new TextField();
        TextField categoryField = new TextField();
        TextField quantityField = new TextField();
        TextField costField = new TextField();
        TextField sellingField = new TextField();
        TextField supplierField = new TextField();
        TextField thresholdField = new TextField();

        // Buttons
        Button addBtn = ActionButton.create("Add");
        Button updateBtn = ActionButton.create("Update");
        Button deleteBtn = ActionButton.create("Delete");
        Button clearBtn = ActionButton.create("Clear");

        // Table click
        table.setOnMouseClicked(e -> {
            Product p = table.getSelectionModel().getSelectedItem();

            if (p != null) {
                nameField.setText(p.getName());
                categoryField.setText(p.getCategory());
                quantityField.setText(String.valueOf(p.getQuantity()));
                costField.setText(String.valueOf(p.getCostPrice()));
                sellingField.setText(String.valueOf(p.getSellingPrice()));
                supplierField.setText(String.valueOf(p.getSupplierId()));
                thresholdField.setText(String.valueOf(p.getThreshold()));
            }
        });

        // Button actions
        // ADD
        addBtn.setOnAction(e -> {
            try {
                Product p = new Product(
                        nameField.getText(),
                        categoryField.getText(),
                        Integer.parseInt(quantityField.getText()),
                        Double.parseDouble(costField.getText()),
                        Double.parseDouble(sellingField.getText()),
                        Integer.parseInt(supplierField.getText()),
                        Integer.parseInt(thresholdField.getText())
                );

                controller.addProduct(p);
                table.getItems().setAll(controller.getAllProducts());

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            }
        });

        //UPDATE
        updateBtn.setOnAction(e -> {
            // Get the selected supplier and store in selectedRow
            Product selectedRow = table.getSelectionModel().getSelectedItem();

            if (selectedRow != null) {
                try {
                    // Takes the admin input from UI and updates the selected supplier details in memory
                    selectedRow.setName(nameField.getText());
                    selectedRow.setCategory(categoryField.getText());
                    selectedRow.setQuantity(Integer.parseInt(quantityField.getText()));
                    selectedRow.setCostPrice(Double.parseDouble(costField.getText()));
                    selectedRow.setSellingPrice(Double.parseDouble(sellingField.getText()));
                    selectedRow.setSupplierId(Integer.parseInt(supplierField.getText()));
                    selectedRow.setThreshold(Integer.parseInt(thresholdField.getText()));

                    // Updates the database - sends the update to controller and then controller updates the record in the database
                    controller.updateProduct(selectedRow);

                    // Updates the tables
                    table.getItems().setAll(controller.getAllProducts());
                    table.getSelectionModel().clearSelection();

                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input");
                }
            }
        });

        // https://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
        // DELETE
        deleteBtn.setOnAction(e -> {
            // Gets the row the admin clicked
            Product selectedRow = table.getSelectionModel().getSelectedItem();

            // Check if a row is selected
            if (selectedRow != null) {
                // Delete from database
                controller.deleteProduct(selectedRow.getProductCode()); // sends the supplier id to delete it from database
                // Updates the table from database again to show the changes
                table.getItems().setAll(controller.getAllProducts());
            }
        });

        // CLEAR
        clearBtn.setOnAction(e -> {
            nameField.clear();
            categoryField.clear();
            quantityField.clear();
            costField.clear();
            sellingField.clear();
            supplierField.clear();
            thresholdField.clear();
        });

        HBox buttonRow1 = new HBox(10, addBtn, updateBtn, deleteBtn);
        HBox buttonRow2 = new HBox(clearBtn);
        VBox buttonBox = new VBox(10, buttonRow1, buttonRow2);

        VBox form = new VBox(10,
                formTitle,
                new HBox(10,new Label("Name"), nameField),
                new HBox(10,new Label("Category"), categoryField),
                new HBox(10,new Label("Quantity"), quantityField),
                new HBox(10,new Label("Cost Price"), costField),
                new HBox(10,new Label("Selling Price"), sellingField),
                new HBox(10,new Label("Supplier ID"), supplierField),
                new HBox(10,new Label("Threshold"), thresholdField),
                buttonBox
        );

        form.setPadding(new Insets(10));
        form.setPrefWidth(300);

        HBox content = new HBox(20, leftSection, form);
        content.setPadding(new Insets(10));

        VBox top = new VBox(header);

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(content);

        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle("Manage Products");
        stage.setScene(scene);
        stage.show();
    }
}