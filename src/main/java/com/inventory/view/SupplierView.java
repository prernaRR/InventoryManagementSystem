package com.inventory.view;

import com.inventory.controller.SupplierController;
import com.inventory.model.Supplier;
import com.inventory.view.components.ActionButton;
import com.inventory.view.components.AppHeader;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SupplierView {
    private final SupplierController controller = new SupplierController();

    public void show(Stage stage) {
        VBox header = AppHeader.create(stage, "Manage Suppliers");

        // Search bar
        Label search = new Label("Search Supplier");
        search.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // https://stackoverflow.com/questions/47559491/making-a-search-bar-in-javafx
        TextField searchField = new TextField();
        searchField.setPromptText("Enter supplier name or ID");
        searchField.setPrefWidth(200);
        Button searchBtn = ActionButton.create("Search");

        HBox searchBox = new HBox(10, search, searchField, searchBtn);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        // Supplier Table
        TableView<Supplier> table = new TableView<>();

        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableColumn.html

        TableColumn<Supplier, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getSupplierId()).asObject());

        TableColumn<Supplier, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Supplier, String> contactCol = new TableColumn<>("Contact Number");
        contactCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getContactNumber()));

        TableColumn<Supplier, String> emailCol = new TableColumn<>("Email Address");
        emailCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEmail()));

        // Add all columns
        table.getColumns().addAll(idCol, nameCol, contactCol, emailCol);

        // Load supplier from database
        table.getItems().addAll(controller.getAllSuppliers());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Search button action
        searchBtn.setOnAction(e -> {
            table.getItems().clear();
            String keyword = searchField.getText();
            table.getItems().addAll(controller.searchSupplier(keyword));
        });

        // Left section of content that will be search box and table
        VBox leftSection = new VBox(15, searchBox, table);

        // FORM - Right section of content that will be the form for product
        Label formTitle = new Label("Supplier Details");
        formTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #092e53;");

        TextField nameField = new TextField();
        TextField contactField = new TextField();
        TextField emailField = new TextField();

        // Buttons
        Button addBtn = ActionButton.create("Add");
        Button updateBtn = ActionButton.create("Update");
        Button deleteBtn = ActionButton.create("Delete");
        Button clearBtn = ActionButton.create("Clear");

        // Table click
        table.setOnMouseClicked(e -> {
            Supplier s = table.getSelectionModel().getSelectedItem();

            if (s != null) {
                nameField.setText(s.getName());
                contactField.setText(s.getContactNumber());
                emailField.setText(s.getEmail());
            }
        });

        // Button actions
        // ADD
        addBtn.setOnAction(e -> {
            Supplier s = new Supplier(
                    nameField.getText(),
                    contactField.getText(),
                    emailField.getText()
            );
            controller.addSupplier(s);
            table.getItems().setAll(controller.getAllSuppliers());
        });

        //UPDATE
        updateBtn.setOnAction(e -> {
            // Get the selected supplier and store in selectedRow
            Supplier selectedRow = table.getSelectionModel().getSelectedItem();

            if (selectedRow != null) {
                // Takes the admin input from UI and updates the selected supplier details in memory
                selectedRow.setName(nameField.getText());
                selectedRow.setContactNumber(contactField.getText());
                selectedRow.setEmail(emailField.getText());

                // Updates the database - sends the update to controller and then controller updates the record in the database
                controller.updateSupplier(selectedRow);

                // Updates the tables
                table.getItems().setAll(controller.getAllSuppliers());
                table.getSelectionModel().clearSelection();
            }
        });

        // https://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
        // DELETE
        deleteBtn.setOnAction(e -> {
            // Gets the row the admin clicked
            Supplier selectedRow = table.getSelectionModel().getSelectedItem();

            // Check if a row is selected
            if (selectedRow != null) {
                // Delete from database
                controller.deleteSupplier(selectedRow.getSupplierId()); // sends the supplier id to delete it from database
                // Updates the table from database again to show the changes
                table.getItems().setAll(controller.getAllSuppliers());
            }
        });

        // CLEAR
        clearBtn.setOnAction(e -> {
            nameField.clear();
            contactField.clear();
            emailField.clear();
        });

        HBox buttonRow1 = new HBox(10, addBtn, updateBtn, deleteBtn);
        HBox buttonRow2 = new HBox(clearBtn);
        VBox buttonBox = new VBox(10, buttonRow1, buttonRow2);

        VBox form = new VBox(10,
                formTitle,
                new HBox(10,new Label("Name"), nameField),
                new HBox(10,new Label("Contact"), contactField),
                new HBox(10,new Label("Email"), emailField),
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

        stage.setTitle("Manage Suppliers");
        stage.setScene(scene);
        stage.show();
    }
}
