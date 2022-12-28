package com.isep.the7WondersArchitect;

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

        /*
        * Selection du nombre de joueurs de 2 à 7 joueurs
        * Pour chaque players on lui attribut une merveille à construire (selection ou hasard au choix)
        * La merveille est divisé en 5 parties
        *
        * Au début du jeu : chaque player affiche sa merveille
        * Pour chaque merveille : on a un tas de carte (nombre variant) lié à la merveille placé entre le player et celui de gauche avec la 1ere carte visible
        *
        * Au centre du jeu on a :
            * 1 Pioche de (60) carte neutre avec le dos "?"
            * 1 Pioche de défausse
            * 1 Tas avec les 15 jetons progrès (sont vert et avec un "?" dont les trois 1er de la pile sont visible
            * 1 Tas avec les (max 6) jetons conflits dont le nombre est determiné par le nombre de player
            * 1 Reserve de 28 jetons victoire militaire
            * 1 Pion chat
         */
    }


}
