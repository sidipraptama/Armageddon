package com.pbo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

import java.nio.file.Paths;
import java.util.ArrayList;


public class MainMenuScreen implements Screen {
    private Music mainMenuMusic;
    private Sound transition;
    private Texture background;
    private BitmapFont mainFont;
    final Armageddon game;
    OrthographicCamera camera;

    public MainMenuScreen(final Armageddon game) {
        this.game = game;

        game.scores = new ArrayList<>();
        game.readScore(game.scores, Paths.get("Scores.txt"));

        background = new Texture(Gdx.files.internal("mainScreen.png"));

        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("MainMenuScreen_BGM.mp3"));
        mainMenuMusic.setVolume(0.5f);
        mainMenuMusic.setLooping(true);
        mainMenuMusic.play();
        transition = Gdx.audio.newSound(Gdx.files.internal("transition.mp3"));

        mainFont = new BitmapFont();
        mainFont.setColor(Color.WHITE);
        mainFont.getData().setScale(3, 3);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 800);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, 1000, 800);
        mainFont.draw(game.batch, "Welcome " + game.currentPlayer.getName() + "!!!", game.widthScreen / 2 - 150, game.heightScreen / 2 - 200);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            transition.play();

            game.setScreen(new GameScreen(game));
            dispose();
        }
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
        mainFont.dispose();
        background.dispose();
        mainMenuMusic.dispose();
    }
}