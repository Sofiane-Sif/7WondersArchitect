package com.isep.controllers;

import com.isep.the7WondersArchitect.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label welcomeText;



    @FXML
    public void initialize() {
        Game.startNewGame();
        Game.option.settingUpGame();
    }




    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}