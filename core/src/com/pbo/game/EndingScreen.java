package com.pbo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class EndingScreen implements Screen {
    private Texture background;
    final Armageddon game;

    private Music endMusic;
    BitmapFont bitmapFontBlck;
    OrthographicCamera camera;

    public EndingScreen(Armageddon game) {
        this.game = game;
        endMusic = Gdx.audio.newMusic(Gdx.files.internal("EndScreen_BGM.mp3"));
        endMusic.setVolume(0.5f);
        endMusic.setLooping(true);
        endMusic.play();
        bitmapFontBlck = new BitmapFont();
        bitmapFontBlck.getData().setScale(3, 3);
        bitmapFontBlck.setColor(Color.BLACK);
        background = new Texture(Gdx.files.internal("endScreen.png"));
        game.saveScore(game.scores, game.currentPlayer.getName(), game.currentPlayer.getScore());
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 800);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.begin();
        game.batch.draw(background, 0, 0, 1000, 800);
        game.bitmapFont.draw(game.batch, "Your Score: ", game.widthScreen / 2 - 115, game.heightScreen / 2 + 340);
        game.bitmapFont.draw(game.batch, String.valueOf(game.currentPlayer.getScore()), game.widthScreen / 2 - 10, game.heightScreen / 2 + 275);
        game.displayScore(bitmapFontBlck, game.scores, game.widthScreen / 2 - 165, game.heightScreen / 2);
        game.batch.end();

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

    }
}
