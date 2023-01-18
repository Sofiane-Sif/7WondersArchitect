package com.isep.items.wonders;

import com.isep.MainApplication;
import com.isep.controllers.ControlleurBase;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

public enum Wonders {

	Alexandrie("Alexandrie", "Alexandrie",
			"Prenez la premi�re carte d'une pioche au choix, n'importe o� sur la table, et posez-la devant vous",
			5,-1, 0, 0),

	Halicarnasse("Halicarnasse", "Halicarnasse", //
			"Prenez les 5 premi�res cartes de la pioche � votre gauche ou � votre droite, choisissez-en 1 et posez-la devant vous" //
			+ "M�langez-les cartes restantes dans leur pioche",
			4, 1, 2, 3),

	Ephese("Ephese", "Eph�se", //
			"Prenez la premi�re carte de la pioche centrale et posez-la devant vous",
			3, 1, 3, 10),

	Olympie("Olympie", "Olympie", //
			"Prenez la premi�re carte de la pioche � votre cauche et de celle � votre droite, et posez-les devant vous",
			4, 2, 2, 0),

	Babylone("Babylone", "Babylone", //
			"Choisissez 1 jeton Progr�s parmi les 4 disponibles, et posez-le devant vous",
			4, 0, 2, 5),

	Rhodes("Rhodes", "Rhodes", //
			"Ajoutez 1 Bouclier � votre total de Boucliers",
			4, 3,  2, 10),

	Gizeh("Gizeh", "Gizeh", //
			"Cette merveille n'a pas d'effet particulier, mais rapporte plus de points de victoire que les autres Merveilles",
			5, -1, 0, 0);

	// ------------------------------------------------------------------------

	// Parametres de base sur les Wonders
	private final String name;
	private final String frenchName;
	private final String effectDescription;
	// Dossier images
	private final String imageFolderPath;
	// Parametre pour afficher les wonders
	private final int height;
	private final Integer numMultiLine;
	private final Integer nbCase;
	private final Integer padding;

	// Autre parametres non input
	//private int levelCiv = 0;
	private final List<Boolean> levelCivByStep = Arrays.asList(false, false, false, false, false); // [E5, E4, E3, E2, E1]

	// ------------------------------------------------------------------------

	Wonders(String name, String frenchName, String effectDescription,
			int height, Integer numMultiLine, Integer nbCase, Integer padding) {
		this.name = name;
		this.frenchName = frenchName;
		this.effectDescription = effectDescription;
		this.imageFolderPath = "images/wonders" + this.name;
		this.height = height;
		this.numMultiLine = numMultiLine;
		this.nbCase = nbCase;
		this.padding = padding;
	}





	public VBox createImage() {
		List<String> imgConstructionList = new ArrayList<>();
		List<String> imgBuildList = new ArrayList<>();
		VBox vBoxWonderImages;
		// Va recuperer dans le dossier de la Wonders toutes ses images en deux listes distinctes
		String nameCiv = this.name.toLowerCase();
		String pathStart = "images/wonders/"+nameCiv+"/piece-";
		for (int i = 5; i >= 1; i--) {
			imgConstructionList.add(pathStart + "back-"+nameCiv+"-"+(i)+".png");
			imgBuildList.add(pathStart + "front-"+nameCiv+"-"+(i)+".png");
		}
		//for(String i: this.imgConstructionList) {System.out.println(i);}
		//for(String i: this.imgBuildList) {System.out.println(i);}
		//System.out.println(this.levelCiv);
		HBox hBox = null;
		vBoxWonderImages = new VBox();
		vBoxWonderImages.setAlignment(Pos.BOTTOM_CENTER);
		int diviseurZoom = 70;
		// Pour chaque images trouvé dans le dossier
		for (int numImg = 0; numImg < imgConstructionList.size(); numImg++) {
			// Etage construit ?
			//List<String> lstImgPath = (numImg < this.levelCiv) ? imgBuildList : imgConstructionList;
			List<String> lstImgPath = (this.levelCivByStep.get(numImg)) ? imgBuildList : imgConstructionList;
			// Mise en place de l'ImageView
			Image image = ControlleurBase.setAnImage(lstImgPath.get(numImg));
			ImageView imageView = new ImageView(image);
			// Redimention de l'iamge en conservant le ratio
			imageView.setPreserveRatio(true);
			// Si on est dans le multiEtage
			if (numImg >= this.numMultiLine & numImg < (this.numMultiLine + this.nbCase)) {
				// Si c'est le 1er bloc
				if (numImg == this.numMultiLine) {
					hBox = new HBox();
					hBox.setAlignment(Pos.BOTTOM_CENTER);
					hBox.setSpacing(this.padding);
					vBoxWonderImages.getChildren().add(hBox);
				}
				// ajout de l'ImageView dans le VBox
				assert hBox != null;
				hBox.getChildren().add(imageView);
				//imageView.setFitWidth(diviseurZoom/this.nbCase);
				imageView.setFitHeight(imageView.getLayoutBounds().getHeight()/4); // diviseurZoom/2
			}
			else {
				// Sinon ajout de l'ImageView dans le HBox
				vBoxWonderImages.getChildren().add(imageView);
				int fit = diviseurZoom + this.padding * (this.nbCase-1);
				//imageView.setFitWidth(fit/2);
				imageView.setFitHeight(imageView.getLayoutBounds().getHeight()/4); // fit/2.3
			}
		}
		return vBoxWonderImages;
	}


	public List<?> getInfoConstruction(int etage) {
		/*
		* Pour chaque Wonders on a 5 pieces
		* l'ordre des nombres de ressources est : 2-2-3-3-4
		* Le type de resources : diff-equal-diff-equal-diff
		*
		* Le programme suivant doit normalement calculer ça (à partir du numero de l'etage de la wonder)
		*
		* if (this.levelCiv > 5) {GAME_OVER}
		*/
		//int levelCiv = this.countNbStepBuid();
		int nbRessource = 2 + etage / 2;
		boolean isEqualRessource = etage % 2 != 0;
		return Arrays.asList(nbRessource, isEqualRessource);
	}



	public int countNbStepBuid() {return (int) this.levelCivByStep.stream().filter(Predicate.isEqual(true)).count();}

	public void buildWonderLevel(int levelBuild) {
		int nbStepBuild = (5-1) - (levelBuild) ;
		levelCivByStep.set(nbStepBuild, true);
	}
}
