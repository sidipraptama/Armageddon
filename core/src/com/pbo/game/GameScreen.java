package com.pbo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {
    private Armageddon game;
    private Array<Asteroid> asteroids;
    private Array<Bullet> bullets;
    private Earth earth;
    private Texture background;
    private Texture space;
    private BitmapFont bitmapFontGame;
    private BitmapFont bitmapFontBig;
    private Battery battery;
    private Array<Weapon> weapons;
    private OrthographicCamera camera;
    private int currentWeapNum;
    private Long lastUpSpeed = TimeUtils.nanoTime();
    private Long lastDropTime;
    private final Music gameMusic;
    private Sound gameOver;
    private Sound shootSound;
    private Sound boomAsteroid;
    private Sound boomAOE;
    private Sound boomEarth;
    private long spawnSpeed = 2000000000L;
    private Texture explosionAsteroid, explosionAOE, explosionEarth;

    public GameScreen(Armageddon game) {
        this.game = game;
        bitmapFontBig = new BitmapFont();
        bitmapFontBig.setColor(Color.BLACK);
        bitmapFontBig.getData().setScale(4, 4);

        // transisi game over
        gameOver = Gdx.audio.newSound(Gdx.files.internal("gameOver.wav"));

        // background gameScreen
        background = new Texture(Gdx.files.internal("gameScreen.png"));

        // bgm and sfx (music and sound)
        shootSound = Gdx.audio.newSound(Gdx.files.internal("weapon1.wav"));
        boomAsteroid = Gdx.audio.newSound(Gdx.files.internal("boomAsteroid.wav"));
        boomAOE = Gdx.audio.newSound(Gdx.files.internal("boomAOE.wav"));
        boomEarth = Gdx.audio.newSound(Gdx.files.internal("boomEarth.wav"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("GameScreen_BGM.mp3"));
        gameMusic.setVolume(0.5f);
        gameMusic.setLooping(true);
        gameMusic.play();

        // frame hijau (bar HP)
        battery = new Battery(new Texture(Gdx.files.internal("backBattery.png")), new Texture(Gdx.files.internal("frontBattery.png")), 595, 45, 600, 45);
        // bitmap khusus gameScreen
        bitmapFontGame = new BitmapFont();
        bitmapFontGame.getData().setScale(2, 2);
        bitmapFontGame.setColor(Color.BLACK);

        // menangambil weapon
        currentWeapNum = 0;

        // array jenis weapon
        weapons = new Array<>();
        weapons.add(new Weapon(new Texture(Gdx.files.internal("weapon1.png")), 650000000L));
        weapons.add(new Weapon(new Texture(Gdx.files.internal("weapon2.png")), 1500000000L));
        weapons.add(new Weapon(new Texture(Gdx.files.internal("weapon3.png")), 5000000000L));

        // asteroids
        asteroids = new Array<>();
        spawnAsteroid();

        // bullets
        bullets = new Array<>();

        // explosion
        explosionAsteroid = new Texture(Gdx.files.internal("explosionAsteroid.png"));
        explosionAOE = new Texture(Gdx.files.internal("explosionAOE.png"));
        explosionEarth = new Texture(Gdx.files.internal("explosionEarth.png"));

        // set camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.widthScreen, game.heightScreen);

        // background space
        space = new Texture(Gdx.files.internal("spaceBackGround.jpg"));

        // earth
        earth = new Earth(100, new Texture(Gdx.files.internal("earth.png")), 1100, 300 - 1100 / 2, -320 - 1100 / 2);
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        // background space
        game.batch.draw(space, 0, 0);

        // earth
        game.batch.draw(earth.getEARTH_IMG(), earth.getX(), earth.getY(), earth.getDIAMETER(), earth.getDIAMETER());        // weapon (pakai sprite)

        // battery (bar HP)
        game.batch.draw(battery.getBACK_IMG(), battery.getBackX(), battery.getBackY());
        game.batch.draw(battery.getFRONT_IMG(), battery.getFrontX(), battery.getFrontY());
        bitmapFontGame.draw(game.batch, String.valueOf(earth.getHP()), 620, 90);

        // weapon
        Sprite spriteWeap = new Sprite(weapons.get(currentWeapNum).getWeapImg());
        spriteWeap.setOrigin(weapons.get(currentWeapNum).ORIGIN_X, weapons.get(currentWeapNum).ORIGIN_Y);
        spriteWeap.setRotation((float) weapons.get(currentWeapNum).getWeapDegree());
        spriteWeap.setPosition(weapons.get(currentWeapNum).X, weapons.get(currentWeapNum).Y);
        spriteWeap.setSize(weapons.get(currentWeapNum).WIDTH, weapons.get(currentWeapNum).HEIGHT);
        spriteWeap.draw(game.batch);

        // for loop draw asteroid
        for (Asteroid i : asteroids) {
            game.batch.draw(i.getImg(), i.x, i.y, i.width, i.height);
        }

        // bullet
        for (Bullet i : bullets) {
            game.batch.draw(i.getImg(), i.x, i.y, i.width, i.height);
        }

        // frame
        game.batch.draw(background, 0, 0, 1000, 800);

        // bitmap leaderboard
        bitmapFontGame.draw(game.batch, game.getHighScore(0), 630, 485);
        bitmapFontGame.draw(game.batch, game.getHighScore(1), 690, 390);
        bitmapFontGame.draw(game.batch, game.getHighScore(2), 630, 295);

        // current score
        bitmapFontBig.draw(game.batch, String.valueOf(game.currentPlayer.getScore()), 750, 710);

        //logic meteor and earth
        for (Iterator<Asteroid> iter = asteroids.iterator(); iter.hasNext(); ) {
            Asteroid asteroid = iter.next();
            asteroidFall(asteroid);
            if (asteroid.y <= 90) {
                boomEarth.play();
                game.batch.draw(explosionEarth, asteroid.x, asteroid.y);
                iter.remove();
                earth.setHP(earth.getHP() - asteroid.getDamage());
                battery.setFrontX(battery.getFrontX() + 3 / 2 + asteroid.getDamage() * (350 / earth.getMAX_HP()));
                if (earth.getHP() <= 0) {
                    gameOver.play();
                    earth.setHP(0);
                    game.setScreen(new EndingScreen(game));
                    dispose();
                }
            }
        }

        //logic bullet dan asteroid
        for (Iterator<Bullet> iterBul = bullets.iterator(); iterBul.hasNext(); ) {
            Bullet bullet = iterBul.next();
            bulletMove(bullet);
            //keluar batas delete bullet
            if (bullet.x < 40 || bullet.x > 550 || bullet.y > 750) {
                iterBul.remove();
            }
            for (Iterator<Asteroid> iterAst = asteroids.iterator(); iterAst.hasNext(); ) {
                Asteroid asteroid = iterAst.next();
                //untuk bullet yang AOE
                if (bullet instanceof AOEBullet && bullet.overlaps(asteroid)) {
                    asteroid.setHp(asteroid.getHp() - bullet.getDamage());
                    inflictSplash(((AOEBullet) bullet), asteroids);
                    game.batch.draw(explosionAOE, asteroid.x - explosionAOE.getWidth() / 2, asteroid.y - explosionAOE.getHeight() / 2);
                    boomAOE.play();
                    iterBul.remove();
                    game.batch.draw(explosionAsteroid, asteroid.x, asteroid.y);
                    break;
                }
                //untuk bullet yang pierceable
                else if (bullet.isPierceable() && bullet.overlaps(asteroid)) {
                    asteroid.setHp(asteroid.getHp() - bullet.getDamage());
                    if (asteroid.getHp() <= 0) {
                        game.currentPlayer.setScore(game.currentPlayer.getScore() + asteroid.getPoint());
                        boomAsteroid.play();
                        iterAst.remove();
                        game.batch.draw(explosionAsteroid, asteroid.x, asteroid.y);
                    }
                }
                //untuk bullet yang normal
                else if (bullet.overlaps(asteroid)) {
                    asteroid.setHp(asteroid.getHp() - bullet.getDamage());
                    iterBul.remove();
                    if (asteroid.getHp() <= 0) {
                        game.currentPlayer.setScore(game.currentPlayer.getScore() + asteroid.getPoint());
                        boomAsteroid.play();
                        iterAst.remove();
                        game.batch.draw(explosionAsteroid, asteroid.x, asteroid.y);
                    }
                    break;
                }
            }
        }

        //set up phase spawn time asteroid
        if (TimeUtils.timeSinceNanos(lastUpSpeed) > 1000000000) {
            spawnSpeed -= 500000;
            if (spawnSpeed <= 1000000000L) spawnSpeed = 1000000000L;//batas tercepat spawn
            lastUpSpeed = TimeUtils.nanoTime();
        }

        //spawn asteroid
        if (TimeUtils.nanoTime() - lastDropTime > spawnSpeed) spawnAsteroid();

        //set senjata bisa tembak lagi
        if (TimeUtils.nanoTime() - weapons.get(currentWeapNum).getShootTime() >= weapons.get(currentWeapNum).getCoolDown()) {
            weapons.get(currentWeapNum).setCanShoot(true); //jika time sudah melebihi cooldown maka set bisa tembak lagi
        }

        // controller weapon
        if (Gdx.input.isKeyPressed(Keys.D)) {
            weapons.get(currentWeapNum).setWeapDegree(weapons.get(currentWeapNum).getWeapDegree() - 100 * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Keys.A)) {
            weapons.get(currentWeapNum).setWeapDegree(weapons.get(currentWeapNum).getWeapDegree() + 100 * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
            currentWeapNum = 0;
            shootSound = Gdx.audio.newSound(Gdx.files.internal("weapon1.wav"));
        }
        if (Gdx.input.isKeyPressed(Keys.NUM_2) && earth.getHP() <= 60) {
            currentWeapNum = 1;
            shootSound = Gdx.audio.newSound(Gdx.files.internal("weapon2.wav"));
        }
        if (Gdx.input.isKeyPressed(Keys.NUM_3) && earth.getHP() <= 30) {
            currentWeapNum = 2;
            shootSound = Gdx.audio.newSound(Gdx.files.internal("weapon3.wav"));
        }
        if (Gdx.input.isKeyPressed(Keys.SPACE) && weapons.get(currentWeapNum).isCanShoot()) {
            shoot(currentWeapNum);
            shootSound.play();
        }
        if (weapons.get(currentWeapNum).getWeapDegree() < -80) {
            weapons.get(currentWeapNum).setWeapDegree(-80);
        }
        if (weapons.get(currentWeapNum).getWeapDegree() > 80) {
            weapons.get(currentWeapNum).setWeapDegree(80);
        }
        game.batch.end();
    }

    private void spawnAsteroid() {
        ArrayList<Texture> arrayImg = new ArrayList<>();
        arrayImg.add(new Texture(Gdx.files.internal("asteroid1.png")));
        arrayImg.add(new Texture(Gdx.files.internal("asteroid2.png")));
        arrayImg.add(new Texture(Gdx.files.internal("asteroid3.png")));
        arrayImg.add(new Texture(Gdx.files.internal("asteroid4.png")));
        arrayImg.add(new Texture(Gdx.files.internal("asteroid5.png")));
        arrayImg.add(new Texture(Gdx.files.internal("asteroid6.png")));
        arrayImg.add(new Texture(Gdx.files.internal("asteroid7.png")));
        arrayImg.add(new Texture(Gdx.files.internal("asteroid8.png")));

        Asteroid asteroid = new Asteroid();
        asteroid.x = MathUtils.random(50, 550 - 64);
        asteroid.y = 750;
        if (MathUtils.random(0, 1) == 0) {
            asteroid.width = (float) asteroid.BIG;
            asteroid.height = (float) asteroid.BIG;
            asteroid.setImg(arrayImg.get(MathUtils.random(0, 7)));
            asteroid.setDropSpeed(60 * Gdx.graphics.getDeltaTime());
            asteroid.setHp(5);
            asteroid.setDamage(3);
            asteroid.setPoint(5);
        } else {
            asteroid.width = (float) asteroid.SMALL;
            asteroid.height = (float) asteroid.SMALL;
            asteroid.setImg(arrayImg.get(MathUtils.random(0, 7)));
            asteroid.setDropSpeed(120 * Gdx.graphics.getDeltaTime());
            asteroid.setHp(2);
            asteroid.setDamage(1);
            asteroid.setPoint(2);
        }
        asteroid.setTargetX(MathUtils.random(80, 500));
        asteroid.setTargetY(MathUtils.random(0));
        asteroids.add(asteroid);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void inflictSplash(AOEBullet bullet, Array<Asteroid> asteroids) {
        //lokasi saat impact
        for (Iterator<Asteroid> astIter = asteroids.iterator(); astIter.hasNext(); ) {
            Asteroid ast = astIter.next();
            if (bullet.detectAsteroidinExplosion(ast)) {
                ast.setHp(ast.getHp() - bullet.getDamage());
            }
            if (ast.getHp() <= 0) {
                game.currentPlayer.setScore(game.currentPlayer.getScore() + ast.getPoint());
                astIter.remove();
            }
        }
    }

    // shoot/spawn bullet
    private void shoot(int currentWeapNum) {
        Weapon weap = weapons.get(currentWeapNum);
        double initX = Math.sin(Math.toRadians(0 - weap.getWeapDegree())) * weap.HEIGHT + weap.X;
        double initY = Math.cos(Math.toRadians(0 - weap.getWeapDegree())) * weap.HEIGHT + weap.Y;
        weap.setShootTime(TimeUtils.nanoTime());//mengupdate nanotime stiap shoot
        weap.setCanShoot(false);//update supaya ga bisa tembak lagi sebelum cd selesai
        switch (currentWeapNum) {
            case 0:
                Bullet normBullet = new Bullet();
                normBullet.x = (float) initX + 7.5f;
                normBullet.y = (float) initY;
                normBullet.height = 20;
                normBullet.width = 20;
                normBullet.setImg(new Texture(Gdx.files.internal("bullet1.png")));
                normBullet.setDamage(2);
                normBullet.setSpeed(500 * Gdx.graphics.getDeltaTime());
                normBullet.setDegree(weapons.get(currentWeapNum).getWeapDegree());
                bullets.add(normBullet);
                break;
            case 1:
                Bullet pierceBullet = new Bullet();
                pierceBullet.x = (float) initX + 13;
                pierceBullet.y = (float) initY;
                pierceBullet.height = 10;
                pierceBullet.width = 10;
                pierceBullet.setImg(new Texture(Gdx.files.internal("bullet2.png")));
                pierceBullet.setDamage(10);//kena auto mati
                pierceBullet.setSpeed(600 * Gdx.graphics.getDeltaTime());
                pierceBullet.setDegree(weap.getWeapDegree());
                pierceBullet.setPierceable(true);
                bullets.add(pierceBullet);
                break;
            case 2:
                Bullet aoeBullet = new AOEBullet();
                aoeBullet.x = (float) initX + 4;
                aoeBullet.y = (float) initY;
                aoeBullet.height = 30;
                aoeBullet.width = 30;
                aoeBullet.setImg(new Texture(Gdx.files.internal("bullet3.png")));
                aoeBullet.setDegree(weap.getWeapDegree());
                aoeBullet.setDamage(10);//kena pasti mati
                aoeBullet.setSpeed(100 * Gdx.graphics.getDeltaTime());
                ((AOEBullet) aoeBullet).setExplosionWidth(300);
                ((AOEBullet) aoeBullet).setExplosionHeight(300);
                bullets.add(aoeBullet);
                break;
        }
    }

    //movement asteroid
    private void asteroidFall(Asteroid asteroid) {
        double hypotenuse = Math.sqrt(Math.pow(asteroid.getTargetX() - asteroid.x, 2) + Math.pow(asteroid.getTargetY() - asteroid.y, 2));
        asteroid.x += ((asteroid.getTargetX() - asteroid.x) / hypotenuse) * asteroid.getDropSpeed();
        asteroid.y += ((asteroid.getTargetY() - asteroid.y) / hypotenuse) * asteroid.getDropSpeed();
    }

    //movement bullet
    private void bulletMove(Bullet bullet) {
        bullet.x += Math.sin(Math.toRadians(0 - bullet.getDegree())) * bullet.getSpeed();
        bullet.y += Math.cos(Math.toRadians(bullet.getDegree())) * bullet.getSpeed();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bitmapFontGame.dispose();
        background.dispose();
        gameMusic.dispose();
    }
}