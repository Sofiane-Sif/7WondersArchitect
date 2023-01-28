package com.isep.the7WondersArchitect;

import com.isep.items.cards.*;
import com.isep.items.cat.Cat;
import com.isep.items.wonders.Wonders;

import java.util.*;

public class Player {

    // Info player
    protected final String name;
    protected final int age;
    protected String civilisationName;
    // Possession Player
    protected Wonders wonder;
    protected final List<Card> deckPlayer = new ArrayList<>();
    protected Card cardInIsHand = null;
    // Ressources Player

    protected final List<Card> materialCardList = new ArrayList<>();
    protected final List<Card> scienceCardList = new ArrayList<>();
    protected final List<Card> warCardList = new ArrayList<>();
    protected final List<Card> politicCardList = new ArrayList<>();

    // Autre
    protected final List<Card> progressTokenList = new ArrayList<>();
    protected int militaryVictoryPoint = 0;
    protected Cat cat = null;


    public Player(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setCat(Cat cat) {this.cat=cat;}
    public boolean haveTheCat() {return cat != null;}

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
        try {this.cardInIsHand = cardListDeckChoose.get(0);}
        catch (Exception e) {
            System.out.println("\n"+this.name);
            System.out.println(this.civilisationName);
            System.out.println(this.cardInIsHand);
        }

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

    public void looseWarCorn() {
        // Supprime toutes les Cards War avec Corne
        List<Card> filteredCards = this.warCardList.stream().filter(card ->card.front.cornCount>0).toList();
        this.warCardList.removeAll(filteredCards);

    }
    public int getNbShildPeace(){return this.getNbShildType(true);}
    public int getNbShildWar(){return this.getNbShildType(false);}

    public int getTotNbShild() {return this.getNbShildPeace()+this.getNbShildWar();}


    public int getNbPolilicPoint(){
        int numRessource = 0;
        for (Card card: this.politicCardList) {
            String[] type = card.front.cardDisplayName.split(":");
            numRessource += card.front.laurelCount;
        }
        return numRessource;
    }

    public int getmilitaryVictoryPoint() {return this.militaryVictoryPoint;}
    public void addOneMilitaryVictoryPoint() {this.militaryVictoryPoint++;}




    public void usePowerCard() {
        // Lecture du type de la carte choisi
        String typeCard = this.getTypeCardInIsHand();
        String[] type = typeCard.split(":");
        //System.out.println("Carte pioché : " + this.cardInIsHand.front.cardCategory + " --> " + getTypeCardInIsHand());
        // En fonction du type de la carte : effectue des actions differentes
        switch (type[0]) {
            case "material" -> {this.wonderConstruction();}
            case "science" -> {//System.out.println("science : Non implementé");
            }
            case "war" -> {Game.option.addWarCorn(this.cardInIsHand.front.cornCount);}
            case "politic" -> {if (this.cardInIsHand.front.cat) {Game.option.assignCat(this);}}
        }
    }





    private void wonderConstruction() {
        /*
        Quand pour un etage il y a plusieurs pieces, c'est dans l'ordre que l'on veut -- NOT IMPLEMENT
         * Pour chaque pieces constructible -- NOT IMPLEMENT
            *Si on a les ressources neccessaires : -- OK
                * On enleve les ressources utilisé -- OK
                * On contruit un etage -- OK
                * Il est possible que le pouvoir de la merveille peut etre activé -- NOT IMPLEMENT
                * On recommence tant qu'une autre construction est possible -- NOT IMPLEMENT
                * On affiche les ressources restantes -> GameController -- OK
                * on affiche le nouvelle etage de la wonder -> GameController -- OK
         */
        // Que faut t'il pour construire la pieces
        List<?> lstInfo = wonder.getInfoConstruction(wonder.countNbStepBuid());
        int nbRessourceNeed = (int) lstInfo.get(0);
        boolean isEqualRessource = (boolean) lstInfo.get(1);

        // Affiche la condition et les ressources dispos
        //printOmplype("\nCondition suivante : " + nbRessourceNeed + " resources pareil ? " + isEqualRessource);
        //printOmplype("- Ressource base");
        //for (Card c: this.materialCardList){printOmplype(c.front.cardDisplayName+" - ", true);}
        //printOmplype("");

        // Recherche et prelevement du prix de la construction si ok
        boolean canBuild = false;

        // nbRessource pareil
        if(isEqualRessource) {
            // Nombre de pieces d'or
            int nbGold = this.getNbRessource("gold");
            // List de tous les materiaux gold exclu
            List<Material> lstMat = new ArrayList<>(Arrays.stream(Material.values()).toList());
            lstMat.remove(Material.Gold);
            // Pour chaque list de materiaux
            for (Material material: lstMat){
                int nbMat = this.getNbRessource(material.name().toLowerCase());
                // S'il y en  a le nombre necessaire gold compris
                if (nbMat + nbGold >= nbRessourceNeed) {
                    // On peut construire
                    canBuild = true;
                    //System.out.println("Build !");
                    // Supprime le nombre de ressources necessaires
                    List<Card> cardsToRemove = new ArrayList<>();
                    // Pour l'ensemble des carte de cette ressource
                    for (Card cardRess : this.materialCardList) {
                        String nomMat = material.name().toLowerCase();
                        String nomCard = cardRess.front.cardDisplayName.split(":")[1];
                        // S'il y a besoin de jeter des cartes et si la carte est la bonne ressource
                        if (nbRessourceNeed > 0 & Objects.equals(nomCard, nomMat)) {
                            // On la met dans la defause
                            cardsToRemove.add(cardRess);
                            // Moins une carte au compteur
                            nbRessourceNeed--;
                        }
                    }
                    // S'il manque des ressources c'est que le player a des Golds qu'il faut lui prendre
                    for (Card cardRess : this.materialCardList) {
                        // S'il y a besoin de jeter des cartes et si la carte est la bonne ressource
                        String nomCard = cardRess.front.cardDisplayName.split(":")[1];
                        if (nbRessourceNeed > 0 & Objects.equals(nomCard, "gold")) {
                            // On la met dans la defause
                            cardsToRemove.add(cardRess);
                            // Moins une carte au compteur
                            nbRessourceNeed--;
                        }
                    }
                    // Affiche les cartes à jeter
                    //System.out.println("- Ressources à supp");
                    //for (Card c: cardsToRemove){System.out.println(c.front.cardDisplayName+" - ");}
                    // Supprimer les cartes qui a permis la construction
                    for (Card c: cardsToRemove){this.materialCardList.remove(c);}
                    // Affiche les cartes restantant
                    //System.out.println("\n- Ressources restant");
                    //for (Card c: this.materialCardList){System.out.print(c.front.cardDisplayName+" - ");}
                    //System.out.println("_____ : " + this.materialCardList.size());
                    //System.out.println("\n");
                    break;
                }
            }
         }

        // nbRessource differentes
        else {
            // Pour chaque materiaux -> Compte le nombre de ressources differents dans materialCardList
            int numberOfDiffRessources = 0;
            for (Material material: Material.values()){
                int nbMat = this.getNbRessource(material.name().toLowerCase());
                if (nbMat>0) {numberOfDiffRessources++;}
            }
            // Si j'ai au minimum nbRessourceNeed differente dans materialCardList
            if (numberOfDiffRessources >= nbRessourceNeed) {
                // Alors on peut construire
                canBuild = true;
                // List de tous les materiaux
                List<Material> lstMat = new ArrayList<>(Arrays.stream(Material.values()).toList());
                // Pour chaque list de materiaux
                for (Material material: lstMat){
                    // Si besoin de payer des ressources
                    if (nbRessourceNeed > 0) {
                        // Recupereration de toutes les cartes du meme type
                        String nomMat = material.name().toLowerCase();
                        List<Card> filteredCards = this.materialCardList.stream().filter(card ->
                                Objects.equals(nomMat, card.front.cardDisplayName.split(":")[1])).toList();
                        if (filteredCards.size() > 0) {
                            // Recuperation de la first Card of the list
                            Card cardToDel = filteredCards.get(0);
                            // Suppression de la carte
                            this.materialCardList.remove(cardToDel);
                            // Moins une carte au compteur
                            nbRessourceNeed--;
                        }
                    }
                }
                // Affiche les cartes restantant
                //System.out.println("\n- Ressources restant");
                //for (Card c: this.materialCardList){System.out.print(c.front.cardDisplayName+" - ");}
                //System.out.println("_____ : " + this.materialCardList.size());
            }

        }
        if (canBuild) {
            this.wonder.buildWonderLevel(this.wonder.countNbStepBuid());
        }
    }

}
