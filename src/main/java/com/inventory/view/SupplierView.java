package com.inventory.view;

import com.inventory.controller.SupplierController;
import com.inventory.model.Supplier;
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
        Button searchBtn = new Button("Search");
        searchBtn.setStyle("-fx-background-color: #092e53; -fx-text-fill: white;");

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
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

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

        stage.setTitle("Manage Products");
        stage.setScene(scene);
        stage.show();
    }
}
