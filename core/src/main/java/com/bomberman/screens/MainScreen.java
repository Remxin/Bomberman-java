package com.bomberman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.audio.Music;

import javax.swing.*;

public class MainScreen implements Screen {
    private Stage stage;
    private Game game;
    private Texture background;
    private SpriteBatch batch;
    private Texture play_button_texture;
    private Texture logo_texture;
    private Music music;

    public MainScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();

        music = Gdx.audio.newMusic(Gdx.files.internal("music/backgroundMusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);  // dowolnie
        music.play();

        background = new Texture("MainScreen_background.png");
        logo_texture = new Texture(Gdx.files.internal("logo.png"));
        play_button_texture = new Texture(Gdx.files.internal("play_button.png"));

        Image logo = new Image(new TextureRegion(logo_texture));

        ImageButton.ImageButtonStyle style_play_button = new ImageButton.ImageButtonStyle();
        style_play_button.up = new TextureRegionDrawable(new TextureRegion(play_button_texture)); // stan normalny
        style_play_button.down = new TextureRegionDrawable(new TextureRegion(play_button_texture)); // stan wciśnięty (możesz użyć innej tekstury)

        logo.setPosition(Gdx.graphics.getWidth() / 2.0f - logo.getWidth() / 2.0f , Gdx.graphics.getHeight() / 1.3f - logo.getHeight() / 2.0f);

        ImageButton play_button = new ImageButton(style_play_button);
        play_button.setPosition(Gdx.graphics.getWidth() / 2.0f - play_button.getWidth() / 2.0f, Gdx.graphics.getHeight() / 4.5f - play_button.getHeight() / 2.0f);

        play_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });

        stage.addActor(logo);
        stage.addActor(play_button);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void dispose() {}

}
