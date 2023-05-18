package com.pbo.game;

public class AOEBullet extends Bullet {
    private int explosionWidth;
    private int explosionHeight;

    public int getExplosionWidth() {
        return explosionWidth;
    }

    public void setExplosionWidth(int explosionWidth) {
        this.explosionWidth = explosionWidth;
    }

    public int getExplosionHeight() {
        return explosionHeight;
    }

    public void setExplosionHeight(int explosionHeight) {
        this.explosionHeight = explosionHeight;
    }

    public boolean detectAsteroidinExplosion(Asteroid ast) {
        float xImpact = this.x + this.width / 2;
        float yImpact = this.y + this.height;
        boolean xTrue = false;
        boolean yTrue = false;
        //cek x didalam ruang explotion
        if ((ast.x >= xImpact - explosionWidth / 2.0 && ast.x <= xImpact + explosionWidth / 2.0) ||
                (ast.x + ast.width >= xImpact - explosionWidth / 2.0 && ast.x + ast.width <= xImpact + explosionWidth / 2.0)) {
            xTrue = true;
        }
        if ((ast.y >= yImpact - this.explosionHeight / 2.0 && ast.y <= yImpact + this.explosionHeight / 2.0) ||
                (ast.y + ast.height >= yImpact - this.explosionHeight / 2.0 && ast.y + ast.height <= yImpact + this.explosionHeight / 2.0)) {
            yTrue = true;
        }
        return (xTrue && yTrue);
    }
}
