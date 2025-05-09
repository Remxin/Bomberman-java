package com.bomberman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;

public class BombManager {

    private final List<Bomb> bombs = new ArrayList<>();
    private final Map<PlayerColor, Texture> bombTextures = new HashMap<>();
    private final float tileSize;
    private final Blocks[][] map;
    private final List<Player> players = new ArrayList<>();

    public BombManager(Blocks[][] map, float tileSize) {
        this.map = map;
        this.tileSize = tileSize;
    }

    public void setBombTexture(PlayerColor color, Texture texture) {
        bombTextures.put(color, texture);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void placeBomb(float worldX, float worldY, PlayerColor color) {
        // Konwersja współrzędnych świata na współrzędne siatki
        int gx = (int)(worldX / tileSize);
        int gy = (int)(worldY / tileSize);

        // Sprawdź czy na danym polu już jest bomba
        for (Bomb b : bombs) {
            if (!b.isExploded() && b.getGridX() == gx && b.getGridY() == gy) {
                return; // Na tym polu już jest bomba
            }
        }

        Gdx.app.log("DEBUG", "Placing bomb at grid: " + gx + "," + gy +
            " (world: " + worldX + "," + worldY + ")");

        Texture tex = bombTextures.get(color);
        bombs.add(new Bomb(gx, gy, tileSize, tex));
    }

    public void update(float delta) {
        Iterator<Bomb> it = bombs.iterator();
        while (it.hasNext()) {
            Bomb b = it.next();
            b.update(delta);
            if (b.isExploded()) {
                // Bomba eksploduje i jest usuwana
                b.explode(map, tileSize, players);
                it.remove();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Bomb b : bombs) {
            b.render(batch);
        }
    }
}
