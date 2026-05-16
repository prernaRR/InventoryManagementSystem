package com.inventory.view.components;

import com.inventory.view.HomePage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppHeader {

    public static VBox create(Stage stage, String pageTitleText) {
        // Header
        Label title = new Label("Inventory Management System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Back button
        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #dcdcdc; -fx-text-fill: #092e53; -fx-font-weight: bold;");

        // Back action
        backBtn.setOnAction(e -> {
            new HomePage().show(stage);
        });

        // Page Title
        Label pageTitle = new Label(pageTitleText);
        pageTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Layout
        HBox leftBox = new HBox(backBtn);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        HBox centerBox = new HBox(pageTitle);
        centerBox.setAlignment(Pos.CENTER);

        // https://stackoverflow.com/questions/12118681/how-can-i-create-resizing-spacers-in-javafx
        // Spacer
        Region spacerLeft = new Region();
        Region spacerRight = new Region();

        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        HBox titleBar = new HBox(10, leftBox, spacerLeft, centerBox, spacerRight);

        VBox header = new VBox(5, title, titleBar);
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #092e53; -fx-padding: 20; -fx-border-color: #061f36; -fx-border-width: 0 0 2 0;");

        return header;
    }
}
