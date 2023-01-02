package com.isep.controllers;

import com.isep.MainApplication;
import com.isep.the7WondersArchitect.Game;
import com.isep.the7WondersArchitect.Player;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class PlayerCreationController extends ControlleurBase {


    @FXML
    private Label infoNbHeroe;

    @FXML
    private TextField nameHeroe;

    @FXML
    private Button next;

    @FXML
    private Label labelError;

    @FXML
    private Spinner spinnerAge;

    @FXML
    private HBox hBoxCiv;

    // Attribut de la class
    private String civilisationChoice;

    private int nbPlayerCreate;
    private int nbPlayer;

    @FXML
    public void initialize() {
        // Recuperation du nombre de Player créé et un nombre total de player
        this.nbPlayerCreate = Game.option.getPlayerList().size() + 1;
        this.nbPlayer = Game.option.getNbPlayers();
        infoNbHeroe.setText("Joueur " + this.nbPlayerCreate + "/" + this.nbPlayer);
        // Changement du texte du bouton play
        if (this.nbPlayerCreate >= this.nbPlayer) next.setText("Jouer");
        else next.setText("Joueur Suivant");
        // On retire les wonders déjà choisi
        this.hideWonderSelected();
    }


    private void hideWonderSelected() {
        for (Node child: this.hBoxCiv.getChildren()) {
            ImageView imgCiv = (ImageView) child;
            // Recupère le nom de la Wonder
            String nameCivImage = imgCiv.getId();
            // Hide if in PlayerList
            for (Player player: Game.option.getPlayerList()) {
                if (Objects.equals(player.getCivilisationName(), nameCivImage)) {
                    imgCiv.setVisible(false);
                }
            }
        }
    }



    /** Quand on selectionne une image */
    @FXML
    protected void onImageCivClick(Event event)  {
        ImageView imgCiv = (ImageView) event.getSource();
        // Recupère le nom de la Wonder
        this.civilisationChoice = imgCiv.getId();
        // Deselection du temoin de selection pour tous sauf celui choisi
        for (Node child: this.hBoxCiv.getChildren()) {
            ImageView imageView = (ImageView) child;
            imageView.getStyleClass().remove("imgCivIsSelect");
        }
        // Temoin de selection
        imgCiv.getStyleClass().add("imgCivIsSelect");
        // Dezoom
        imgCiv.setScaleX(1);
        imgCiv.setScaleY(1);
    }

    /** Quand la sourie rentre dans une image */
    @FXML
    protected void onImageCivHoover(Event event) {
        ImageView imgCiv = (ImageView) event.getSource();
        // Zoom
        imgCiv.setScaleX(1.6);
        imgCiv.setScaleY(1.6);
    }

    /** Quand la sourie quite une image */
    @FXML
    protected void onImageCivQuit(Event event) {
        ImageView imgCiv = (ImageView) event.getSource();
        // Dezoom
        imgCiv.setScaleX(1);
        imgCiv.setScaleY(1);
    }



    @FXML
    protected void onCreatePlayerClick() {
        // Recuperation de l'age du player
        int age;
        try {age = Integer.parseInt(this.spinnerAge.getEditor().getText());}
        catch (NumberFormatException e) {this.labelError.setText("Erreur sur l'âge"); return;}
        // Verification de civilisationChoice
        if (this.civilisationChoice == null) {this.labelError.setText("civilisation non selectionné"); return;}
        // Verification du nom du player
        String name = nameHeroe.getText();
        if (Objects.equals(name, "")) {this.labelError.setText("Nom inexistant"); return;}
        // Création du player
        Game.option.addPlayer(name, age, this.civilisationChoice);
        // Changement de scene
        if (this.nbPlayerCreate < this.nbPlayer) {super.loadPage("playerCreation");}
        else {super.loadPage("game");}
    }


    @FXML
    protected void onMenuButtonClick() {
        super.loadPage("main");
    }


}