package com.inventory.view;

import com.inventory.controller.ProductController;
import com.inventory.model.Product;
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

        VBox header = AppHeader.create(stage, "Manage Products");

        // Search bar
        Label search = new Label("Search Product");
        search.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // https://stackoverflow.com/questions/47559491/making-a-search-bar-in-javafx
        TextField searchField = new TextField();
        searchField.setPromptText("Enter product name or code");
        searchField.setPrefWidth(200);
        Button searchBtn = new Button("Search");
        searchBtn.setStyle("-fx-background-color: #092e53; -fx-text-fill: white;");

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
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

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
        Button addBtn = new Button("Add");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");
        Button clearBtn = new Button("Clear");

        String buttonStyle = "-fx-background-color: #092e53;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;";

        addBtn.setStyle(buttonStyle);
        updateBtn.setStyle(buttonStyle);
        deleteBtn.setStyle(buttonStyle);
        clearBtn.setStyle(buttonStyle);

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