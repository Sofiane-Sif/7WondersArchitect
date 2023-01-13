package com.isep.the7WondersArchitect;
import java.util.List;


public class Bot7Wonder {
    private GameState gameState;//état actuel du jeu qui possède une méthode 'play'

    public Bot7Wonder (GameState gameState) {
        this.gameState = gameState;
    }

    public void play() {//méthode qui boucle jusqu'à la fin de la partie

        //À chaque tour le bot récupère la liste des actions possibles et en choisit une en utilisant la méthode 'chooseBestAction' et l'applique à l'état du jeu
        while (!gameState.isGameOver()) {
            List<Action> possibleActions = gameState.getPossibleActions();
            Action action = chooseBestAction(possibleActions);
            gameState.applyAction(action);
        }
    }

    //méthode permettant de déterminer la meilleure action possible à chaque tour
    //ça dépend de notre stratégie et de nos objectifs pour le bot
    private Action chooseBestAction(List<Action> actions) {

    }

    //Deep learning trop compliqué pour les jeux de plateau stratégique
    //faisable avec plus de temps
    //meilleur choix possible avec du temps là actuellement pas de temps donc infaisable
    //maintenant ce que je peux faire c'est de faire la fonction 'chooseBestAction'
    //fonction permettant de faire le meilleur choix possible
    //Pour cela j'aurais besoin d'avoir l'état actuel du jeu
    //et la liste de toutes les actions

    //ia agressive / ia passive ...
    //random entre 1 2 ou 3

}


