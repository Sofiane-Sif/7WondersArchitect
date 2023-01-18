package com.isep.the7WondersArchitect;

import com.isep.items.cards.*;
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
    private final List<Card> materialCardList = new ArrayList<>();
    private final List<Card> scienceCardList = new ArrayList<>();
    private final List<Card> warCardList = new ArrayList<>();
    private final List<Card> politicCardList = new ArrayList<>();
    // Autre
    private final List<Card> progressTokenList = new ArrayList<>();
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

        // Ajout de la carte dans la bonne liste de carte
        switch (this.cardInIsHand.front.cardCategory) {
            case MaterialCard ->  this.materialCardList.add(this.cardInIsHand);
            case ProgressCard -> this.scienceCardList.add(this.cardInIsHand);
            case WarCard -> this.warCardList.add(this.cardInIsHand);
            case PoliticCard -> this.politicCardList.add(this.cardInIsHand);
        }

        // S'il y a encore des cartes dans le deck
        if ((cardListDeckChoose.size()>0)) {
            // Revoi la carte suivante pour remplacer l'affichage
            Card newCarte = cardListDeckChoose.get(0);
            return  (!newCarte.back.centralDeck) ?  newCarte.front.imageResource:  newCarte.back.imgBackPath;
        }
        // Sinon on affiche le dos de la carte
        if (!Objects.equals(nameCivSelect, "CentralDeck")) {return this.cardInIsHand.back.imgBackPath;}
        else {return "images/cards/card-back/card-back-none.png";}





    }

    public String getCardInIsHandImgPath() {
        // Recuperation de l'image de la carte
        String imgPath = this.cardInIsHand.front.imageResource;
        // Vide la main du Player
        this.cardInIsHand = null;
        // Retourne le path pour l'afficher dans la defausse
        return imgPath;

    }

    public String getTypeCardInIsHand() {return this.cardInIsHand.front.cardDisplayName;}

    public List<Card> getMaterialCardList() {return this.materialCardList;}
    public List<Card> getScienceCardList() {return this.scienceCardList;}
    public List<Card> getWarCardList() {return this.warCardList;}
    public List<Card> getPoliticCardList() {return this.politicCardList;}



    private int getNbValueCard(List<Card> lstCard, String scienceName) {
        int numRessource = 0;
        for (Card card: lstCard) {
            String[] type = card.front.cardDisplayName.split(":");
            if (Objects.equals(type[1], scienceName)) {numRessource++;}
        }
        return numRessource;
    }
    public int getNbRessource(String materialName){return this.getNbValueCard(this.materialCardList, materialName);}
    public int getNbScience(String scienceName){return this.getNbValueCard(this.scienceCardList, scienceName);}


    private int getNbShildType(boolean isCenturion){
        int numRessource = 0;
        for (Card card: this.warCardList) {
            String[] type = card.front.cardDisplayName.split(":");
            if (isCenturion == Objects.equals(type[1], "centurion")) {
                numRessource += card.front.shieldCount;
            }
        }
        return numRessource;
    }
    public int getNbShildPeace(){return this.getNbShildType(true);}
    public int getNbShildWar(){return this.getNbShildType(false);}


    public int getNbPolilicPoint(){
        int numRessource = 0;
        for (Card card: this.politicCardList) {
            String[] type = card.front.cardDisplayName.split(":");
            numRessource += card.front.laurelCount;
        }
        return numRessource;
    }

    public int getmilitaryVictoryPoint() {return this.militaryVictoryPoint;}



    public void usePowerCard() {


        // Lecture du type de la carte choisi
        String typeCard = this.getTypeCardInIsHand();
        String[] type = typeCard.split(":");
        //System.out.println("Carte pioché : " + this.cardInIsHand.front.cardCategory + " --> " + getTypeCardInIsHand());

        // En fonction du type de la carte : effectue des actions differentes
        switch (type[0]) {
            case "material" -> {
                this.wonderConstruction();
            }
            case "science" -> {
                //System.out.println("science : Non implementé");
            }
            case "war" -> {
                //System.out.println("war : Non implementé");
            }
            case "politic" -> {
                //System.out.println("politic : Non implementé");
            }


        }

    }


    private void wonderConstruction() {
        /*
        Quand pour un etage il y a plusieurs pieces, c'est dans l'ordre que l'on veut
         * Pour chaque pieces constructible
            *Si on a les ressources neccessaires :
                * On enleve les ressources utilisé
                * On contruit un etage
                * Il est possible que le pouvoir de la merveille peut etre activé
                * On recommence tant qu'une autre construction est possible
                * On affiche les ressources restantes -> GameController
                * on affiche le nouvelle etage de la wonder -> GameController
         */



        // Que faut t'il pour construire la pieces
        int nbRessourceNeed = (int) wonder.getInfoConstruction(wonder.countNbStepBuid()).get(0);
        boolean isEqualRessource = (boolean) wonder.getInfoConstruction(wonder.countNbStepBuid()).get(1);
        System.out.println("Condition suivante : " + nbRessourceNeed + " resources. diferentes ? " + isEqualRessource);

        // Recherche et prelevement du prix de la construction
        boolean canBuild = false;
        // nbRessource pareil

        if(isEqualRessource) {
         /*   // Nombre de pieces d'or
            int nbGold = this.getNbRessource("gold");
            // Pour chaque materiaux gold exclu
            List<Material> lstMat = new ArrayList<>(Arrays.stream(Material.values()).toList());
            lstMat.remove(lstMat.size() - 1);

            // Pour chaque list de materiaux, on regarde s'il y a nbRessourceNeed
            for (Material material: lstMat){
                int nbMat = this.getNbRessource(material.name().toLowerCase());
                // S'il y en  a le nombre necessaire avec le sac d'or
                if (nbMat + nbGold >= nbRessourceNeed) {
                    canBuild = true;
                    System.out.println("construction possible sans or");
                    // On remove le nombre de ressouce nessessaire  + le nombre d'or qui manque
                    break;
                }
            }*/
            System.out.println("Free for no long time!");
            canBuild = true;
        }
        // nbRessource differentes
        else {
            System.out.println("Free!");
            canBuild = true;
        }




        if (canBuild) {
            this.wonder.buildWonderLevel(this.wonder.countNbStepBuid());
        }



    }

}
