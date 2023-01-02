package com.isep.controllers;

import com.isep.domain.cards.Card;
import com.isep.domain.conflictToken.ConflictTokens;
import com.isep.domain.progressToken.ProgressToken;
import com.isep.domain.wonders.Wonder;
import com.isep.the7WondersArchitect.Game;
import com.isep.the7WondersArchitect.Player;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class GameController extends ControlleurBase {


    @FXML
    private ImageView imgViewCat, imgViewCentralDeck, imgViewTrash;

    @FXML
    private GridPane GridPaneConflictTokens;

    @FXML
    private VBox vBoxProgressTokens;

    @FXML
    private Label labelProgressToken, labelCentralDeck;

    @FXML
    private AnchorPane anchorPlayers;


    private List<AnchorPane> playerZonesList = new ArrayList<>();



    @FXML
    public void initialize() {


        // New player // A faire dans une page intermediaire entre main et game
        // Alexandrie, Halicarnasse, Ephese, Olympie, Babylone, Rhodes, Gizeh
        Game.option.addPlayer("Admin-1", 20, "Ephese");
        Game.option.addPlayer("Admin-2", 19, "Rhodes");
        Game.option.addPlayer("Admin-3", 60, "Alexandrie");

        /*Game.option.addPlayer("Jon", 45, "Halicarnasse");
        Game.option.addPlayer("Brutus", 30, "Olympie");
        Game.option.addPlayer("Apollo", 70, "Babylone");
        Game.option.addPlayer("Astride", 42, "Gizeh");*/



        Game.option.setNbPlayers();
        this.initialisePlayerZonesList();

        // Positionnement des Players
        List<Player> playerList = Game.option.getPlayerList();
        for (Player player: playerList) {

            // Recuperation de la boite d'affichage du player
            AnchorPane playerZone = this.playerZonesList.get(playerList.indexOf(player));
            ObservableList<Node> playerZoneChildren = playerZone.getChildren();
            playerZone.setVisible(true);

            // Affichage du nom et de la civilisation du player
            Label labelZone = (Label) playerZoneChildren.get(1);
            labelZone.setText(player.getName() + "\n" + player.getCivilisationName());

            // Positionnement des Deck Players placé à sa gauche
            List<Card> cardListPlayer = player.createDeckPlayer();
            // Recuperation de l'image de la 1ere carte du deck
            ImageView imgZone = (ImageView) playerZoneChildren.get(0);
            String imgPathCardPlayer = cardListPlayer.get(0).front.imageResource;
            Image imgCardPlayer = ControlleurBase.setAnImage(imgPathCardPlayer);
            // Affichage des cardDeckPlayer
            imgZone.setImage(imgCardPlayer);

            // Positionnement des Wonder Players
            Wonder wonderPlayer = player.createWonder();


            System.out.println(wonderPlayer);


        }









        // Positionnement du chat
        String imgPathCat = Game.option.settingCat();
        Image imgCat = ControlleurBase.setAnImage(imgPathCat);
        this.imgViewCat.setImage(imgCat);


        // Positionnement des conflictTokens
        ArrayList<ConflictTokens> conflictTokensArrayList = Game.option.settingConflictTokens();
        for (ConflictTokens conflictTokens: conflictTokensArrayList) {
            // Recuperation de l'image
            String imgPathconflictTokens = conflictTokens.getImagePathFace();
            Image imgconflictTokens = ControlleurBase.setAnImage(imgPathconflictTokens);
            // Récuperation de l'imageView
            int numIndex = conflictTokensArrayList.indexOf(conflictTokens);
            ImageView imageConflictToken = ((ImageView) this.GridPaneConflictTokens.getChildren().get(numIndex));
            // Affichage
            imageConflictToken.setVisible(true);
            imageConflictToken.setImage(imgconflictTokens);
        }


        // Positionnement des progressTokens visibles
        List<ProgressToken> progressTokensList = Game.option.settingProgressTokens();
        for (int numProgressToken = 0; numProgressToken < 3; numProgressToken++) {
            // Recuperation de l'image
            ProgressToken progressToken = progressTokensList.get(numProgressToken);
            String imagePathProgressToken = progressToken.imageResource;
            Image imgProgressToken = ControlleurBase.setAnImage(imagePathProgressToken);
            // Récuperation de l'imageView
            ImageView imageProgressToken = ((ImageView) this.vBoxProgressTokens.getChildren().get(numProgressToken+1));
            // Affichage des labelProgressToken
            imageProgressToken.setImage(imgProgressToken);
        }
        // Affichage du nombre de labelProgressToken restant dans la pioche
        this.labelProgressToken.setText(""+progressTokensList.size());




        // Positionnement du jeu de carte central
        List<Card> cardListCentralDeck = Game.option.settingCentralDeck();
        // Affichage du nombre de CentralDeckCard restant dans la pioche
        this.labelCentralDeck.setText(""+cardListCentralDeck.size());
        // Affiche le dos de la 1ere Card
        String imagePathCentralDeck = cardListCentralDeck.get(0).back.imgBackPath;
        Image imgCentralDeck = ControlleurBase.setAnImage(imagePathCentralDeck);
        this.imgViewCentralDeck.setImage(imgCentralDeck);

    }



    private void initialisePlayerZonesList() {
        for (Node playerZone: this.anchorPlayers.getChildren()) {
            this.playerZonesList.add((AnchorPane) playerZone);
        }
    }




}