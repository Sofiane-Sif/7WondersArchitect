package com.isep.controllers;

import com.isep.items.cards.Card;
import com.isep.items.cards.Material;
import com.isep.items.cards.ScienceCategory;
import com.isep.items.conflictToken.ConflictTokens;
import com.isep.items.progressToken.ProgressToken;
import com.isep.items.wonders.Wonders;
import com.isep.the7WondersArchitect.Game;
import com.isep.the7WondersArchitect.Player;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
            // Application du style null pour pouvoir recuperer le nom de la merveille du deck
            imgZone.setId(player.getCivilisationName());
            // Positionnement et affichage du nombre de carte restante
            Label labeNbCardDeck = (Label) playerZoneChildren.get(1);
            labeNbCardDeck.setText(" "+cardListPlayer.size());
            labeNbCardDeck.setLayoutY(135);

            // Affichage des cardDeckPlayer
            imgZone.setImage(imgCardPlayer);

            // Positionnement des Wonder Players
            Wonders wonderPlayer = player.createWonder();
            // Recuperation de la Vbox Existante
            VBox wonderBox = (VBox) playerZoneChildren.get(2);
            // generation de la VBox bien comme il faut
            ObservableList <Node> wonderBoxElements = wonderPlayer.createImage().getChildren();
            wonderBox.getChildren().addAll(wonderBoxElements);

            // Recupère le joueur le plus jeune
            if (player.getAge() < firstPlayer.getAge()) {
                firstPlayer = player;
                posFirstPlayer = playerList.indexOf(player);
            }

            // Initialisation des ressources à 0
            GridPane gridPaneRessources = ((GridPane) playerZoneChildren.get(4));
            ObservableList<Node> gridPaneChild= gridPaneRessources.getChildren();
            for (Node n: gridPaneChild) {
                ((Label) n).setText("0");
            }

            ImageView imageRessources = ((ImageView) playerZoneChildren.get(3));
            Bounds coordImg = imageRessources.getLayoutBounds();

            // Affichage du nom et de la civilisation du player
            Label labelPlayer = (Label) playerZoneChildren.get(5);
            labelPlayer.setText(player.getName()+"\n"+player.getCivilisationName());
            // Positionne le label sous sa Wonder

            labelPlayer.setLayoutX(imageRessources.getLayoutX()+coordImg.getWidth()/3);
            labelPlayer.setLayoutY(imageRessources.getLayoutY()+10);

            // Dezoom sur les boites de ressources !!!!!
            imageRessources.setScaleX(0.7);
            imageRessources.setScaleY(0.7);
            gridPaneRessources.setScaleX(0.7);
            gridPaneRessources.setScaleY(0.7);
            labelPlayer.setScaleX(0.7);
            labelPlayer.setScaleY(0.7);

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
        this.imgViewCentralDeck.setId("CentralDeck");

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
         *OK While !gameOver
             *OK Le premier joueur pioche une carte
                *OK On regarde la type de la carte
                *-> En fonction de son type on execute une fonction verifiant les possiblilité assosié à cette carte
                *-> Verfi si a gagné
          *OK Tour du player suivant
          * ...
      *-> On compte les points pour savoir qui a gangné
         */
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
    private List<Card> centraldeck;



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
        // Va recuperer le centralDeck
        this.centraldeck = Game.option.getCentralDeck();

    }


    private void nextPlayerTurn() {
        // Récupération des infos du player qui doit jouer
        this.setInfoPlayerTurn();
        // Met en couleur le joueur qui joue;
        this.wonderPlayerImg.getStyleClass().add("selectWonder");

            // Mets les jeux de cartes disponibles en couleur  et clickable
        // Deck du Player
        if (this.deckPlayer.size()>=1) {
            this.deckPlayerImg.getStyleClass().add("selectDeck");
            this.deckPlayerImg.setOnMouseClicked(this::onSelectionCartePlayerButton);
        }
        // Deck de droite
        if (this.deckRightPlayer.size()>=1) {
            this.deckRightPlayerImg.getStyleClass().add("selectDeck");
            this.deckRightPlayerImg.setOnMouseClicked(this::onSelectionCartePlayerButton);
        }
        // Deck du millieu
        if (this.centraldeck.size()>=1) {
            this.imgViewCentralDeck.getStyleClass().add("selectDeck");
            this.imgViewCentralDeck.setOnMouseClicked(this::onSelectionCartePlayerButton);
        }

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

        // recupératon de la carteImg et du nom de la civilisation du deck
        ImageView selectCardImg = (ImageView) event.getSource();
        String civNameCard = selectCardImg.getId();

        // Pioche la carte du deck selectionné
        String newCardDeckImgPath = playerTurn.piocheCarte(civNameCard);
        Image newCardDeckImg = ControlleurBase.setAnImage(newCardDeckImgPath);
        selectCardImg.setImage(newCardDeckImg);
        // Met à jour le nombre de cartes restants
        this.majInfoSizeDeck();
        this.majInfoRessourcesPlayer();

        // Joue la carte
        this.playerTurn.usePowerCard();

        // Passe au player suivant ou GameOver
        Game.option.setNumPlayer();
        if (Game.option.getNumPlayerTurn() < Game.option.getNbPlayers()*100) {this.nextPlayerTurn();}
        else {Game.option.isGameOver();
            System.out.println("Nombre de tour de jeu dépassé. \nGAME OVER");}
    }

    private void majInfoRessourcesPlayer() {

        System.out.println("Maj des infos ressources en cours d'implementation");
        /*
        * switch (type[0]) {
            case "material" -> getNbRessource(nameRessource);
            case "science" -> getNbScience(nameScience);
            case "war" -> getNbShildPeace() & getNbShildWar();
            case "politic" -> getNbPolilicPoint();
            default -> new ArrayList<>();
        };
        *
        * if ("material") maj le material en question
        * if ("science") maj le science en question
        * if ("war") maj les deux labels war
        * if ("politic") maj politic
        *
        *
        * On peut meme rassembler ces deux blocs
         */







        // Lecture des types de la carte choisi
        String typeCard = this.playerTurn.getTypeCardInIsHand();
        String[] type = typeCard.split(":");
/*
        // Recuperation du bon deck
        System.out.println();
        List<Card> cardList = switch (type[0]) {
            case "material" -> this.playerTurn.getMaterialCardList();
            case "science" -> this.playerTurn.getProgressCardList();
            case "war" -> this.playerTurn.getWarCardList();
            case "politic" -> this.playerTurn.getPoliticCardList();
            default -> new ArrayList<>();
        };

        // Compte le nombre de carte pareil
        int nbCard = 0;
        for (Card card: cardList) {

            if (Objects.equals(type[0], "material") | Objects.equals(type[0], "science")) {
                String typeOfTheCard = card.front.cardDisplayName.split(":")[0];
                System.out.println(typeOfTheCard +" - "+type[0]);

                if (Objects.equals(typeOfTheCard, type[0])) {nbCard++;}
            }


        }
        /*
         * Fonctionne pour material and science but not for war and politic
         *
         */

      //  System.out.println(type[1]+" = " + nbCard);



        // Recuperation des elements d'affichage
        GridPane gridPaneRessources = ((GridPane) this.visualPlayerTurn.get(4));
        ObservableList<Node> gridPaneChild= gridPaneRessources.getChildren();
        // Trensformation en dictionnaire avec key=n.getStyleClass().get(2) et value=Label
        HashMap<String, Label> dictionary = new HashMap<String, Label>();
        for (Node n: gridPaneChild) {
            Label labelRessource = ((Label) n);
            String typeLabel = n.getStyleClass().get(2);
            dictionary.put(typeLabel, labelRessource);
        }
/*

        for (Label l : dictionary.values()) {
            System.out.println(l);
        }


        Label labelRessource;

        if (Objects.equals(type[1], "barbarian") | Objects.equals(type[1], "archer")) {
            labelRessource = dictionary.get("war");
        }
        else if (Objects.equals(type[1], "centurion")) {

        }

        /*
         * Dico ressource
         * -> recuperer soustypecarte
            * if barbarian or archer -> label = dico.get(corne)
            * else if centurion -> label = dico.get(shild)
            * else (material ou science) -> label = dico.get(soustypecarte)

        * -> recuperer typecarte
         * * Si politic -> label = dico.get(typecarte)
         */

/*
        for (Node n: gridPaneChild) {
            String typeLabel = n.getStyleClass().get(2);

            if (Objects.equals(type[1], "wood") & Objects.equals(typeLabel, "wood")) {}
            else if (Objects.equals(type[1], "paper") & Objects.equals(typeLabel, "paper")) {}
            else if (Objects.equals(type[1], "brick") & Objects.equals(typeLabel, "brick")) {}
            else if (Objects.equals(type[1], "stone") & Objects.equals(typeLabel, "stone")) {}
            else if (Objects.equals(type[1], "glass") & Objects.equals(typeLabel, "glass")) {}
            else if (Objects.equals(type[1], "gold") & Objects.equals(typeLabel, "gold")) {}

            else if (Objects.equals(type[1], "law") & Objects.equals(typeLabel, "law")) {}
            else if (Objects.equals(type[1], "mechanic") & Objects.equals(typeLabel, "mechanic")) {}
            else if (Objects.equals(type[1], "architect") & Objects.equals(typeLabel, "architect")) {}

            else if (Objects.equals(type[1], "centurion") & Objects.equals(typeLabel, "winScience")) {}
            else if ((Objects.equals(type[1], "archer") | Objects.equals(type[1], "winScience")) & Objects.equals(typeLabel, "winScience")) {}

            else if (Objects.equals(type[1], "emperor") & Objects.equals(typeLabel, "winScience")) {}
            else if (Objects.equals(type[1], "cat") & Objects.equals(typeLabel, "winScience")) {}
        }
                /*
                "material:wood"
                "material:paper"
                "material:brick"
                "material:stone"
                "material:glass"
                // joker (mandatory replacing any material)
                "material:gold"
                // Science cards
                "science:law"
                "science:mechanic"
                "science:architect"

                // War Cards
                "war:barbarian"
                "war:centurion"
                "war:archer"

                // Polics Cards
                "politic:emperor"
                "politic:cat"
                */

/*


        // En fonction du type et du sous type de la carte
        switch (type[0]) {
            case "material" -> {

                Label labelRessource;
                int newValue;

                switch (type[1]) {
                    case "wood" -> {System.out.println("wood");}
                    case "paper" -> {System.out.println("paper");}
                    case "brick" -> {System.out.println("brick");}
                    case "stone" -> {System.out.println("stone");}
                    case "glass" -> {System.out.println("glass");}
                    case "gold" -> {System.out.println("gold");}
                }


            }
            case "science" -> {
                System.out.println("science");
            }
            case "war" -> {
                System.out.println("war");
            }
            case "politic" -> {
                System.out.println("politic");
            }
        }


*/

        // Error Pas la bonne implementation !!!!
        for (Node n: gridPaneChild) {
            Label labelRessource = ((Label) n);
            int newValue = Integer.parseInt(labelRessource.getText());
            // For Material and ScienceCategory

            if (Objects.equals(n.getStyleClass().get(2), type[1])) {
                //System.out.println(newValue);
                switch (type[0]) {
                    case "material" -> newValue = this.playerTurn.getMaterialCardList().size();
                    case "science" -> newValue = this.playerTurn.getProgressCardList().size();
                }
            //    System.out.println(newValue);
           //     System.out.println(this.playerTurn.getMaterialCardList().size());
            //    System.out.println(this.playerTurn.getProgressCardList().size());
             //   System.out.println(n.getStyleClass().get(2) + " - "+type[0]+" - "+type[1]);
            }
            labelRessource.setText(newValue+"");
        }


    }

    private void majInfoSizeDeck() {
        int sizeDeck = Game.option.getCentralDeck().size();
        this.labelCentralDeck.setText(""+sizeDeck);
        // Met à jour le l'affichage du nombre de carte poru chaque Deck
        for (int numPlayer = 0; numPlayer < Game.option.getNbPlayers(); numPlayer++) {
            sizeDeck = Game.option.getPlayerList().get(numPlayer).getDeckPlayer().size();
            ((Label) this.playerZonesList.get(numPlayer).getChildren().get(1)).setText(" "+sizeDeck);
        }
    }


}