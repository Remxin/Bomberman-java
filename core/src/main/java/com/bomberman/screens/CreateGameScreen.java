package com.bomberman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Texture;


import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bomberman.game.Main;
import com.bomberman.ui.*;



public class CreateGameScreen implements Screen {
    private Stage stage;
    private Main game;
    private Texture wsad_keys_texture;
    private Texture arrow_keys_texture;
    private Texture play_button_texture;

    final float sliderStartHeight = 275;
    final float sliderStartWidth = 200;
    final float sliderWidth = 300;

    public CreateGameScreen(Game game) {
        this.game = (Main) game;
    }

    @Override
    public void show() {
        stage = new Stage();
        wsad_keys_texture = new Texture(Gdx.files.internal("WSAD_keys.png"));
        arrow_keys_texture = new Texture(Gdx.files.internal("arrow_keys.png"));
        play_button_texture = new Texture(Gdx.files.internal("play_button.png"));

        Texture spacebar_texture = new Texture(Gdx.files.internal("spacebar.png"));
        Texture enter_texture = new Texture(Gdx.files.internal("enter.png"));

        Texture spriteSheet = new Texture(Gdx.files.internal("player_sprite.png"));
        TextureRegion[][] regions = TextureRegion.split(spriteSheet, 24, 24);

        TextureRegion playerRegion = regions[0][4]; // <-- zmień indeksy w zależności od tego który sprite chcesz

        Image player1Image = new Image(new TextureRegionDrawable(playerRegion));
        player1Image.setSize(100, 100);
        player1Image.setPosition(150, Gdx.graphics.getHeight() - 200);

        Image player2Image = new Image(new TextureRegionDrawable(regions[0][6]));
        player2Image.setSize(100, 100);
        player2Image.setPosition(Gdx.graphics.getWidth() - 250, Gdx.graphics.getHeight() - 200);

        Image player1Controlls = new Image(wsad_keys_texture);
        Image player2Controlls = new Image(arrow_keys_texture);

        Image player1BombControll = new Image(spacebar_texture);
        Image player2BombControll = new Image(enter_texture);

        player1Controlls.setSize(200, 200);
        player2Controlls.setSize(200, 200);

        player1BombControll.setSize(150, 150);
        player2BombControll.setSize(150, 150);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, com.badlogic.gdx.graphics.Color.WHITE);

        Label player1Label = new Label("Player 1", labelStyle);
        Label player2Label = new Label("Player 2", labelStyle);
        Label titleLabel = new Label("Settings", labelStyle);
        titleLabel.setFontScale(3f);

        titleLabel.setPosition(
            (Gdx.graphics.getWidth() - titleLabel.getPrefWidth()) / 2f,
            Gdx.graphics.getHeight() - 50 // np. 50 px od góry
        );

        player1Label.setPosition(175, Gdx.graphics.getHeight() - 100);
        player2Label.setPosition(Gdx.graphics.getWidth() - 225, Gdx.graphics.getHeight() - 100);

        stage.addActor(player1Label);
        stage.addActor(player2Label);

        player1Controlls.setPosition(100, Gdx.graphics.getHeight() - 400);
        player2Controlls.setPosition(Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() - 400);

        player1BombControll.setPosition(125, Gdx.graphics.getHeight() - 450);
        player2BombControll.setPosition(Gdx.graphics.getWidth() - 275, Gdx.graphics.getHeight() - 500);


        ImageButton play_button = new ScreenRedirectButton(
            game,
            play_button_texture,
            () -> new GameScreen(game),
            Gdx.graphics.getWidth() - 350,
            Gdx.graphics.getHeight() / 4.75f - play_button_texture.getHeight() / 2.0f
        );

        initForm(labelStyle);

        stage.addActor(play_button);
        stage.addActor(player1Image);
        stage.addActor(player2Image);

        stage.addActor(titleLabel);
        stage.addActor(player1Controlls);
        stage.addActor(player2Controlls);
        stage.addActor(player1BombControll);
        stage.addActor(player2BombControll);

        Gdx.input.setInputProcessor(stage);
    }

    public void initForm(Label.LabelStyle labelStyle) {
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Slider startHearthSlider = new Slider(1, 6, 1, false, skin);
        Label startHearthLabel = new Label("Start hearth: " + game.context.maxPlayerHealth, labelStyle);
        startHearthLabel.setFontScale(1f);
        startHearthLabel.setPosition(sliderStartWidth, sliderStartHeight);

        startHearthSlider.setWidth(sliderWidth);
        startHearthSlider.setPosition(sliderStartWidth, sliderStartHeight - 25);


        Slider startBombExplosionRadiusSlider = new Slider(1, 6, 1, false, skin);
        Label startBombExplosionRadiusLabel = new Label("Bomb explosion radius: " + game.context.initialBombExplosionRadius, labelStyle);
        startBombExplosionRadiusLabel.setFontScale(1f);
        startBombExplosionRadiusLabel.setPosition(sliderStartWidth, sliderStartHeight - 50);

        startBombExplosionRadiusSlider.setWidth(sliderWidth);
        startBombExplosionRadiusSlider.setPosition(sliderStartWidth, sliderStartHeight - 75);

        Slider startSpeedSlider = new Slider(1, 3, 1, false, skin);
        Label startSpeedLabel = new Label("Start speed: " + (int) game.context.initialPlayerSpeed, labelStyle);
        startSpeedLabel.setFontScale(1f);
        startSpeedLabel.setPosition(sliderStartWidth, sliderStartHeight - 100);

        startSpeedSlider.setWidth(sliderWidth);
        startSpeedSlider.setPosition(sliderStartWidth, sliderStartHeight - 125);


        startHearthSlider.setValue(game.context.maxPlayerHealth);
        startHearthSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.context.maxPlayerHealth = (int) startHearthSlider.getValue();
                startHearthLabel.setText("Start hearth: " + game.context.maxPlayerHealth);
            }
        });

        startBombExplosionRadiusSlider.setValue(game.context.initialBombExplosionRadius);
        startBombExplosionRadiusSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.context.initialBombExplosionRadius = (int) startBombExplosionRadiusSlider.getValue();
                startBombExplosionRadiusLabel.setText("Bomb explosion radius: " + game.context.initialBombExplosionRadius);
            }
        });

        startSpeedSlider.setValue((float) game.context.initialPlayerSpeed); // rzutowanie na float, jeśli trzeba
        startSpeedSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.context.initialPlayerSpeed = startSpeedSlider.getValue();
                startSpeedLabel.setText("Start speed: " + (int) game.context.initialPlayerSpeed);
            }
        });

        stage.addActor(startHearthLabel);
        stage.addActor(startBombExplosionRadiusLabel);
        stage.addActor(startSpeedLabel);

        stage.addActor(startHearthSlider);
        stage.addActor(startBombExplosionRadiusSlider);
        stage.addActor(startSpeedSlider);
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
