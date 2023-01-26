package com.isep.the7WondersArchitect;
import com.isep.items.cards.Card;
import com.isep.items.cards.CardDecks;
import com.isep.items.cards.CardType;
import com.isep.items.cat.Cat;
import com.isep.items.wonders.Wonders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Bot7Wonder extends Player {
    private Game gameState;//état actuel du jeu qui possède une méthode 'play'

    Card carteSelect;

    public Bot7Wonder (String name, int age) {
        super(name, age);
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
    private void chooseBestAction(List<Card> cad) {

        //si je n'ai pas de carte ressources dans mon deck
        if (super.materialCardList.size() - super.getNbRessource("gold") == 0){

            //si carte gold dans les pioches
            for (Card i : cad) { //pour tous les i contenu dans " List<Card> cad "
                String[] type = i.front.cardDisplayName.split(":");
                if (Objects.equals(type[1], "gold")){
                    //je récupère la carte dans mon deck
                    carteSelect = i;
                    return;
                }
            }

            //si carte grise dans la pioche
            for(Card i : cad) {//pour tous les i contenu dans " List<Card> cad "
                String[] type = i.front.cardDisplayName.split(":");
                if (Objects.equals(type[0], "material")){
                    //je récupère la carte dans mon deck
                    carteSelect = i;
                    return;
                }

            }
            //pioche carte deck milieu
            carteSelect = gameState.getCentralDeck().get(0);
            return;

        }

        //si step == ressources identiques
        else if ((boolean) (wonder.getInfoConstruction(wonder.countNbStepBuid()).get(1))){

        }

        //si step == ressources différentes
        else {
            if()
        }
    }

    public void readGame (List<Card> deckPlayer, List<Card> deckRightPlayer, List<Card> centraldeck){
        // recup the game
        this.gameState = Game.option;

        List<Card> listCardICanPiocher = new ArrayList<>();
        // recup ls cartes dispo
        listCardICanPiocher.add(deckPlayer.get(0));
        listCardICanPiocher.add(deckRightPlayer.get(0));
        if(!(super.cat == null)) {centraldeck.add(deckPlayer.get(0));}

        System.out.println(deckRightPlayer.get(0).front.cardCategory);

        chooseBestAction(listCardICanPiocher) ;
    }

    private bestChoice(){


        if (cartetype != CardType.valueOf("materrial"))
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


