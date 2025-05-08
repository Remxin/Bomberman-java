package com.bomberman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.bomberman.game.Main;
import com.bomberman.ui.*;

public class MainScreen implements Screen {
    private Stage stage;
    private Main game;
    private Texture background;
    private SpriteBatch batch;
    private Texture play_button_texture;
    private Texture logo_texture;

    public MainScreen(Game game) {
        this.game = (Main) game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();

        background = new Texture("MainScreen_background.png");
        logo_texture = new Texture(Gdx.files.internal("logo.png"));
        play_button_texture = new Texture(Gdx.files.internal("play_button.png"));

        Image logo = new Image(new TextureRegion(logo_texture));

        ImageButton play_button = new ScreenRedirectButton(
            game,
            play_button_texture,
            new CreateGameScreen(game),
            Gdx.graphics.getWidth() / 2.0f - play_button_texture.getWidth() / 2.0f,
            Gdx.graphics.getHeight() / 4.5f - play_button_texture.getHeight() / 2.0f
        );

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
