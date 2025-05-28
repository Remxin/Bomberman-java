package com.bomberman.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;

public class BombManager {

    private final List<Bomb> bombs = new ArrayList<>();
    private final Map<PlayerColor, Texture> bombTextures = new HashMap<>();
    private final Map<PlayerColor, Texture> explosionTextures = new HashMap<>();
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
    public void setExplosionTexture(PlayerColor color, Texture texture) { explosionTextures.put(color, texture); }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void placeBomb(float worldX, float worldY, Player player) {
        // Konwersja współrzędnych świata na współrzędne siatki
        int gx = (int)(worldX / tileSize);
        int gy = (int)(worldY / tileSize);

        // Sprawdź czy na danym polu już jest bomba
        for (Bomb b : bombs) {
            if (!b.exploded && b.getGridX() == gx && b.getGridY() == gy) {
                return; // Na tym polu już jest bomba
            }
        }

        Texture tex = bombTextures.get(player.getColor());
        Texture explosionTex = explosionTextures.get(player.getColor());
        bombs.add(new Bomb(gx, gy, tileSize, tex, explosionTex, player.bombExplosionRadius));
    }

    public void update(float delta) {
        Iterator<Bomb> it = bombs.iterator();
        while (it.hasNext()) {
            Bomb b = it.next();
            b.update(delta, map, tileSize, players);

            if (b.isReadyToRemove()) {
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
