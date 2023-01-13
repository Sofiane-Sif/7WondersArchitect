package com.isep.the7WondersArchitect;

import com.isep.items.cards.Card;
import com.isep.items.cards.CardBack;
import com.isep.items.cards.CardDecks;
import com.isep.items.cards.CardsCivilisation;
import com.isep.items.cat.Cat;
import com.isep.items.wonders.Wonders;

import java.util.*;

public class Player {

    // Info player
    private final String name;
    private final int age;
    private String civilisationName;
    // Possession Player
    private Wonders wonder;
    private final List<Card> deckPlayer = new ArrayList<>();
    private Card cardInIsHand = null;
    // Ressources Player
    private List<Card> materialCardList = new ArrayList<>();
    private List<Card> progressCardList = new ArrayList<>();
    private List<Card> warCardList = new ArrayList<>();
    private List<Card> politicCardList = new ArrayList<>();
    // Autre
    private List<Card> progressTokenList = new ArrayList<>();
    int militaryVictoryPoint = 0;
    private Cat cat = null;


    public Player(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setCat(Cat cat) {this.cat=cat;}
    public void looseCat(){this.cat=null;}

    public void setCivilisationName(String civilisationName) {this.civilisationName=civilisationName;}

    public String getName() {return this.name;}
    public String getCivilisationName() {return this.civilisationName;}
    public Wonders getWonder() {return this.wonder;}
    public List<Card> getDeckPlayer() {return this.deckPlayer;}

    public int getAge() {return this.age;}



    public List<Card> createDeckPlayer() {
        List<CardDecks.CardTypeQuantity> CardTypeList = CardsCivilisation.valueOf(this.civilisationName).lstCards;
        CardBack cardBack = CardBack.valueOf(this.civilisationName);
        // Constitution de la liste des cartes avec un front et un back
        // Boucle sur le nombre de type de cartes
        for (CardDecks.CardTypeQuantity cardFront: CardTypeList) {
            // Boucle sur le nombre de cartes pour ce type
            for (int num = 0; num < cardFront.quantity; num++) {
                this.deckPlayer.add(new Card(cardFront.cardType, cardBack));
            }
        }
        // trie du CardDecks
        Collections.shuffle(this.deckPlayer);
        // Return pour affichage image
        return this.deckPlayer;
    }


    public Wonders createWonder() {
        this.wonder = Wonders.valueOf(this.civilisationName);
        return this.wonder;
    }


    public String piocheCarte(String nameCivSelect) {
        // Recuperation du Carddeck
        List<Card> cardListDeckChoose = Game.option.getCentralDeck();
        for (Player player: Game.option.getPlayerList()) {
            if (Objects.equals(nameCivSelect, player.getCivilisationName())) {
                cardListDeckChoose = player.getDeckPlayer();
                break;
            }
        }
        // Pioche et recupere la permiere carte du deck
        this.cardInIsHand = cardListDeckChoose.get(0);
        // enleve la carte du deck
        cardListDeckChoose.remove(this.cardInIsHand);

        // S'il y a encore des cartes dans le deck
        if ((cardListDeckChoose.size()>0)) {
            // Revoi la carte suivante pour remplacer l'affichage
            Card newCarte = cardListDeckChoose.get(0);
            return  (!newCarte.back.centralDeck) ?  newCarte.front.imageResource:  newCarte.back.imgBackPath;
        }
        if (!Objects.equals(nameCivSelect, "CentralDeck")) {return this.cardInIsHand.back.imgBackPath;}
        else {return "images/cards/card-back/card-back-none.png";}




        /*
         * Ajouter card back sous les deckPlayer pour quand il y aura de l'annimation
         *OK Ajouter label nbCardsDeck pour chaque deckPlayer
         *OK Mettre a jour ces labels
         *
         *OK La fonction pour maj l'image et le label est seulement lié à l'interface graphique et doit doit etre ecrit dedant

        *OK Un fois :
            * la carte pioché
            * l'imgDack changé
            * Le label maj
        * Alors on peut utiliser son pouvoir
         */

    }


    public void usePowerCard() {

        System.out.println("Carte pioché : " + this.cardInIsHand.front.cardCategory + " -->"
        +this.cardInIsHand.front.cardDisplayName);

        // Ajout de la carte dans la bonne liste de carte
        switch (this.cardInIsHand.front.cardCategory) {
            case MaterialCard ->  this.materialCardList.add(this.cardInIsHand);
            case ProgressCard -> this.progressCardList.add(this.cardInIsHand);
            case WarCard -> this.warCardList.add(this.cardInIsHand);
            case PoliticCard -> this.politicCardList.add(this.cardInIsHand);
        }
        // La player n'a pluas la carte en main
        this.cardInIsHand = null;

        /*
        * Pour le pouvoir du chat : Creer fonction dans GameController pour deplacer le chat
        * Ajouter un socle image de ressources
        *
         */
    }


}
