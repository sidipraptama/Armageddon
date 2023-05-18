package com.pbo.game;

import com.badlogic.gdx.graphics.Texture;

public class Battery {
    private final Texture BACK_IMG;
    private final Texture FRONT_IMG;
    private int frontX;
    private int frontY;
    private int backX;
    private int backY;

    public Battery(Texture BACK_IMG, Texture FRONT_IMG, int backX, int backY, int frontX, int frontY) {
        this.BACK_IMG = BACK_IMG;
        this.FRONT_IMG = FRONT_IMG;
        this.backX = backX;
        this.backY = backY;
        this.frontX = frontX;
        this.frontY = frontY;
    }

    public void setFrontX(int frontX) {
        this.frontX = frontX;
    }

    public Texture getBACK_IMG() {
        return BACK_IMG;
    }

    public Texture getFRONT_IMG() {
        return FRONT_IMG;
    }

    public int getFrontX() {
        return frontX;
    }

    public int getFrontY() {
        return frontY;
    }

    public int getBackX() {
        return backX;
    }

    public int getBackY() {
        return backY;
    }
}
