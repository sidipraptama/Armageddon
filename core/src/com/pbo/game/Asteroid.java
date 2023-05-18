package com.pbo.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Asteroid extends Rectangle {
    private int damage;
    private Texture img;
    private int hp;
    private double dropSpeed;
    private int point;
    private int targetX;
    private int targetY;
    final double BIG = 40;
    final double SMALL = 30;
    private boolean hitPierce = true;
    private boolean hitSplash = true;

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public double getDropSpeed() {
        return dropSpeed;
    }

    public void setDropSpeed(double dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isHitPierce() {
        return hitPierce;
    }

    public void setHitPierce(boolean hitPierce) {
        this.hitPierce = hitPierce;
    }

    public boolean isHitSplash() {
        return hitSplash;
    }

    public void setHitSplash(boolean hitSplash) {
        this.hitSplash = hitSplash;
    }
}
