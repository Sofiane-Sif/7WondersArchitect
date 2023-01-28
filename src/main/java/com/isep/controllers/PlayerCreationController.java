package com.isep.controllers;

import com.isep.items.wonders.Wonders;
import com.isep.the7WondersArchitect.Game;
import com.isep.the7WondersArchitect.Player;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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

    @FXML CheckBox CheckBoxWonderSelection;

    // Attribut de la class
    private String civilisationChoice;

    private int nbPlayerCreate;
    private int nbPlayer;

    @FXML
    public void initialize() {

        this.screenElements.setScaleX(1.5);
        this.screenElements.setScaleY(1.5);

        if (!Game.option.isBotModActived()) {
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
        else {
            infoNbHeroe.setText("One Player VS Bots");
            next.setText("Jouer");
        }
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
        this.deselectAllWonders(null);
        // Temoin de selection
        imgCiv.getStyleClass().add("imgCivIsSelect");
        // Dezoom
        imgCiv.setScaleX(1);
        imgCiv.setScaleY(1);
        // Deselection du random Choice
        this.CheckBoxWonderSelection.setSelected(false);
    }

    @FXML
    protected void deselectAllWonders(Event event) {
        // Deselection du temoin de selection pour tous sauf celui choisi
        for (Node child: this.hBoxCiv.getChildren()) {
            ImageView imageView = (ImageView) child;
            imageView.getStyleClass().remove("imgCivIsSelect");
        }
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
        try {age = Integer.parseInt(this.spinnerAge.getEditor().getText().trim());}
        catch (NumberFormatException ignored) {this.labelError.setText("Erreur sur l'âge"); return;}

        // Verification du nom du player
        String name = nameHeroe.getText();
        if (Objects.equals(name, "")) {this.labelError.setText("Nom inexistant"); return;}

        // Verification de civilisationChoice
        if (this.civilisationChoice == null & !this.CheckBoxWonderSelection.isSelected()) {
            this.labelError.setText("civilisation non selectionné"); return;
        }

        // Random Wonder
        if(this.CheckBoxWonderSelection.isSelected()) {this.civilisationChoice=Game.option.chooseRandomWonder();}

        //System.out.println("Choose :" + this.civilisationChoice);
        // Création du player
        Game.option.addPlayer(name, age, this.civilisationChoice);
        // Changement de scene
        if (!Game.option.isBotModActived()) {
            if (this.nbPlayerCreate < this.nbPlayer) {
                super.loadPage("playerCreation");
            } else {
                super.loadPage("game");
            }
        }
        else {
            Game.option.createBot();
            super.loadPage("game");}
    }




    @FXML
    protected void onMenuButtonClick() {
        super.loadPage("main");
    }


}