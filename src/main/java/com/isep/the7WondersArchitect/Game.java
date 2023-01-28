package com.isep.the7WondersArchitect;

import com.isep.items.cards.*;
import com.isep.items.cat.Cat;
import com.isep.items.conflictToken.ConflictTokens;
import com.isep.items.progressToken.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {

    /* _______________________________________________________*/
    /* Transformation de la class Game en une Singleton Class */
    /* _______________________________________________________*/
    // Variable unique Game qui contiendra toutes les données du jeu peu importe la scene
    public static Game option;
    // L'instantiation de la classe Game devient impossible
    private Game() {}
    // Création de l'unique Game
    public static void startNewGame() {
        // A chaque fois qu''on retourne sur le menu principal, on remet les options du jeu à zero
        Game.option = null;
        Game.option = new Game();
    }



    /* _________________ */
    /* Variable de class */
    /* _________________ */
    public MusiquePlayer musiquePlayer = new MusiquePlayer();
    private Cat cat;
    private ArrayList<ConflictTokens> conflictTokensList = new ArrayList<>();
    private int nbPlayers = 3;
    private List<Player> playerList = new ArrayList<>();
    private List<ProgressToken> progressTokensList;
    private List<Card> centralDeck = new ArrayList<>();
    //private boolean gameOver = false;
    private int numPlayerTurn = 0;
    // Soit que des Player - Soit 1 Player et que des bots
    private boolean haveBot = false;

    /* _______ */
    /* Getters */
    /* _______ */
    public int getNumPlayerTurn() {return this.numPlayerTurn;}

    public int getProgressTokensListSize() {return this.progressTokensList.size();}

    public List<Player> getPlayerList() {return this.playerList;}
    public List<ConflictTokens> getConflictTokensList() {return this.conflictTokensList;}
    public int getNbPlayers() {return this.nbPlayers;}

    public List<Card> getCentralDeck() {return this.centralDeck;}

    public boolean isBotModActived() {return this.haveBot;}

    /* _______ */
    /* Setters */
    /* _______ */

    public void selectBotMod() {this.haveBot=true;}

    /* ______ */
    /* Adders */
    /* ______ */
    public void addPlayer(String name, int age, String civilisationName) {
        // Alexandrie, Halicarnasse, Ephese, Olympie, Babylone, Rhodes, Gizeh
        Player newPlayer = new Player(name, age);
        newPlayer.setCivilisationName(civilisationName);
        this.playerList.add(newPlayer);
    }

    public void addBot(String name, int age, String civilisationName) {
        // Alexandrie, Halicarnasse, Ephese, Olympie, Babylone, Rhodes, Gizeh
        Bot7Wonder newPlayer = new Bot7Wonder(name, age);
        newPlayer.setCivilisationName(civilisationName);
        this.playerList.add(newPlayer);
    }


    /* ______ */
    /* Methodes de la Class Game */
    /* ______ */


    public void setNumPlayer() {this.numPlayerTurn++;}

    public void setNbPlayers(int nbPlayers) {this.nbPlayers = nbPlayers;}

    public String createCat() {
        this.cat = new Cat();
        // Return pour affichage image
        return this.cat.getImagePathCat();
    }


    public void assignCat(Player playerCat) {
        for (Player player: this.playerList) {
            player.setCat(null);
        }
        playerCat.setCat(this.cat);
    }


    public void addWarCorn(int nbCorn) {
        // Retourne le nombre de jeton necessaire
        for (ConflictTokens conflictTokens: this.conflictTokensList) {
            if (!conflictTokens.IsInWar() & nbCorn > 0) {
                conflictTokens.changeFace();
                nbCorn--;
            }
        }
        // Si tous les jetons sont en mode war
        if (this.conflictTokensList.size()==this.getNbConfict()) {
            this.beginningOfTheBattle();
            this.worldInPeace();
        }
    }

    public void worldInPeace() {
        // Met tous les ConflictTokens en mode Peace
        for (ConflictTokens conflictTokens: this.conflictTokensList) {
            if (conflictTokens.IsInWar()) {conflictTokens.changeFace();}
        }
    }

    public int getNbConfict() {
        int nbConflit = 0;
        for (ConflictTokens conflictTokens : this.conflictTokensList){
            if (conflictTokens.IsInWar())  {nbConflit++;}
        }
        return nbConflit;
    }

    private void beginningOfTheBattle() {
        /*
         * Pour chaque Player
            * On regarde son nombre de bouclier
            * On le compare à ceux du Player à Droite
            * On le compare à ceux du Palyer à Gauche
            * Si nbBouclierPlayer> nbBouclier Adversaire
                * +1 militaryVictoryPoint
        * Toutes les cartes avec une corne vont à la défausse
         */

        // Pour chaque Player
        for (int numPlayer = 0; numPlayer < this.nbPlayers; numPlayer++) {
            Player player = this.playerList.get(numPlayer);
            // On regarde son nombre de bouclier
            int nbShild = player.getTotNbShild();
            // Ses voisons
            Player playerRight = this.playerList.get(Math.floorMod(numPlayer+1,this.nbPlayers));
            Player playerLeft = this.playerList.get(Math.floorMod(numPlayer-1,this.nbPlayers));
            // Leur bouclier
            int nbShildPlayerRight = playerRight.getTotNbShild();
            int nbShildPlayerLeft = playerLeft.getTotNbShild();
            // On attribut des victoirePoint s'il y a
            if (nbShild>nbShildPlayerRight) {player.addOneMilitaryVictoryPoint();}
            if (nbShild>nbShildPlayerLeft) {player.addOneMilitaryVictoryPoint();}
        }
        // Pour chaque Player
        for (int numPlayer = 0; numPlayer < this.nbPlayers; numPlayer++) {
            // Les shilds avec cornes sont supprimé
            this.playerList.get(numPlayer).looseWarCorn();
        }


    }

    public ArrayList<ConflictTokens> settingConflictTokens() {
        // Calcul du nombre de ConflictTokens
        int nbConflictTokens = switch (this.nbPlayers) {
            case 2, 3 -> 3;
            case 4 -> 4;
            case 5 -> 5;
            case 6, 7 -> 6;
            default -> 0;
        };
        // Creation du nombre de ConflictTokens calculé
        for (int num = 0; num < nbConflictTokens; num++) {
            this.conflictTokensList.add(new ConflictTokens());
        }
        // Return pour affichage image
        return this.conflictTokensList;
    }



    public List<ProgressToken> settingProgressTokens() {
        // Création de la liste des ProgressToken
        this.progressTokensList = ProgressTokens.TOKENS;
        // Return pour affichage image
        return this.progressTokensList;
    }






    public List<Card> settingCentralDeck() {
        String civilisationName = "CentralDeck";
        List<CardDecks.CardTypeQuantity> CardTypeList = CardsCivilisation.valueOf(civilisationName).lstCards;
        CardBack cardBack = CardBack.valueOf(civilisationName);
        // Constitution de la liste des cartes avec un front et un back
        // Boucle sur le nombre de type de cartes
        for (CardDecks.CardTypeQuantity cardFront: CardTypeList) {
            // Boucle sur le nombre de cartes pour ce type
            for (int num = 0; num < cardFront.quantity; num++) {
                this.centralDeck.add(new Card(cardFront.cardType, cardBack));
            }
        }
        // Return pour affichage image
        Collections.shuffle(this.centralDeck);
        return this.centralDeck;
    }

    public void definePlayerOrder(Player firstPlayer) {
        // Recupère le joueur le plus jeune et change l'ordre des joueurs
        List<Player> newList = new ArrayList<>();
        int indexFirstPlayer = this.playerList.indexOf(firstPlayer);
        for (int numPlayer = indexFirstPlayer; numPlayer < this.nbPlayers+indexFirstPlayer; numPlayer++) {
            newList.add(this.playerList.get(numPlayer % this.nbPlayers));
        }
        this.playerList = newList;
    }





    public boolean isGameOver() {
        for (Player player: this.playerList) {
            if (player.getWonder().countNbStepBuid()>=5) {
                System.out.println("\nGAME OVER\n"+player.getName()+"["+player.getCivilisationName()+"] is the winner !");
                return true;
            }
        }
        return false;
    }

    // Calcul le Player qui a le plsu de point : renvoi sont name et son score
    public List<?> getWinner() {
        // info Winner
        Player winner = new Player("...", -1);
        String winnerName = winner.getName();
        int winnerScore = 0;

        // Pour chaque player on calcul son score
        for (Player player: this.playerList) {
            int playerScore = 0;
            // Points WonderCiv
            // Point Cat
            if (player.haveTheCat()) {playerScore+=2;}
            // Points cartes bleu
            playerScore += player.getNbPolilicPoint();
            // Points conflits
            playerScore += player.getmilitaryVictoryPoint();
            // Point carte vert car progressToken non implementé
            playerScore += player.getScienceCardList().size()*2;

            // Si Player meilleur que le dernier
            if (playerScore > winnerScore) {
                winnerName = player.getName();
                winnerScore = playerScore;
            }
            // Affiche le player et son score
            System.out.println("\n"+player.getName()+"["+player.getCivilisationName() + "] : "+winnerScore+" points");
        }
        // return info winner
        System.out.println("\n"+winnerName+"["+winner.getCivilisationName() + "] is the Winner with : "+winnerScore+" points");
        return Arrays.asList(winnerName, winnerScore);
    }





















    public void printsettingUpGame() {
        System.out.println("Préparation du jeu");

        /**
         * Explication de la mise en place du jeu
         */
        /*
        * Selection du nombre de joueurs de 2 à 7 joueurs
        * Pour chaque players on lui attribut une merveille à construire (selection ou hasard au choix)
        * La merveille est divisé en 5 parties
        *
        * Au début du jeu : chaque player affiche sa merveille
        * Pour chaque merveille : on a un tas de carte (nombre variant) lié à la merveille placé entre le player et celui de gauche avec la 1ere carte visible
        *
        * Au centre du jeu on a :
            * 1 Pioche de (60) cartes neutres avec le dos "?"
            * 1 Pioche de défausse
            * 1 Tas avec les 15 jetons progrès (sont vert et avec un "?" dont les trois 1ers de la pile sont visible
            * 1 Tas avec les (max 6) jetons conflits dont le nombre est determiné par le nombre de players
            * 1 Reserve de 28 jetons victoires militaires
            * 1 Pion chat
            *
            */


        /**
         * Explication de la gestion des cartes
         */

        /*
            *
            * Ensemble des cartes - > les cartes d'une merveille - > le type de la carte ->
                * pour chaque carte on a 9 variables :
                    * String cardDisplayName - CardCategory cardCategory --// for all
                    * Material material --// only when cardCategory==Material -- type de materiaux
                    * ScienceCategory scienceCategory --// only when cardCategory==Science -- type de carte verte
                    * int shieldCount - int cornCount --// only when cardCategory==War -- nb boucliers et nb cornes
                    * int laurelCount - boolean cat --// only when cardCategory==Politic -- nb PV et si pouvoir du chat présent
                    * String imageResource --// for all -- image de la carte
                    *
                * CardDecks -> CardTypeQuantity -> CardType :
                * CardCategory, Material, ScienceCategory // ne contiennent rien
                *
            * <--> Card(CardType, CardBack)
            *
                * CardBack :
                    * boolean centralDeck
                    * Wonder wonderDeck :
                        *   String displayName - String frenchName - String effectDescription
          *
          * Pour Wonder j'ai pas encors compris à quoi sert la variable effectDescription
          * C'est le descriptif des pouvoirs de chaque civilisation mais pourquoi le mettre sur chaque carte et pas seulement sur le plateau de merveille ?
          * Et au final on s'en fou de l'appartenance d'une carte à une merveille ainsi que l'image de dos d'une carte associé à une wonder
          *
         */


        // CentralDeck, Alexandrie, Halicarnasse, Ephese, Olympie, Babylone, Rhodes, Gizeh
        String civilisationName = "CentralDeck";
        List<CardDecks.CardTypeQuantity> CardDecksCivilisation = CardsCivilisation.valueOf(civilisationName).lstCards;
        CardBack cardBackCivilisation = CardBack.valueOf(civilisationName);



        for (CardDecks.CardTypeQuantity c: CardDecksCivilisation) {

            // la carte constitué dans sa ensemble
            Card theCard = new Card(c.cardType, cardBackCivilisation);

            CardType ct =  theCard.front;
            CardBack cb = theCard.back;

            System.out.println(
                    "Deck : "+civilisationName+" \n"

                    + c.quantity +" - "+ ct + "\n  front=["

                    + ct.cardDisplayName +" - "+ ct.cardCategory +" - "+ ct.material +" - "
                    + ct.scienceCategory +" - "+ ct.shieldCount +" - "+ ct.cornCount +" - "
                    + ct.laurelCount +" - "+ ct.cat +" - "+ ct.imageResource
                    + "]"

                    + "\n   back=[" + cb.centralDeck
                    + "]"

                    + "]"

            + "\n");
        }
/*
        System.out.println("--------------------------------------------");

        /**
         * Fonctionnement de la gestion des jetons Progrès
         */
/*
        for (ProgressToken token : ProgressTokens.TOKENS) {
            System.out.println(
                    "Jeton = " + token
                    + "\n   [" + token.displayName  +" - "+ token.frenchName + "\n"
                            + token.effectDescription  +"\n"+ token.imageResource
                    + "]"
            + "\n");
        }


        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");

        /**
         * Ensemble des Wonders
         */
/*
        // Alexandrie, Halicarnasse, Ephese, Olympie, Babylone, Rhodes, Gizeh
        civilisationName = "Olympie";
        Wonder myWonder = Wonder.valueOf(civilisationName);

        System.out.println(myWonder.displayName +" - "+ myWonder.frenchName +" -\n"+ myWonder.effectDescription +"\n"+ myWonder.imagePath);

*/
    }






}
