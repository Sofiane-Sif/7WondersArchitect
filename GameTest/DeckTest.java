import com.isep.items.wonders.Wonders;
import com.isep.the7WondersArchitect.Game;
import com.isep.the7WondersArchitect.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PlayerTest {

    @Test
    void PlayerName()

    {
        Player so = new Player("Sofiane", 20);
        so.setCivilisationName("Ephese");
        assertEquals("Sofiane", so.getName());
        assertEquals(20, so.getAge());
        assertEquals("Ephese", so.getCivilisationName());
        so.createWonder();
        assertEquals(Wonders.Ephese, so.getWonder());
    }

    @Test
    void PlayerCards(){
        // Creation du Game
        Game.startNewGame();
        Game game = Game.option;
        Game.option.setNbPlayers(2);
        Game.option.setNumPlayer();
        game.addPlayer("So", 20, "Ephese");
        Player player = game.getPlayerList().get(0);
        player.createDeckPlayer();

        // Mise en plae du jeu
        game.createCat();
        game.settingConflictTokens();
        game.settingProgressTokens();
        game.settingCentralDeck();

        player.piocheCarte("Ephese");

        System.out.println(player.getTypeCardInIsHand());
        System.out.println(player.getCardInIsHandImgPath());
    }


}
