package com.isep.controllers;

import com.isep.the7WondersArchitect.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class MainController extends ControlleurBase {


    @FXML
    private Slider sliderNbPlayers;

    @FXML
    public void initialize() {
        // Stop la musique est en cours
        try {Game.option.musiquePlayer.stopMedia();
        } catch (Exception ignored) {}
        // Création de la partie de jeu
        Game.startNewGame();
        // Joue une nouvelle musique
        Game.option.musiquePlayer.playMedia();
    }



    @FXML
    protected void startGame() {
        // Récureration du nombre de heros pour la partie
        int nbHeroesChoose = (int) sliderNbPlayers.getValue();
        Game.option.setNbPlayers(nbHeroesChoose);
        // Chargement de la nouvelle scene
        super.loadPage("playerCreation");
    }


    @FXML
    protected void startGameTest() {
        // Récureration du nombre de heros pour la partie
        int nbHeroesChoose = (int) sliderNbPlayers.getValue();
        Game.option.setNbPlayers(nbHeroesChoose);

        // Alexandrie, Babylone, Gizeh, Ephese, Halicarnasse, Olympie, Rhodes
        Game.option.setNbPlayers(3);
        Game.option.addPlayer("Admin-1", 20, "Ephese");
        Game.option.addPlayer("Admin-2", 19, "Rhodes");
        Game.option.addPlayer("Admin-3", 60, "Alexandrie");

        /*Game.option.addPlayer("Jon", 45, "Halicarnasse");
        Game.option.addPlayer("Brutus", 30, "Olympie");
        Game.option.addPlayer("Apollo", 70, "Babylone");
        Game.option.addPlayer("Astride", 42, "Gizeh");*/

        // Chargement de la nouvelle scene
        super.loadPage("game");
    }

    @FXML
    protected void exitGame() {((Stage) stage.getScene().getWindow()).close();}

}