package com.bomberman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Bomb {

    public static final float DETONATION_TIME = 2f;
    private static final int  DEFAULT_RADIUS  = 2;

    private final Vector2 worldPos;
    private final int gridX, gridY;

    private float  timer   = 0f;
    private int    radius  = DEFAULT_RADIUS;
    public static final float BOMB_SIZE = 30f;
    public boolean exploded = false;
    private float tileSize;

    private final Texture texture;
    private final Texture explosionTexture;

    private static class ExplosionTile {
        Vector2 position;

        public ExplosionTile(float x, float y) {
            this.position = new Vector2(x, y);
        }
    }
    private final List<ExplosionTile> explosionTiles = new ArrayList<>();
    private float explosionTimer = 0f;
    private static final float EXPLOSION_DURATION = 0.5f;
    private boolean finished = false;

    public Bomb(float gridX, float gridY, float tileSize, Texture tex, Texture explosionTexture, int radius) {
        this.gridX = (int)gridX;
        this.gridY = (int)gridY;
        this.tileSize = tileSize;
        // Obliczenie pozycji w świecie - wyśrodkowanie bomby na kafelku
        float worldX = this.gridX * tileSize + (tileSize - BOMB_SIZE) / 2f;
        float worldY = this.gridY * tileSize + (tileSize - BOMB_SIZE) / 2f;
        this.worldPos = new Vector2(worldX, worldY);
        this.texture  = tex;
        this.explosionTexture = explosionTexture;
        this.radius = radius;
    }


    public int  getGridX()     { return gridX; }
    public int  getGridY()     { return gridY; }

    public void update(float delta, Blocks[][] map, float tile, List<Player> players) {
        if (!exploded) {
            timer += delta;
            if (timer >= DETONATION_TIME) {
                exploded = true;
                explosionTimer = 0f;
                explode(map, tile, players); // ← wywołaj tutaj!
            }
        } else {
            explosionTimer += delta;
        }
    }

    public boolean isReadyToRemove() {
        return exploded && explosionTimer >= EXPLOSION_DURATION;
    }

    private void addExplosionTile(int x, int y) {
        float wx = x * tileSize + (tileSize - BOMB_SIZE) / 2f;
        float wy = y * tileSize + (tileSize - BOMB_SIZE) / 2f;
        explosionTiles.add(new ExplosionTile(wx, wy));
    }

    public boolean isFinished() {
        return finished;
    }

    public void render(SpriteBatch batch) {
        if (!exploded) {
            batch.draw(texture, worldPos.x, worldPos.y, BOMB_SIZE, BOMB_SIZE);
        } else {
            for (ExplosionTile e : explosionTiles) {
                batch.draw(explosionTexture, e.position.x, e.position.y, BOMB_SIZE, BOMB_SIZE);
            }
        }
    }


    public void explode(Blocks[][] map, float tile, List<Player> players) {
        // Eksplozja na pozycji bomby
        damageCell(gridX, gridY, map);

        addExplosionTile(gridX, gridY);
        Gdx.app.log("DEBUG", "exploded");
        // Eksplozja w czterech kierunkach
        for (int d = 1; d <= radius; ++d) {
            if (!damageCell(gridX + d, gridY, map)) break;
        }
        for (int d = 1; d <= radius; ++d) {
            if (!damageCell(gridX - d, gridY, map)) break;
        }
        for (int d = 1; d <= radius; ++d) {
            if (!damageCell(gridX, gridY + d, map)) break;
        }
        for (int d = 1; d <= radius; ++d) {
            if (!damageCell(gridX, gridY - d, map)) break;
        }

        // Sprawdzenie czy gracz został trafiony
        for (Player p : players) {
            int px = Player.toGrid(p.getPosition().x, tile);
            int py = Player.toGrid(p.getPosition().y, tile);

            // Sprawdź czy gracz znajduje się w zasięgu wybuchu
            for (ExplosionTile e : explosionTiles) {
                int ex = Player.toGrid(e.position.x, tile);
                int ey = Player.toGrid(e.position.y, tile);
                if (ex == px && ey == py) {
                    p.takeDamage();
                    p.respawn();
                    break;
                }
            }
        }
    }

    private boolean damageCell(int x, int y, Blocks[][] map) {
        // Sprawdź czy współrzędne są prawidłowe
        if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) return false;

        // Puste pole - eksplozja przechodzi dalej
        if (map[x][y] == null) {
            addExplosionTile(x, y);
            return true;
        }

        // Blok zniszczalny - zniszcz i zatrzymaj eksplozję
        if (map[x][y].isBreakable()) {
            map[x][y].onDestroy();
            addExplosionTile(x, y);
            return false;
        }

        // Blok niezniszczalny - zatrzymaj eksplozję
        return !map[x][y].isSolid();
    }
}
