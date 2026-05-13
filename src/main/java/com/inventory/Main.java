package com.inventory;

import com.inventory.database.DatabaseManager;
import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Main extends Application {
    @Override
    public void start(Stage stage) {

        Button btn = new Button("Hello Inventory");

        StackPane root = new StackPane(btn);

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        DatabaseManager.initDatabase();
        launch(args);
    }
}
