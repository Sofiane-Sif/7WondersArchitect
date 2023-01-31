package com.isep.controllers;

import com.isep.items.cards.Card;
import com.isep.items.conflictToken.ConflictTokens;
import com.isep.items.progressToken.ProgressToken;
import com.isep.items.wonders.Wonders;
import com.isep.the7WondersArchitect.Bot7Wonder;
import com.isep.the7WondersArchitect.Game;
import com.isep.the7WondersArchitect.Player;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameController extends ControlleurBase {


    @FXML
    private ImageView imgViewCat, imgViewCentralDeck, imgViewTrash, cardMovement;

    @FXML
    private GridPane GridPaneConflictTokens;

    @FXML
    private VBox vBoxProgressTokens;

    @FXML
    private Label labelProgressToken, labelCentralDeck;

    @FXML
    private AnchorPane anchorPlayers, anchorPaneScore;

    private List<AnchorPane> playerZonesList = new ArrayList<>();



    @FXML
    public void initialize() {

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
            //labeNbCardDeck.setLayoutY(125);

            // Affichage des cardDeckPlayer
            imgZone.setImage(imgCardPlayer);

            // Positionnement des Wonder Players
            Wonders wonderPlayer = player.createWonder();
            // Recuperation de la Vbox Existante
            VBox wonderBox = (VBox) playerZoneChildren.get(2);
            // generation de la VBox bien comme il faut
            ObservableList <Node> wonderBoxElements = wonderPlayer.createImage().getChildren();
            wonderBox.getChildren().addAll(wonderBoxElements);
            // Place la Wonder à Gauche du tas et au dessus du paneau ressources
            //...
            int tryInY = switch (wonderPlayer.toString()){case "Alexandrie"->-50;case"Rhodes"->-35;
                case"Halicarnasse","Ephese"->-20; case"Olympie"->-3; default -> 0;};
            wonderBox.setLayoutX(110);
            wonderBox.setLayoutY(tryInY);

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

            // Affichage du nom et de la civilisation du player getCivilisationName
            Label labelPlayer = (Label) playerZoneChildren.get(5);
            labelPlayer.setText(player.getCivilisationName()+" - "+player.getName());
            // Positionne le label sous sa Wonder
            //labelPlayer.setLayoutX(imageRessources.getLayoutX()+coordImg.getWidth()/3);
            labelPlayer.setLayoutY(imageRessources.getLayoutY()+5);

            // Dezoom sur les boites de ressources !!!!!
            /*double zoom = 0.9;
            imageRessources.setScaleX(zoom);
            imageRessources.setScaleY(zoom);
            gridPaneRessources.setScaleX(zoom);
            gridPaneRessources.setScaleY(zoom);
            labelPlayer.setScaleX(zoom);
            labelPlayer.setScaleY(zoom);*/

        }

        // Positionnement du chat
        String imgPathCat = Game.option.createCat();
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
            String imagePathProgressToken = progressToken.getImageResource();
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
    private VBox wonderPlayerImg;
    private Wonders wonderPlayer;
    private ImageView deckPlayerImg;
    private ImageView deckRightPlayerImg;
    private List<Card> deckPlayer;
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

        if(this.playerTurn instanceof Bot7Wonder) {
            // Selection d'une carte
            ((Bot7Wonder) this.playerTurn).readGame(deckPlayer, deckRightPlayer, centraldeck);
            // Recuperation du nameWonderDeck choose
            String civNameCard = ((Bot7Wonder) this.playerTurn).returnBestAction();
            // Recuperation de l'ImageView du deck de la civ select parmis les trois pioches possibles
            ImageView selectCardImg;
            if (Objects.equals(civNameCard, "CentralDeck")) {selectCardImg=this.imgViewCentralDeck;}
            else if (Objects.equals(civNameCard, this.playerTurn.getCivilisationName())) {selectCardImg=this.deckPlayerImg;}
            else {selectCardImg=this.deckRightPlayerImg;}
            // Impact et controle de la carte
            this.cartePlayerAction(civNameCard, selectCardImg);
        }
        //C'est a un Player Humain de jouer
        else {
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
            // Affiche the Cat et la carte central face visible
            if (this.playerTurn.haveTheCat()) {
                this.imgViewCat.getStyleClass().add("selectCat");
                if (this.centraldeck.size()>0) {
                    Card cardCentral = this.centraldeck.get(0);
                    String imgFrontPath = cardCentral.front.imageResource;
                    this.imgViewCentralDeck.setImage(ControlleurBase.setAnImage(imgFrontPath));
                }
            }
        }
    }



    private void onSelectionCartePlayerButton(Event event) {
        // Deselection de tous les elements mis en avant
        // PlayerDeck
        this.deckPlayerImg.getStyleClass().remove("selectDeck");
        this.deckPlayerImg.setOnMouseClicked(null);
        // CentralDeck
        this.imgViewCentralDeck.getStyleClass().remove("selectDeck");
        this.imgViewCentralDeck.setOnMouseClicked(null);
        // RightDeck
        this.deckRightPlayerImg.getStyleClass().remove("selectDeck");
        this.deckRightPlayerImg.setOnMouseClicked(null);
        // Cat et la carte central
        if (this.playerTurn.haveTheCat()) {
            this.imgViewCat.getStyleClass().remove("selectCat");
            if (this.centraldeck.size()>0) {
                this.imgViewCentralDeck.setImage(ControlleurBase.setAnImage("images/cards/card-back/card-back-question.png"));
            }
        }
        // recupératon de la carteImg et du nom de la civilisation du deck
        ImageView selectCardImg = (ImageView) event.getSource();
        String civNameCard = selectCardImg.getId();
        // Impact et controle de la carte
        this.cartePlayerAction(civNameCard, selectCardImg);
    }

    private void cartePlayerAction(String civNameCard, ImageView selectCardImg) {
        // Pioche la carte du deck selectionné
        String newCardDeckImgPath = playerTurn.piocheCarte(civNameCard);
        // Affiche la nouvelle carte du deck choisi
        Image newCardDeckImg = ControlleurBase.setAnImage(newCardDeckImgPath);
        // Joue la carte
        this.playerTurn.usePowerCard();
        // Maj de la Wonder
        this.majInfoSizeDeck();
        this.majInfoRessourcesPlayer();
        this.majViewWonderPlayer();
        this.majConflictTokenImage();
        this.majConflictTokenCornPlayer();
        // Annimation
        this.moveCardsIntoTrash(selectCardImg, newCardDeckImg);
    }

    private void endTurn() {
        // WonderPlayer
        this.wonderPlayerImg.getStyleClass().remove("selectWonder");
        // Passe au player suivant ou GameOver
        Game.option.setNumPlayer();
        if(Game.option.isGameOver()) {this.printScore();}
        else {this.nextPlayerTurn();}
    }

    /**
     * Affiche la carte choisi dans un new ImageView au meme endroit
     * Retour la nouvelle carte
     * deplace le new ImageView vers la trash
     * change l'image de la trash avec la nouvelle carte
     * supprimer l'ImageView de deplacement
     * @param CardDeckView Pioche de la carte choisie
     * @param newCardDeck Nouvelle Card de cette pioche
     */
    private void moveCardsIntoTrash(ImageView CardDeckView, Image newCardDeck) {
        // Retour la nouvelle carte du deck
        CardDeckView.setImage(newCardDeck);
        // Image de la nouvelle Card
        String pathImgGet = this.playerTurn.getCardInIsHandImgPath();
        Image newRubbichImg = ControlleurBase.setAnImage(pathImgGet);
        // Deplace le cardMovement sur la position du deck selectionné
        double posImgMoveX = CardDeckView.getLocalToSceneTransform().getTx();
        double posImgMoveY = CardDeckView.getLocalToSceneTransform().getTy();
        // Met l'imageS de CardSelect et la rend visible
        this.cardMovement.setImage(newRubbichImg);
        this.cardMovement.setVisible(true);
        // Coordonnée de la trash
        double posTrashX = this.imgViewTrash.getLocalToSceneTransform().getTx();
        double posTrashY = this.imgViewTrash.getLocalToSceneTransform().getTy();
        // Deplacement de la carte
        int  timeStep = 500;
        // Création et parameters de l'annimation
        TranslateTransition tt = new TranslateTransition(Duration.millis(timeStep), this.cardMovement);
        tt.setFromX(posImgMoveX);
        tt.setFromY(posImgMoveY);
        tt.setToX(posTrashX);
        tt.setToY(posTrashY);
        // Suite du jeu après la fin de l'annimation
        tt.setOnFinished(event-> {
            imgViewTrash.setImage(newRubbichImg);
            cardMovement.setVisible(false);
            this.endTurn();
        });
        // Joue l'annimation
        tt.play();
    }


    private void printScore() {
        // Affiche la table des scores
        this.anchorPaneScore.setVisible(true);
        // List des VBoxScorePlayer
        ObservableList<Node> vBoxListScore = ((HBox) this.anchorPaneScore.getChildren().get(1)).getChildren();
        // Pour chaque Player
        List<Player> playerList = Game.option.getPlayerList();
        for (int numPlayer = 0; numPlayer < playerList.size(); numPlayer++) {
            // Recuperation du Player et de sa zone de score
            Player player = playerList.get(numPlayer);
            VBox zoneScore = (VBox) vBoxListScore.get(numPlayer);
            // Liste des labelScoreList
            ObservableList<Node> lScLst = zoneScore.getChildren();
            // Affiche la premiere lettre de la civWonder
            this.getLabel(lScLst, 0).setText(player.getCivilisationName().substring(0, 3));
            // Recuperation des scores du Player
            List<String> lstScore = player.countMyScore();
            // Affiche les scores
            this.getLabel(lScLst, 1).setText(lstScore.get(0));
            this.getLabel(lScLst, 2).setText(lstScore.get(1));
            this.getLabel(lScLst, 3).setText(lstScore.get(2));
            this.getLabel(lScLst, 4).setText(lstScore.get(3));
            this.getLabel(lScLst, 5).setText(lstScore.get(4));
            this.getLabel(lScLst, 6).setText(lstScore.get(5));
        }
        // Recup Winner
        Player winner = Game.option.getWinner();
        int indexWinner = Game.option.getPlayerList().indexOf(winner);
        // Si bot -> visibility sur le player
        if (Game.option.isBotModActived() & indexWinner!=0) {
            this.playerZonesList.get(0).getStyleClass().add("selectPlayerbot");
            vBoxListScore.get(0).getStyleClass().add("selectPlayerbot");
        }
        // Visibility sur le Winner
        VBox zoneWinner = (VBox) vBoxListScore.get(indexWinner);
        zoneWinner.getStyleClass().add("selectWinner");
        this.playerZonesList.get(indexWinner).getStyleClass().add("selectWinnerWonder");
    }

    private Label getLabel(ObservableList<Node> labelList, int index) {return (Label) labelList.get(index);}




    private void majInfoRessourcesPlayer() {

        // Lecture du type de la carte choisi
        String typeCard = this.playerTurn.getTypeCardInIsHand();
        String[] type = typeCard.split(":");

        // Recuperation des elements d'affichage
        GridPane gridPaneRessources = ((GridPane) this.visualPlayerTurn.get(4));
        ObservableList<Node> gridPaneChild= gridPaneRessources.getChildren();
        // Transformation en dictionnaire avec key=n.getStyleClass().get(2) et value=Label
        HashMap<String, Label> dicLabel = new HashMap<String, Label>();
        for (Node n: gridPaneChild) {
            Label labelRessource = ((Label) n);
            String typeLabel = n.getStyleClass().get(2);
            dicLabel.put(typeLabel, labelRessource);
        }

        // recup la nouvelle value et l'affiche dans le label
        switch (type[0]) {
            case "material" -> {
                // On  met à jour tous les valeurs
                dicLabel.get("wood").setText(this.playerTurn.getNbRessource("wood")+"");
                dicLabel.get("paper").setText(this.playerTurn.getNbRessource("paper")+"");
                dicLabel.get("brick").setText(this.playerTurn.getNbRessource("brick")+"");
                dicLabel.get("stone").setText(this.playerTurn.getNbRessource("stone")+"");
                dicLabel.get("glass").setText(this.playerTurn.getNbRessource("glass")+"");
                dicLabel.get("gold").setText(this.playerTurn.getNbRessource("gold")+"");
            }
            case "science" -> {
                int value = this.playerTurn.getNbScience(type[1]);
                dicLabel.get(type[1]).setText(value+"");
            }
            case "war" -> {
                int value1 = this.playerTurn.getNbShildPeace();
                int value2 = this.playerTurn.getNbShildWar();
                dicLabel.get("peace").setText(value1+"");
                dicLabel.get("war").setText(value2+"");
            }
            case "politic" -> {
                int value = this.playerTurn.getNbPolilicPoint();
                dicLabel.get("winScience").setText(value+"");
            }
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


    private void majViewWonderPlayer() {
        // Positionnement de la nouvelle Wonder Players
        Wonders wonderPlayer = this.playerTurn.getWonder();
        // Recuperation de la Vbox Existante
        AnchorPane playerZone = this.playerZonesList.get(this.numPlayer);
        ObservableList<Node> playerZoneChildren = playerZone.getChildren();
        VBox wonderBox = (VBox) playerZoneChildren.get(2);
        // On vide la VBox Existante
        wonderBox.getChildren().removeAll(wonderBox.getChildren());
        // Generation de la VBox
        ObservableList <Node> wonderBoxElements = wonderPlayer.createImage().getChildren();
        // Ajout de la nouvelle VBox sur le plateau de jeu
        wonderBox.getChildren().addAll(wonderBoxElements);
    }

    private void majConflictTokenImage() {
        List<ConflictTokens> conflictTokensList = Game.option.getConflictTokensList();
        ObservableList<Node> imageViewListConflictToken = GridPaneConflictTokens.getChildren();
        for (int numConflictToken = 0; numConflictToken < conflictTokensList.size(); numConflictToken++) {
            // Recuperation du token et de son image
            ConflictTokens conflictTokens = conflictTokensList.get(numConflictToken);
            ImageView conflictTokensImgView = (ImageView) imageViewListConflictToken.get(numConflictToken);
            // Recuperation de l'image du type de conflit
            String pathImage = conflictTokens.getImagePathFace();
            conflictTokensImgView.setImage(ControlleurBase.setAnImage(pathImage));
        }
    }


    private void majConflictTokenCornPlayer() {
        for (Player player: Game.option.getPlayerList()) {
            // Recuperation des elements d'affichage
            ObservableList<Node> visualPlayer = this.playerZonesList.get(Game.option.getPlayerList().indexOf(player)).getChildren();
            GridPane gridPaneRessources = ((GridPane) visualPlayer.get(4));
            ObservableList<Node> gridPaneChild= gridPaneRessources.getChildren();
            // Transformation en dictionnaire avec key=n.getStyleClass().get(2) et value=Label
            HashMap<String, Label> dicLabel = new HashMap<String, Label>();
            for (Node n: gridPaneChild) {
                Label labelRessource = ((Label) n);
                String typeLabel = n.getStyleClass().get(2);
                dicLabel.put(typeLabel, labelRessource);
            }
            int value2 = player.getNbShildWar();
            dicLabel.get("war").setText(value2+"");
            dicLabel.get("winWar").setText(player.getmilitaryVictoryPoint()+"");
        }
    }

}