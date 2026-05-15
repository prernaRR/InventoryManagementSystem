package com.inventory.view;

import com.inventory.controller.ProductController;
import com.inventory.model.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class AlertView {
    private final ProductController controller = new ProductController();

    public void show(Stage stage) {

        VBox header = AppHeader.create(stage, "Stock Alerts");

        // Get products
        List<Product> products = controller.getAllProducts();

        VBox alertBox = new VBox(10);
        alertBox.setPadding(new Insets(15));

        int lowStockCount = 0;
        int outOfStockCount = 0;

        for (Product p : products) {
            // Out of stock
            if (p.getQuantity() == 0) {
                outOfStockCount++;
                Label alert = new Label("OUT OF STOCK: " + p.getName() + "(Code: " + p.getProductCode() + ")");

                alert.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
                alertBox.getChildren().add(alert);
            }

            // Low stock
            else if (p.getQuantity() <= p.getThreshold()) {
                lowStockCount++;
                Label alert = new Label("LOW STOCK: " + p.getName() + "(Qty: " + p.getQuantity() + ", Threshold: " + p.getThreshold() + ")");

                alert.setStyle("-fx-text-fill: orange; -fx-font-weight: bold");
                alertBox.getChildren().add(alert);
            }
        }

        Label report = new Label(
                "Low Stock: " + lowStockCount + " | Out of Stock: " + outOfStockCount);

        report.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox content = new VBox(15, report, alertBox);
        content.setPadding(new Insets(20));

        VBox root = new VBox(header, content);

        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("View Alerts");
        stage.setScene(scene);
        stage.show();
    }
}
