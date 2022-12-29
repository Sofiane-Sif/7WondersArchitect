package com.isep.the7WondersArchitect;

import com.isep.cards.*;
import com.isep.progressToken.ProgressToken;
import com.isep.progressToken.ProgressTokens;

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

    /* _______ */
    /* Getters */
    /* _______ */


    /* _______ */
    /* Setters */
    /* _______ */


    /* ______ */
    /* Adders */
    /* ______ */


    /* ______ */
    /* Methodes de la Class Game */
    /* ______ */

    public void settingUpGame() {
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

        List<CardDecks.CardTypeQuantity> CardDecksCivilisation = CardDecks.deckCardQuantities_Gizeh;
        CardBack cardBackCivilisation = CardBack.Gizeh;

        for (CardDecks.CardTypeQuantity c: CardDecksCivilisation) {

            // la carte constitué dans sa ensemble
            Card theCard = new Card(c.cardType, cardBackCivilisation);

            CardType ct =  theCard.front;
            CardBack cb = theCard.back;
            Wonder w = theCard.back.wonderDeck;

            System.out.println(
                    "Deck : deckCardQuantities_Extra \n"

                    + c.quantity +" - "+ ct + "\n  front=["

                    + ct.cardDisplayName +" - "+ ct.cardCategory +" - "+ ct.material +" - "
                    + ct.scienceCategory +" - "+ ct.shieldCount +" - "+ ct.cornCount +" - "
                    + ct.laurelCount +" - "+ ct.cat +" - "+ ct.imageResource
                    + "]"

                    + "\n   back=[" + cb.centralDeck + " - " + cb.wonderDeck
                    + "]"

                    + "\n           [" + w.displayName + " - " + w.frenchName + " - " + w.effectDescription
                    + "]"

            + "\n");
        }

        System.out.println("--------------------------------------------");

        /**
         * Fonctionnement de la gestion des jetons Progrès
         */

        for (ProgressToken token : ProgressTokens.TOKENS) {
            System.out.println(
                    "Jeton = " + token
                    + "\n   [" + token.displayName  +" - "+ token.frenchName + "\n"
                            + token.effectDescription  +"\n"+ token.imageResource
                    + "]"
            + "\n");
        }


    }


}
