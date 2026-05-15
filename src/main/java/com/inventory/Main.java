package com.inventory;

import com.inventory.database.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;
import com.inventory.view.HomePage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {

        DatabaseManager.initDatabase();

        new HomePage().show(stage);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
