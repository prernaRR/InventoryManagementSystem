package com.inventory.view;

import com.inventory.controller.ProductController;
import com.inventory.model.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class UpdateStockView {
    private final ProductController controller = new ProductController();

    public void show(Stage stage) {
        VBox header = AppHeader.create(stage, "Update Stock");

        // Form
        Label formTitle = new Label("Update Stock");
        formTitle.setStyle("-fx-font-size: 18px; -fx-text-fill: #092e53; -fx-font-weight: bold");

        /*.
        * .
        * .
        * .*/

        Button updateBtn = new Button("Update Quantity");
        updateBtn.setStyle("-fx-background-color: #092e53; -fx-text-fill: white; -fx-font-weight: bold;");

        Button clearBtn = new Button("Clear");
        clearBtn.setStyle("-fx-background-color: #092e53; -fx-text-fill: white; -fx-font-weight: bold;");



        content.setPadding(new Insets(20));

        VBox root = new VBox(header, content);

        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Update Stock");
        stage.setScene(scene);
        stage.show();
    }
}
