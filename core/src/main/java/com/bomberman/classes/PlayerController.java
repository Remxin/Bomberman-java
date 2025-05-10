package com.bomberman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerController {
    private final Player player;
    private final BombManager bombManager;
    private final float tileSize;
    private final float speed;
    public Direction direction;

    private final int upKey, downKey, leftKey, rightKey, bombKey;

    public PlayerController(Player player, BombManager bombManager, float tileSize,
                            int upKey, int downKey, int leftKey, int rightKey,
                            int bombKey, float speed) {
        this.player = player;
        this.bombManager = bombManager;
        this.tileSize = tileSize;
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.bombKey = bombKey;
        this.speed = speed * 90;
    }

    public void update(float delta) {
        if (!player.isAlive()) return;

        // Przetwarzanie ruchu gracza
        float dx = 0f, dy = 0f;
        player.isMoving = false;
        if (Gdx.input.isKeyPressed(upKey))    {
            direction = Direction.UP;
            player.setDirection(Direction.UP, true);
            dy =  1;
        }
        if (Gdx.input.isKeyPressed(downKey))  {
            direction = Direction.DOWN;
            player.setDirection(Direction.DOWN, true);
            dy = -1;
        }
        if (Gdx.input.isKeyPressed(leftKey))  {
            direction = Direction.LEFT;
            player.setDirection(Direction.LEFT, true);
            dx = -1;
        }
        if (Gdx.input.isKeyPressed(rightKey)) {
            direction = Direction.RIGHT;
            player.setDirection(Direction.RIGHT, true);
            dx =  1;
        }

        player.move(dx * speed * delta, dy * speed * delta);

        // Przetwarzanie umieszczania bomby
        if (Gdx.input.isKeyJustPressed(bombKey)) {
            // Pobierz pozycję gracza i zamień ją na współrzędne siatki
            int gridX = Player.toGrid(player.getPosition().x + player.getBounds().width / 2, tileSize);
            int gridY = Player.toGrid(player.getPosition().y + player.getBounds().height / 2, tileSize);


            // Przekaż współrzędne świata odpowiadające współrzędnym siatki
            float worldX = gridX * tileSize;
            float worldY = gridY * tileSize;

            // Umieść bombę używając współrzędnych świata
            bombManager.placeBomb(worldX, worldY, player);
        }
    }
}
