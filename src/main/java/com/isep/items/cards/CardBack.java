package com.isep.items.cards;

public enum CardBack {

	CentralDeck(null, "images/cards/card-back/card-back-question.png"), //
	
	Alexandrie(WonderHold.Alexandrie, "images/cards/card-back/card-back-alexandrie.png"), //
	Halicarnasse(WonderHold.Halicarnasse, "images/cards/card-back/card-back-halicarnasse.png"),
	Ephese(WonderHold.Ephese, "images/cards/card-back/card-back-ephese.png"), //
	Olympie(WonderHold.Olympie, "images/cards/card-back/card-back-olympie.png"), //,
	Babylone(WonderHold.Babylone, "images/cards/card-back/card-back-babylon.png"), //
	Rhodes(WonderHold.Rhodes, "images/cards/card-back/card-back-rhodes.png"), //
	Gizeh(WonderHold.Gizeh, "images/cards/card-back/card-back-gizeh.png"); //
	
	public final boolean centralDeck;
	// set only when not centralDeck
	public final WonderHold wonderDeck;
	// Image back
	public final String imgBackPath;
	
	private CardBack(WonderHold wonderDeck, String imgBackPath) {
		this.centralDeck = (wonderDeck == null);
		this.wonderDeck = wonderDeck;
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
