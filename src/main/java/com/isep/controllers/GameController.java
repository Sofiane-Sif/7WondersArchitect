package com.isep.controllers;

import com.isep.items.cards.Card;
import com.isep.items.conflictToken.ConflictTokens;
import com.isep.items.progressToken.ProgressToken;
import com.isep.items.wonders.Wonders;
import com.isep.the7WondersArchitect.Game;
import com.isep.the7WondersArchitect.Player;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
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
    public void initialize() throws IOException {

        this.initialisePlayerZonesList();

        // Positionnement des Players
        List<Player> playerList = Game.option.getPlayerList();
        Player firstPlayer = new Player("Error", 200);
        int posFirstPlayer = 10;
        for (Player player: playerList) {

            // Recuperation de la boite d'affichage du player
            AnchorPane playerZone = this.playerZonesList.get(playerList.indexOf(player));
            ObservableList<Node> playerZoneChildren = playerZone.getChildren();
            playerZone.setVisible(true);

            // Positionnement des Deck Players placé à sa gauche
            List<Card> cardListPlayer = player.createDeckPlayer();
            // Recuperation de l'image de la 1ere carte du deck
            ImageView imgZone = (ImageView) playerZoneChildren.get(0);
            String imgPathCardPlayer = cardListPlayer.get(0).front.imageResource;
            Image imgCardPlayer = ControlleurBase.setAnImage(imgPathCardPlayer);
            // Affichage des cardDeckPlayer
            imgZone.setImage(imgCardPlayer);

            // Positionnement des Wonder Players
            Wonders wonderPlayer = player.createWonder();
            // Recuperation de la Vbox Existante
            VBox wonderBox = (VBox) playerZoneChildren.get(2);
            // generation de la VBox bien comme il faut
            ObservableList <Node> wonderBoxElements = wonderPlayer.createImage().getChildren();
            wonderBox.getChildren().addAll(wonderBoxElements);

            // Calcul de Width et Heigth de la wonderBox pour chaque images pour la placement du label du Player
            int height =0;
            double width =0;
            for (Node node: wonderBox.getChildren()) {
                Bounds coords = node.getLayoutBounds();
                height += coords.getMaxY();
                if (node instanceof HBox) {height += ((HBox) node).getChildren().get(1).getLayoutBounds().getMaxY();}
                if (width<coords.getMaxX()) {width=coords.getMaxX();}
            }

            // Affichage du nom et de la civilisation du player
            Label labelPlayer = (Label) playerZoneChildren.get(1);
            labelPlayer.setText(player.getName()+"\n"+player.getCivilisationName());
            // Positionne le label sous sa Wonder
            labelPlayer.setLayoutX(5+wonderBox.getLayoutX()+width/2-labelPlayer.getPrefWidth()/2);
            labelPlayer.setLayoutY(5+wonderBox.getLayoutY()+height);

            // Recupère le joueur le plus jeune
            if (player.getAge() < firstPlayer.getAge()) {
                firstPlayer = player;
                posFirstPlayer = playerList.indexOf(player);
            }

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

        // Met dans l'ordre la liste des player en fonction du premier joueur
        Game.option.definePlayerOrder(firstPlayer);
        // Change l'odre de la list des AnchorPane des player
        this.changeListAnchorPaneOrder(posFirstPlayer);
    }



    private void initialisePlayerZonesList() {
        for (Node playerZone: this.anchorPlayers.getChildren()) {
            this.playerZonesList.add((AnchorPane) playerZone);
        }
    }


    @FXML
    protected void onMenuButtonClick() {
        super.loadPage("main");
    }


    @FXML
    protected void onStartGameButton(Event event) {
        // Cache le boutton
        Button buttonStart = (Button) event.getSource();
        buttonStart.setVisible(false);
        // Enlève la visibilité du premier joueur
        this.playerZonesList.get(0).getStyleClass().remove("firstPlayerSelection");
        // Début de la partie
        this.startGame();
    }


    public void changeListAnchorPaneOrder(int posFirstPlayer) {
        // Recupère le joueur le plus jeune et change l'ordre des joueurs
        List<AnchorPane> newList = new ArrayList<>();
        int size = this.playerZonesList.size();
        for (int numPlayer = posFirstPlayer; numPlayer < size+posFirstPlayer; numPlayer++) {
            newList.add(this.playerZonesList.get(numPlayer % size));
        }
        this.playerZonesList = newList;
    }



    public void startGame() {
        /*
         * While !gameOver
             * Le premier joueur pioche une carte
                * On regarde la type de la carte
                * En fonction de son type on execute une fonction verifiant les possiblilité assosié à cette carte
                * Verfi si a gagné
          * Tour du player suivant
          * ...
      * On compte les points pour savoir qui a gangné
         */
        System.out.println("Lancement pour un tour de jeu");

        this.nextPlayerTurn();
    }



    // info player
    private int numPlayer;
    private Player playerTurn;
    private ObservableList<Node> visualPlayerTurn;
    private ImageView deckPlayerImg;
    private List<Card> deckPlayer;
    private VBox wonderPlayerImg;
    private Wonders wonderPlayer;
    private ImageView deckRightPlayerImg;
    private List<Card> deckRightPlayer;



    private void setInfoPlayerTurn() {
        // index du player dans la playerList
        this.numPlayer = Game.option.getNumPlayerTurn() % Game.option.getNbPlayers();
        // Va chercher le Player et sa zone
        this.playerTurn = Game.option.getPlayerList().get(this.numPlayer);
        this.visualPlayerTurn = this.playerZonesList.get(this.numPlayer).getChildren();
        // Va chercher le DeckPlayer
        this.deckPlayerImg = (ImageView) this.visualPlayerTurn.get(0);
        this.deckPlayer = this.playerTurn.getDeckPlayer();
        // Va chercher la WonderPlayer
        this.wonderPlayerImg = (VBox) this.visualPlayerTurn.get(2);
        this.wonderPlayer = this.playerTurn.getWonder();
        // Va chercher le cardDeck présent à la droite du Player ainsi que son image
        int posRightPlayer = (this.numPlayer-1+Game.option.getNbPlayers()) % Game.option.getNbPlayers();
        Player playerDroite = Game.option.getPlayerList().get(posRightPlayer);
        this.deckRightPlayer = playerDroite.getDeckPlayer();
        this.deckRightPlayerImg = (ImageView) this.playerZonesList.get(posRightPlayer).getChildren().get(0);
    }


    private void nextPlayerTurn() {
        // Récupération des infos du player qui doit jouer
        this.setInfoPlayerTurn();

        // Met en couleur le joueur qui joue;
        this.wonderPlayerImg.getStyleClass().add("selectWonder");

            // Mets les jeux de cartes disponibles en couleur  et clickable
        // Deck du Player
        this.deckPlayerImg.getStyleClass().add("selectDeck");
        this.deckPlayerImg.setOnMouseClicked(this::onSelectionCartePlayerButton);
        // Deck de droite
        this.deckRightPlayerImg.getStyleClass().add("selectDeck");
        this.deckRightPlayerImg.setOnMouseClicked(this::onSelectionCartePlayerButton);
        // Deck du millieu
        this.imgViewCentralDeck.getStyleClass().add("selectDeck");
        this.imgViewCentralDeck.setOnMouseClicked(this::onSelectionCartePlayerButton);

        /*
         * L'IA / RobotPlayer est à mettre ici
            * Soit on a un Player Humain et c'est le code plus haut
            * Soit c'est l'IA et c'est un code qui choisit une carte puis appele onSelectionCartePlayerButton()
         */
    }


    private void onSelectionCartePlayerButton(Event event) {

            // Deselection de tous les elements mis en avant
        // WonderPlayer
        this.wonderPlayerImg.getStyleClass().remove("selectWonder");
        // PlayerDeck
        this.deckPlayerImg.getStyleClass().remove("selectDeck");
        this.deckPlayerImg.setOnMouseClicked(null);
        // CentralDeck
        this.imgViewCentralDeck.getStyleClass().remove("selectDeck");
        this.imgViewCentralDeck.setOnMouseClicked(null);
        // RightDeck
        this.deckRightPlayerImg.getStyleClass().remove("selectDeck");
        this.deckRightPlayerImg.setOnMouseClicked(null);


        // recupereaton de la carte
        System.out.println("Carte click");

        // Effet de la carte


        // Passe au player suivant ou GameOver
        Game.option.setNumPlayer();
        if (Game.option.getNumPlayerTurn() < Game.option.getNbPlayers()*10) {this.nextPlayerTurn();}
        else {Game.option.isGameOver();
            System.out.println("Nombre de tour de jeu dépassé. \nGAME OVER");}
    }


}