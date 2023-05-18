package com.pbo.game;

import com.badlogic.gdx.graphics.Texture;

public class Earth {
    private final int MAX_HP;
    private int HP;
    private final int DIAMETER;
    private final int X;
    private final int Y;
    private final Texture EARTH_IMG;

    public Earth(int HP, Texture EARTH_IMG, int DIAMETER, int x, int y) {
        this.HP = HP;
        this.MAX_HP = HP;
        this.EARTH_IMG = EARTH_IMG;
        this.DIAMETER = DIAMETER;
        this.X = x;
        this.Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getDIAMETER() {
        return DIAMETER;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getHP() {
        return HP;
    }

    public Texture getEARTH_IMG() {
        return EARTH_IMG;
    }

    public int getMAX_HP() {
        return MAX_HP;
    }
}
