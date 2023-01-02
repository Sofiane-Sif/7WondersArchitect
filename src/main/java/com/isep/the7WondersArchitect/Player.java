package com.isep.the7WondersArchitect;

import com.isep.domain.cards.Card;
import com.isep.domain.cards.CardBack;
import com.isep.domain.cards.CardDecks;
import com.isep.domain.cards.CardsCivilisation;
import com.isep.domain.wonders.Wonder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

    private String name;
    private int age;
    private String civilisationName;
    private Wonder wonderPlayer;
    private List<Card> deckPlayer = new ArrayList<>();

    public Player(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public void setCivilisationName(String civilisationName) {this.civilisationName=civilisationName;}



    public String getName() {return this.name;}
    public String getCivilisationName() {return this.civilisationName;}
    public Wonder getWonderPlayer() {return this.wonderPlayer;}
    public List<Card> getDeckPlayer() {return this.deckPlayer;}



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


    public Wonder createWonder() {
        this.wonderPlayer = Wonder.valueOf(this.civilisationName);
        return this.wonderPlayer;
    }

}
