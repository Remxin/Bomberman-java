package com.bomberman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.List;

public class Bomb {

    public static final float DETONATION_TIME = 2f;
    private static final int  DEFAULT_RADIUS  = 2;

    private final Vector2 worldPos;
    private final int gridX, gridY;

    private float  timer   = 0f;
    private int    radius  = DEFAULT_RADIUS;
    public static final float BOMB_SIZE = 30f;
    private boolean exploded = false;

    private final Texture texture;

    public Bomb(float gridX, float gridY, float tileSize, Texture tex) {
        this.gridX = (int)gridX;
        this.gridY = (int)gridY;

        // Obliczenie pozycji w świecie - wyśrodkowanie bomby na kafelku
        float worldX = this.gridX * tileSize + (tileSize - BOMB_SIZE) / 2f;
        float worldY = this.gridY * tileSize + (tileSize - BOMB_SIZE) / 2f;
        Gdx.app.log("DEBUG", "tileSize: " + tileSize + " gridX: " + gridX + " gridY: " + gridY + " BombSize: " + BOMB_SIZE);
        Gdx.app.log("DEBUG", "worldX: " + worldX + " worldY: " + worldY + " gridX: " + gridX + " gridY: " + worldY);
        this.worldPos = new Vector2(worldX, worldY);
        this.texture  = tex;
    }

    public int  getGridX()     { return gridX; }
    public int  getGridY()     { return gridY; }
    public boolean isExploded(){ return exploded; }

    public void update(float delta) {
        timer += delta;
        if (timer >= DETONATION_TIME) exploded = true;
    }

    public void render(SpriteBatch batch) {
        if (!exploded) {
            batch.draw(texture, worldPos.x, worldPos.y, BOMB_SIZE, BOMB_SIZE);
        }
    }

    public void explode(Blocks[][] map, float tile, List<Player> players) {
        // Eksplozja na pozycji bomby
        damageCell(gridX, gridY, map);

        // Eksplozja w czterech kierunkach
        for (int d = 1; d <= radius; ++d) if (!damageCell(gridX + d, gridY, map)) break;
        for (int d = 1; d <= radius; ++d) if (!damageCell(gridX - d, gridY, map)) break;
        for (int d = 1; d <= radius; ++d) if (!damageCell(gridX, gridY + d, map)) break;
        for (int d = 1; d <= radius; ++d) if (!damageCell(gridX, gridY - d, map)) break;

        // Sprawdzenie czy gracz został trafiony
        for (Player p : players) {
            int px = Player.toGrid(p.getPosition().x, tile);
            int py = Player.toGrid(p.getPosition().y, tile);

            // Sprawdź czy gracz znajduje się w zasięgu wybuchu
            if ((px == gridX && Math.abs(py - gridY) <= radius) ||
                (py == gridY && Math.abs(px - gridX) <= radius) ||
                (px == gridX && py == gridY)) {
                p.die();
            }
        }
    }

    private static boolean damageCell(int x, int y, Blocks[][] map) {
        // Sprawdź czy współrzędne są prawidłowe
        if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) return false;

        // Puste pole - eksplozja przechodzi dalej
        if (map[x][y] == null) return true;

        // Blok zniszczalny - zniszcz i zatrzymaj eksplozję
        if (map[x][y].isBreakable()) {
            map[x][y].onDestroy();
            return false;
        }

        // Blok niezniszczalny - zatrzymaj eksplozję
        return !map[x][y].isSolid();
    }
}
