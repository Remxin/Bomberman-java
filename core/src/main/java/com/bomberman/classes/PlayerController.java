package com.bomberman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerController {

    private static final float SPEED = 200f;

    private final Player player;
    private final BombManager bombManager;
    private final float tile;

    private final int upKey, downKey, leftKey, rightKey, bombKey;

    public PlayerController(Player player, BombManager bombManager,float tileSize,int upKey, int downKey, int leftKey, int rightKey,int bombKey) {
        this.player      = player;
        this.bombManager = bombManager;
        this.tile        = tileSize;
        this.upKey       = upKey;
        this.downKey     = downKey;
        this.leftKey     = leftKey;
        this.rightKey    = rightKey;
        this.bombKey     = bombKey;
    }
    public void update(float delta) {
        float dx = 0f, dy = 0f;
        if (Gdx.input.isKeyPressed(upKey))    dy =  1;
        if (Gdx.input.isKeyPressed(downKey))  dy = -1;
        if (Gdx.input.isKeyPressed(leftKey))  dx = -1;
        if (Gdx.input.isKeyPressed(rightKey)) dx =  1;

        player.move(dx * SPEED * delta, dy * SPEED * delta);

        if (Gdx.input.isKeyJustPressed(bombKey)) {
            int gridX = Player.toGrid(player.getPosition().x, tile);
            int gridY = Player.toGrid(player.getPosition().y, tile);

            float worldX = gridX * tile;
            float worldY = gridY * tile;

            bombManager.placeBomb(worldX, worldY);
        }
    }
}
