package com.bomberman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerController {

    private static final float SPEED = 200f;

    private final Player player;
    private final BombManager bombManager;
    private final float tile;

    public PlayerController(Player player, BombManager bombManager, float tileSize) {
        this.player      = player;
        this.bombManager = bombManager;
        this.tile        = tileSize;
    }

    public void update(float delta) {
        float dx = 0f, dy = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    dy =  1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  dy = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  dx = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dx =  1;

        player.move(dx * SPEED * delta, dy * SPEED * delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            int gridX = Player.toGrid(player.getPosition().x, tile);
            int gridY = Player.toGrid(player.getPosition().y, tile);

            float worldX = gridX * tile;
            float worldY = gridY * tile;

            bombManager.placeBomb(worldX, worldY);
        }
    }
}
