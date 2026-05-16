package com.inventory.view;

import com.inventory.controller.ProductController;
import com.inventory.model.Product;
import com.inventory.view.components.ActionButton;
import com.inventory.view.components.AppHeader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class UpdateStockView {
    private final ProductController controller = new ProductController();

    public void show(Stage stage) {
        // Header
        VBox header = AppHeader.create(stage, "Update Stock");

        // Product List
        Label selectLabel = new Label("Select Product");
        selectLabel.setStyle("-fx-font-weight: bold;");

        // https://www.geeksforgeeks.org/java/javafx-combobox-with-examples/
        ComboBox<Product> productBox = new ComboBox<>();
        productBox.getItems().addAll(controller.getAllProducts());

        productBox.setPrefWidth(300);

        // Display product in dropdown
        // https://webtechie.be/post/2025-04-09-javafx-combobox-with-objects/
        productBox.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getProductCode() + " - " + item.getName());
                }
            }
        });


        // Fields
        //Label productLabel = new Label("Product Code");
        //TextField productCodeField = new TextField();
        //productCodeField.setPromptText("Enter product code");

        Label qtyLabel = new Label("Amount to Add/Reduce");
        TextField qtyField = new TextField();
        qtyField.setPromptText("Enter quantity");

        // Buttons
        Button addStockBtn = ActionButton.create("Add Stock");
        Button reduceStockBtn = ActionButton.create("Reduce Stock");
        Button clearBtn = ActionButton.create("Clear");

        // Button Action

        // Add stock
        addStockBtn.setOnAction(e -> {
            Product selectedRow = productBox.getValue();

            if (selectedRow != null) {
                try {
                    int qty = Integer.parseInt(qtyField.getText());

                    controller.addStock(selectedRow.getProductCode(), qty);

                    productBox.getItems().setAll(controller.getAllProducts());
                    qtyField.clear();

                } catch (NumberFormatException ex) {
                    System.out.println("Invalid quantity");
                }
            }
        });

        // Reduce Stock
        reduceStockBtn.setOnAction(e -> {
            Product selectedRow = productBox.getValue();

            if (selectedRow != null) {
                try {
                    int qty = Integer.parseInt(qtyField.getText());

                    controller.reduceStock(selectedRow, qty);

                    productBox.getItems().setAll(controller.getAllProducts());
                    qtyField.clear();

                } catch (NumberFormatException ex) {
                    System.out.println("Invalid quantity");
                }
            }
        });

        // Clear button
        clearBtn.setOnAction(e -> {
            productBox.getSelectionModel().clearSelection();
            qtyField.clear();
        });

        VBox form = new VBox(15,
                selectLabel,
                productBox,
                qtyLabel,
                qtyField,
                new HBox(10, addStockBtn, reduceStockBtn),
                clearBtn
        );

        form.setAlignment(Pos.CENTER_LEFT);

        VBox content = new VBox(20, form);
        content.setPadding(new Insets(20));

        VBox root = new VBox(header, content);

        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Update Stock");
        stage.setScene(scene);
        stage.show();
    }
}