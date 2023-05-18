package com.pbo.game;

import com.badlogic.gdx.graphics.Texture;

public class Weapon {
    private static double weapDegree;
    public final int WIDTH = 36;
    public final int HEIGHT = 72;
    public final int X = 50 + 505 / 2 - this.WIDTH / 2;
    public final int Y = 95;
    public final int ORIGIN_X = WIDTH / 2;
    public final int ORIGIN_Y = 0;
    private Texture weapImg;
    private boolean canShoot = true;
    private long coolDown;
    private long shootTime;

    public Weapon(Texture weapImg, long coolDown) {
        this.weapImg = weapImg;
        this.weapDegree = 0;
        this.coolDown = coolDown;
    }

    public double getWeapDegree() {
        return weapDegree;
    }

    public void setWeapDegree(double weapDegree) {
        this.weapDegree = weapDegree;
    }

    public Texture getWeapImg() {
        return weapImg;
    }

    public long getCoolDown() {
        return coolDown;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public long getShootTime() {
        return shootTime;
    }

    public void setShootTime(long shootTime) {
        this.shootTime = shootTime;
    }
}
