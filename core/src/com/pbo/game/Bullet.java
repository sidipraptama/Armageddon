package com.pbo.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Bullet extends Rectangle {
    private double speed;
    private int damage;
    private Texture img;
    private double degree;
    private boolean pierceable = false; //default

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Texture getImg() {
        return img;
    }

    public void setImg(Texture img) {
        this.img = img;
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    public boolean isPierceable() {
        return pierceable;
    }

    public void setPierceable(boolean pierceable) {
        this.pierceable = pierceable;
    }
}
