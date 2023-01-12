package com.isep.items.wonders;

import com.isep.MainApplication;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.*;

public enum Wonders {

	Alexandrie("Alexandrie", "Alexandrie",
			"Prenez la premi�re carte d'une pioche au choix, n'importe o� sur la table, et posez-la devant vous",
			5,-1, 0, 0),

	Halicarnasse("Halicarnasse", "Halicarnasse", //
			"Prenez les 5 premi�res cartes de la pioche � votre gauche ou � votre droite, choisissez-en 1 et posez-la devant vous" //
			+ "M�langez-les cartes restantes dans leur pioche",
			4, 1, 2, 10),

	Ephese("Ephese", "Eph�se", //
			"Prenez la premi�re carte de la pioche centrale et posez-la devant vous",
			3, 1, 3, 15),

	Olympie("Olympie", "Olympie", //
			"Prenez la premi�re carte de la pioche � votre cauche et de celle � votre droite, et posez-les devant vous",
			4, 2, 2, 5),

	Babylone("Babylone", "Babylone", //
			"Choisissez 1 jeton Progr�s parmi les 4 disponibles, et posez-le devant vous",
			5, 0, 2, 2),

	Rhodes("Rhodes", "Rhodes", //
			"Ajoutez 1 Bouclier � votre total de Boucliers",
			4, 3,  2, 7),

	Gizeh("Gizeh", "Gizeh", //
			"Cette merveille n'a pas d'effet particulier, mais rapporte plus de points de victoire que les autres Merveilles",
			4, -1, 0, 0);

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
	private int levelCiv = 0;
	private File[] imgConstructionList;
	private File[] imgBuildList;
	private VBox vBoxWonderImages;

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





	public VBox createImage() throws IOException {

		// folderImagesPath of the wonder
		String path = ("images/wonders/" + this.name.toLowerCase());
		File fullPath = new File(Objects.requireNonNull(MainApplication.class.getResource(path)).toString());
		String modifiedPath = fullPath.toString().substring(5);
		// folderImages
		File nameDir  = new File(modifiedPath);
		// all filesPath in the folder
		File[] listImage = nameDir.listFiles();

		assert listImage != null;
		Arrays.sort(listImage, Comparator.comparing(File::getName));
		/*for (File i: listImage ) {System.out.println(i.getName());}
		System.out.println();
		System.out.println(Arrays.toString(listImage));*/

		// Divise les images en deux List - contruit et non construit
		int half = listImage.length / 2;
		this.imgConstructionList = Arrays.copyOfRange(listImage, 0, half);
		this.imgBuildList = Arrays.copyOfRange(listImage, half, listImage.length);
		// Renverse l'ordre des images
		Collections.reverse(Arrays.asList(this.imgConstructionList));
		Collections.reverse(Arrays.asList(this.imgBuildList));

		HBox hBox = null;
		this.vBoxWonderImages = new VBox();
		this.vBoxWonderImages.setAlignment(Pos.BOTTOM_CENTER);
		int diviseurZoom = 70;
		// Pour chaque images trouvé dans le dossier
		for (int numImg = 0; numImg < listImage.length/2; numImg++) {
			// Mise en place de l'ImageView
			Image image = new Image(String.valueOf(this.imgConstructionList[numImg]));
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
					this.vBoxWonderImages.getChildren().add(hBox);
				}
				// ajout de l'ImageView dans le VBox
				assert hBox != null;
				hBox.getChildren().add(imageView);
				imageView.setFitWidth(diviseurZoom/this.nbCase);
				//imageView.setFitHeight(diviseurZoom/this.nbCase);
			}
			else {
				// Sinon ajout de l'ImageView dans le HBox
				this.vBoxWonderImages.getChildren().add(imageView);
				int fit = diviseurZoom + this.padding * (this.nbCase-1);
				imageView.setFitWidth(fit);
				imageView.setFitHeight(fit);
			}
		}
		return this.vBoxWonderImages;
	}

	public int getLevelCiv() {return this.levelCiv;}

	public void getInfoConstruction() {
		/*
		* Pour chaque Wonders on a 5 pieces
		* l'ordre des nombres de ressources est : 2-2-3-3-4
		* Le type de resources : diff-equal-diff-equal-diff
		*
		* Le programme suivant doit normalement calculer ça (à partir du numero de l'etage de la wonder)
		*
		* if (this.levelCiv > 5) {GAME_OVER}
		*/
		int nbRessource = (this.levelCiv%2==0) ? 2+(this.levelCiv/2) : 2+(this.levelCiv/2)-1;
		boolean isEqualRessource = this.levelCiv % 2 == 0;
		System.out.println("Level : " + this.levelCiv);
		System.out.println("Condition suivante : " + nbRessource + " resources. diferentes ? " + isEqualRessource);
	}


}
