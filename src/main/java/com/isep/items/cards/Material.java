package com.isep.items.cards;

public enum Material {
	Wood("icon-material-wood.png"),
	Paper("icon-material-paper.png"),
	Brick("icon-material-brick.png"),
	Stone("icon-material-stone.png"),
	Glass("icon-material-glass.png"),
	Gold("icon-material-gold.png");


	public final String pathImg;

	Material(String pathImg) {
		this.pathImg = pathImg;

	}
}
