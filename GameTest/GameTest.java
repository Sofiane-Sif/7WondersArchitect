import com.isep.items.conflictToken.ConflictTokens;
import com.isep.the7WondersArchitect.Player;
import com.isep.the7WondersArchitect.Game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {

    @Test

    void AddPlayerTest(){
        // Creation du Game
        Game.startNewGame();
        Game game = Game.option;
        Game.option.setNbPlayers(2);
        Game.option.setNumPlayer();
        game.addPlayer("So", 20, "Ephese");
        game.addPlayer("Paul", 21, "Alexandrie");
        Player player = game.getPlayerList().get(0);
        player.createDeckPlayer();

        // Mise en plae du jeu
        game.createCat();
        game.settingConflictTokens();
        game.settingProgressTokens();
        game.settingCentralDeck();

        // Pioche une cartes
        player.piocheCarte("Ephese");

        assertTrue(Game.option.getPlayerList().size() > 0);
        assertEquals(Game.option.getNbPlayers(), 2);
        assertEquals(Game.option.getNbPlayers(),2);
        assertEquals(player.getDeckPlayer().size(), 24);
    }

    @Test

    void TokenTest(){
        // Creation du Game
        Game.startNewGame();
        Game game = Game.option;
        Game.option.setNbPlayers(2);
        Game.option.setNumPlayer();
        game.addPlayer("So", 20, "Ephese");
        game.addPlayer("Paul", 21, "Alexandrie");
        Player player = game.getPlayerList().get(0);
        player.createDeckPlayer();


        assertEquals(3, game.settingConflictTokens().size()); //2 joueurs = 3 Conflict Tokens
        assertEquals(false,game.settingConflictTokens().get(0).IsInWar());
        assertEquals("images/tokens/token-conflict-peace.png",game.settingConflictTokens().get(0).getImagePathFace());
        game.settingConflictTokens().get(0).changeFace();
        assertEquals(true,game.settingConflictTokens().get(0).IsInWar());

        System.out.println(game.settingProgressTokens());
        assertTrue(game.settingProgressTokens().size() > 5);

    }
}
