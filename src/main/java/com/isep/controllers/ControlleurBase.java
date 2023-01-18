//***** II.1102 – Algorithmique et Programmation - Projet : Mini RPG Lite 3000 *****
// ISEP - A1 - G7C
// Auteur : Charles_Mailley
// Date de rendu  : 17/12/2022

package com.isep.controllers;

import com.isep.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class ControlleurBase {

    @FXML
    protected AnchorPane stageAP, screenElements;

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
            Stage window = ((Stage) stageAP.getScene().getWindow());
            window.setFullScreen(true);
            window.setResizable(false);
            window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            // Aggrandi l'image au max
            background.setFitWidth(stageAP.getWidth());
            background.setFitHeight(stageAP.getHeight());
            // Remet les elements au centre
            double midScreenX = stageAP.getWidth()/2;
            double midScreenY = stageAP.getHeight()/2;
            int AnchorPointX = (int) (midScreenX - screenElements.getWidth()/2);
            int AnchorPointY = (int) (midScreenY - screenElements.getHeight()/2);
            screenElements.setLayoutX(AnchorPointX);
            screenElements.setLayoutY(AnchorPointY);
            // Zoom sur screenElements
            this.screenElements.setScaleX(1.3);
            this.screenElements.setScaleY(1.3);

            /*
             * this.screenElements.setScaleX(1.5);
             * this.screenElements.setScaleY(1.5);
             * C'est deux lignes permettent de zoomer l'AnchorPane qui contient tous les elements
             * C'est pour l'instant zoomé à 1.5 - une valeur fixe...
             * Ca doit pas donner la meme chose en fonction du type d'ecran
             * Faire une fonction qui recup la taille de l'ecran et renvoi une valeur de zoom
             */
        }
    }

    protected void loadPage(String pageName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/"+ pageName +"-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ((Stage) stageAP.getScene().getWindow()).setScene(scene);
        }
        catch (IOException e) {throw new RuntimeException(e);}

    }
    public static Image setAnImage(String path) {
        try {return new Image(Objects.requireNonNull(MainApplication.class.getResource(path)).openStream());}
        catch (IOException | NullPointerException | IllegalArgumentException ignored) {
            System.out.println("Erreur d'affichage : " + path);
            return ControlleurBase.setAnImage("images/background/qrCode_rules.PNG");
        }
    }


}
