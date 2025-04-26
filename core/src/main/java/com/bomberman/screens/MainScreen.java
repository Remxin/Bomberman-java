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

import javax.swing.*;

public class MainScreen implements Screen {
    private Stage stage;
    private Game game;
    private Texture background;
    private SpriteBatch batch;
    private Texture play_button_texture;
    private Texture health_up_button_texture;
    private Texture health_down_button_texture;

    public MainScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        background = new Texture("MainScreen_background.png");

        play_button_texture = new Texture(Gdx.files.internal("play_button.png"));
        health_up_button_texture = new Texture(Gdx.files.internal("health_up.png"));
        health_down_button_texture = new Texture(Gdx.files.internal("health_down.png"));

        ImageButton.ImageButtonStyle style_play_button = new ImageButton.ImageButtonStyle();
        style_play_button.up = new TextureRegionDrawable(new TextureRegion(play_button_texture)); // stan normalny
        style_play_button.down = new TextureRegionDrawable(new TextureRegion(play_button_texture)); // stan wciśnięty (możesz użyć innej tekstury)

        ImageButton.ImageButtonStyle style_health_button = new ImageButton.ImageButtonStyle();
        style_health_button.up = new TextureRegionDrawable(new TextureRegion(health_up_button_texture)); // stan domyślny
        style_health_button.down = new TextureRegionDrawable(new TextureRegion(health_down_button_texture));

        ImageButton play_button = new ImageButton(style_play_button);
        play_button.setPosition(Gdx.graphics.getWidth() / 2.0f - play_button.getWidth() / 2.0f, Gdx.graphics.getHeight() / 4.5f - play_button.getHeight() / 2.0f);

        ImageButton health_button = new ImageButton(style_health_button);
        health_button.setPosition(Gdx.graphics.getWidth() / 2.0f -health_button.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f - health_button.getHeight() / 2.0f);

        health_button.getStyle().up = new TextureRegionDrawable(new TextureRegion(health_up_button_texture));
        health_button.setChecked(false);

        play_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });

        health_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (health_button.isChecked()) {
                    health_button.getStyle().up = new TextureRegionDrawable(new TextureRegion(health_down_button_texture));
                } else {
                    health_button.getStyle().up = new TextureRegionDrawable(new TextureRegion(health_up_button_texture));
                }
            }
        });

        stage.addActor(play_button);
        stage.addActor(health_button);

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
