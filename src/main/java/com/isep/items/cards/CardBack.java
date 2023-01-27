package com.isep.items.cards;

public enum CardBack {

	CentralDeck(true, "images/cards/card-back/card-back-question.png"), //
	
	Alexandrie(false, "images/cards/card-back/card-back-alexandrie.png"), //
	Halicarnasse(false, "images/cards/card-back/card-back-halicarnasse.png"),
	Ephese(false, "images/cards/card-back/card-back-ephese.png"), //
	Olympie(false, "images/cards/card-back/card-back-olympie.png"), //,
	Babylone(false, "images/cards/card-back/card-back-babylon.png"), //
	Rhodes(false, "images/cards/card-back/card-back-rhodes.png"), //
	Gizeh(false, "images/cards/card-back/card-back-gizeh.png"); //
	
	public final boolean centralDeck;
	// Image back
	public final String imgBackPath;
	
	private CardBack(boolean isCentralDeck, String imgBackPath) {
		this.centralDeck = isCentralDeck ;
		this.imgBackPath = imgBackPath;
	}

	/*
	* On va supprimer wonderDeck et garder centralDeck
	* if centralDeck then il faudra afficher la carte face caché
	* else montrer la carte face visible
	* On aura donc : centralDeck = (isCentralDeck) ? pathImage : null
	*
	*
	* Wonder contiendra l'explication du pouvoir de la wonder
	* ainsi que l'ensemble des images associer à cette wonders
	* Plus exactement le chemin du dossier qui contient toutes les images
	*
	 */
}
