package com.isep.the7WondersArchitect;

import com.isep.items.cards.*;
import com.isep.items.cat.Cat;
import com.isep.items.conflictToken.ConflictTokens;
import com.isep.items.progressToken.*;

import java.util.ArrayList;
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
    private boolean gameOver = false;
    private int numPlayerTurn = 0;

    /* _______ */
    /* Getters */
    /* _______ */
    public int getNumPlayerTurn() {return this.numPlayerTurn;}

    public int getProgressTokensListSize() {return this.progressTokensList.size();}
    public List<Player> getPlayerList() {return this.playerList;}
    public int getNbPlayers() {return this.nbPlayers;}
    public boolean isGameOver() {return this.gameOver;}

    public List<Card> getCentralDeck() {return this.centralDeck;}

    /* _______ */
    /* Setters */
    /* _______ */


    /* ______ */
    /* Adders */
    /* ______ */
    public void addPlayer(String name, int age, String civilisationName) {
        // Alexandrie, Halicarnasse, Ephese, Olympie, Babylone, Rhodes, Gizeh
        Player newPlayer = new Player(name, age);
        newPlayer.setCivilisationName(civilisationName);
        this.playerList.add(newPlayer);
    }


    /* ______ */
    /* Methodes de la Class Game */
    /* ______ */


    public void setNumPlayer() {this.numPlayerTurn++;}

    public void setNbPlayers(int nbPlayers) {this.nbPlayers = nbPlayers;}

    public String settingCat() {
        this.cat = new Cat();
        // Return pour affichage image
        return this.cat.getImagePathCat();
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




    public void setVictory() {this.gameOver=true;}






















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
            WonderHold w = theCard.back.wonderDeck;

            System.out.println(
                    "Deck : "+civilisationName+" \n"

                    + c.quantity +" - "+ ct + "\n  front=["

                    + ct.cardDisplayName +" - "+ ct.cardCategory +" - "+ ct.material +" - "
                    + ct.scienceCategory +" - "+ ct.shieldCount +" - "+ ct.cornCount +" - "
                    + ct.laurelCount +" - "+ ct.cat +" - "+ ct.imageResource
                    + "]"

                    + "\n   back=[" + cb.centralDeck + " - " + cb.wonderDeck
                    + "]"

                    + "\n           [" + (cb.wonderDeck!=null ? w.displayName + " - " + w.frenchName + " - " + w.effectDescription : "Pas de wonders")
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
