package com.isep.controllers;

import com.isep.MainApplication;
import com.isep.the7WondersArchitect.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerCreationController extends ControlleurBase {





    @FXML
    protected void nextPlayer() throws IOException {
        System.out.println();

        // Chargement de la nouvelle scene
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ((Stage) stage.getScene().getWindow()).setScene(scene);
    }



    /*
    * Fonction pour incrementer le spinner pour l'age
    * Annimation :
        * au passage de la sourie : aggrandir l'image
        * retour taille normal quand la sourie part
        * au clic pour la selection : board de l'image en couleur
    * Fonction valider si wonder-name-age choisi pour soit :
        * creer le joueur et rafrairir la page pour creer le prochain joueur
        * commencer la partie
    *
     */
}