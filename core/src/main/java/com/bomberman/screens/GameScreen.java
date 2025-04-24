package com.bomberman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    private float playerX = 100;
    private float playerY = 100;
    private final float PLAYER_SIZE = 32;
    private final float SPEED = 200;

    public GameScreen(Game game) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1); // biały gracz
        shapeRenderer.rect(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);
        shapeRenderer.end();
    }

    private void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  playerX -= SPEED * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerX += SPEED * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    playerY += SPEED * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  playerY -= SPEED * delta;
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        shapeRenderer.dispose();
    }
}
