import com.isep.items.cards.Card;
import com.isep.the7WondersArchitect.Game;
import com.isep.the7WondersArchitect.Player;
import org.junit.jupiter.api.Test;

public class testCardListPlayer {

    @Test
    public void getListCardsPlayer() {
        // Creation du Game
        Game.startNewGame();
        Game game = Game.option;

        // Mise en plae du jeu
        game.settingCat();
        game.settingConflictTokens();
        game.settingProgressTokens();
        game.settingCentralDeck();

        // Creation des Player test
        game.addPlayer("Master", 20, "Alexandrie");
        game.addPlayer("Master", 25, "Halicarnasse");
        Player player = game.getPlayerList().get(0);
        player.createDeckPlayer();

        // Pioche 6 cartes
        player.piocheCarte("Alexandrie");
        player.piocheCarte("Alexandrie");
        player.piocheCarte("Alexandrie");
        player.piocheCarte("CentralDeck");
        player.piocheCarte("CentralDeck");
        player.piocheCarte("Alexandrie");
        player.piocheCarte("Alexandrie");
        player.piocheCarte("Alexandrie");
        player.piocheCarte("CentralDeck");
        player.piocheCarte("CentralDeck");

        // Affiche l'ensemble des cartes contenu dans les quatres List possible
        for (Card i: player.getMaterialCardList()) {System.out.println(i.front.cardDisplayName);}
        for (Card i: player.getProgressCardList()) {System.out.println(i.front.cardDisplayName);}
        for (Card i: player.getPoliticCardList()) {System.out.println(i.front.cardDisplayName);}
        for (Card i: player.getWarCardList()) {System.out.println(i.front.cardDisplayName);}
        System.out.println();

            /* ***** Affiche pour chaque deck les ressources à afficher ***** */
        // Material
        System.out.println("Mat_wood: "+ player.getNbRessource("wood"));
        System.out.println("Mat_paper: "+ player.getNbRessource("paper"));
        System.out.println("Mat_brick: "+ player.getNbRessource("brick"));
        System.out.println("Mat_stone: "+ player.getNbRessource("stone"));
        System.out.println("Mat_glass: "+ player.getNbRessource("glass"));
        System.out.println("Mat_gold: "+ player.getNbRessource("gold"));

        // Science
        System.out.println("Sci_law: "+ player.getNbScience("law"));
        System.out.println("Sci_mechanic: "+ player.getNbScience("mechanic"));
        System.out.println("Sci_architect: "+ player.getNbScience("architect"));

        // Shild
        System.out.println("ShP: "+ player.getNbShildPeace());
        System.out.println("ShW: "+ player.getNbShildWar());

        // Politique
        System.out.println("ScP: "+ player.getNbPolilicPoint());

        // War point
        System.out.println("WPs: "+ player.getmilitaryVictoryPoint());

    }
}
