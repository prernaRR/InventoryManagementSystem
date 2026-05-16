package com.inventory.view.components;

import javafx.scene.control.Button;

public class ActionButton {

    private static final String BTNSTYLE =
            "-fx-background-color: #092e53;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;";
    // Reusable Button
    public static Button create(String text) {
        Button btn = new Button(text);
        btn.setStyle(BTNSTYLE);
        return btn;
    }
}