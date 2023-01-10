package com.isep.items.cards;

import java.util.List;

public enum CardsCivilisation {

    CentralDeck(CardDecks.deckCardQuantities_Extra), //

    Alexandrie(CardDecks.deckCardQuantities_Alexandrie), //
    Halicarnasse(CardDecks.deckCardQuantities_Halicarnasse),
    Ephese(CardDecks.deckCardQuantities_Ephese), //
    Olympie(CardDecks.deckCardQuantities_Olympie), //,
    Babylone(CardDecks.deckCardQuantities_Babylon), //
    Rhodes(CardDecks.deckCardQuantities_Rhodes), //
    Gizeh(CardDecks.deckCardQuantities_Gizeh); //


    public final List<CardDecks.CardTypeQuantity> lstCards;

    private CardsCivilisation(List<CardDecks.CardTypeQuantity> lstCards) {
        this.lstCards = lstCards;
    }

}

