package com.climatemonitoring.climatemonitoring;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class climateMonitoringAppController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}