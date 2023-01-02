package com.isep.domain.conflictToken;



public class ConflictTokens {

    private final String imagePathWarFace = "images/tokens/token-conflict-war.png";
    private final String imagePathPeaceFace = "images/tokens/token-conflict-peace.png";

    private boolean isWar = false;
    private String imagePathConflictTokensFace = imagePathPeaceFace;;


    public void changeFace() {
        this.isWar = !isWar;
        this.imagePathConflictTokensFace = isWar ? imagePathWarFace : imagePathPeaceFace;
    }

    public boolean getIsWar() {return this.isWar;}
    public String getImagePathFace() {return imagePathConflictTokensFace;}




}
