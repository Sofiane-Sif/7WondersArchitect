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
        try {age = Integer.parseInt(this.spinnerAge.getEditor().getText().trim());}
        catch (NumberFormatException ignored) {this.labelError.setText("Erreur sur l'âge"); return;}

        // Verification du nom du player
        String name = nameHeroe.getText();
        if (Objects.equals(name, "")) {this.labelError.setText("Nom inexistant"); return;}

        // Verification de civilisationChoice
        if (this.civilisationChoice == null) {this.labelError.setText("civilisation non selectionné"); return;}


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
        else {this.createBot();}
    }

    private void createBot() {
        // Creation des bots
        for (int nbBot = 1; nbBot < Game.option.getNbPlayers(); nbBot++) {
            // Selection d'une wonder aleatoire
            List<Wonders> wondersList = List.of(Wonders.values());
            Random rand = new Random();
            int randomIndex = rand.nextInt(wondersList.size());
            this.civilisationChoice = wondersList.get(randomIndex).name();
            // Dans cette config, le Player commence avant les bots
            Game.option.addBot("Bot-"+nbBot, 200, this.civilisationChoice);
        }
        super.loadPage("game");
    }


    @FXML
    protected void onMenuButtonClick() {
        super.loadPage("main");
    }


}