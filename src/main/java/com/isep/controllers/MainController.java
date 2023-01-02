package com.isep.controllers;

import com.isep.MainApplication;
import com.isep.the7WondersArchitect.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends ControlleurBase {




    @FXML
    public void initialize() {
        Game.startNewGame();
    }


/*
* Definition du nombre de joueur et de si on choisit ou pas les civilisations -> fait ici
 */

    @FXML
    protected void startGame() throws IOException {
        System.out.println();

        // Chargement de la nouvelle scene
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ((Stage) stage.getScene().getWindow()).setScene(scene);
    }
}