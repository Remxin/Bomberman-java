package com.bomberman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.audio.Music;

import com.bomberman.classes.Player;
import com.bomberman.classes.PlayerColor;
import com.bomberman.game.Main;
import com.bomberman.ui.*;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class EndGameScreen implements Screen {
    private Stage stage;
    private Main game;
    private Texture background;
    private SpriteBatch batch;
    private Texture play_button_texture;
    private Texture logo_texture;
    private Music music;
    private Player playerWon;
    private BitmapFont font;

    public EndGameScreen(Game game, Player player) {
        playerWon = player;
        this.game = (Main) game;
    }

    @Override
    public void show() {
        stage = new Stage();
        batch = new SpriteBatch();

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, com.badlogic.gdx.graphics.Color.WHITE);
        Label endLabel = new Label("The end", labelStyle);
        endLabel.setFontScale(3f);
        endLabel.pack();
        endLabel.setPosition((Gdx.graphics.getWidth() - endLabel.getWidth()) / 2, Gdx.graphics.getHeight() - 100);

        String playerWonText = "Draw!";
        Color playerWonColor = Color.WHITE;

        if (playerWon != null) {
            playerWonText = playerWon.getColor() == PlayerColor.RED ? "Player RED won" : "Player BLUE won";
            playerWonColor = playerWon.getColor() == PlayerColor.RED ? Color.RED : Color.BLUE;
        }

        Label.LabelStyle winnerPlayerLabelStyle = new Label.LabelStyle(font, playerWonColor);
        Label winnerPlayerLabel = new Label(playerWonText, winnerPlayerLabelStyle);
        winnerPlayerLabel.setFontScale(2f);
        winnerPlayerLabel.pack();
        winnerPlayerLabel.setPosition((Gdx.graphics.getWidth() - winnerPlayerLabel.getWidth()) / 2, Gdx.graphics.getHeight() - 200);


        stage.addActor(endLabel);
        stage.addActor(winnerPlayerLabel);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


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
