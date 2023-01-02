//***** II.1102 – Algorithmique et Programmation - Projet : Mini RPG Lite 3000 *****
// ISEP - A1 - G7C
// Auteur : Charles_Mailley
// Date de rendu  : 17/12/2022

package com.isep.controllers;

import com.isep.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ControlleurBase {

    @FXML
    protected AnchorPane stage, screenElements;

    @FXML
    protected ImageView background;

    protected boolean isInit = false;

    @FXML
    protected void autoResize() {
        // On centre tous les elements du windows
        if (!this.isInit) {
            // Seulement à la création à chaque changement de scene
            this.isInit = true;
            // Full screen
            Stage window = ((Stage) stage.getScene().getWindow());
            window.setFullScreen(true);
            window.setResizable(false);
            window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            // Aggrandi l'image au max
            background.setFitWidth(stage.getWidth());
            background.setFitHeight(stage.getHeight());
            // Remet les elements au centre
            double midScreenX = stage.getWidth()/2;
            double midScreenY = stage.getHeight()/2;
            int AnchorPointX = (int) (midScreenX - screenElements.getWidth()/2);
            int AnchorPointY = (int) (midScreenY - screenElements.getHeight()/2);
            screenElements.setLayoutX(AnchorPointX);
            screenElements.setLayoutY(AnchorPointY);

            /*
             * On aggrandi le background et on place le screenElements au centre
             * Mais on prend pas toute la place de l'ecran
             * Il faudrai replacer chaque element de screenElements à un %
             */
        }
    }


    public static Image setAnImage(String path) {
        // Attention mauvais path pour : token-education -> il faut bien renommer le nom de l'image correctement
        try {return new Image(Objects.requireNonNull(MainApplication.class.getResource(path)).openStream());}
        catch (IOException e) {throw new RuntimeException(e);}
    }


}
