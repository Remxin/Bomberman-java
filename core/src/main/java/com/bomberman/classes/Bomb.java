package com.bomberman.classes;

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
    private boolean exploded = false;

    private final Texture texture;

    public Bomb(float worldX, float worldY, float tile, Texture tex) {
        this.worldPos = new Vector2(worldX, worldY);
        this.gridX    = (int)((worldX + tile / 2f) / tile);
        this.gridY    = (int)((worldY + tile / 2f) / tile);
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
        if (!exploded) batch.draw(texture, worldPos.x, worldPos.y);
    }

    public void explode(Blocks[][] map, float tile, List <Player> players) {
        damageCell(gridX, gridY, map);

        for (int d = 1; d <= radius; ++d) if (!damageCell(gridX + d, gridY, map)) break;
        for (int d = 1; d <= radius; ++d) if (!damageCell(gridX - d, gridY, map)) break;
        for (int d = 1; d <= radius; ++d) if (!damageCell(gridX, gridY + d, map)) break;
        for (int d = 1; d <= radius; ++d) if (!damageCell(gridX, gridY - d, map)) break;

        for(Player p : players){
            int px = Player.toGrid(p.getPosition().x, tile);
            int py = Player.toGrid(p.getPosition().y, tile);
            if(Math.abs(px - gridX) + Math.abs(py - gridY) <= radius){
                p.die();
            }
        }
    }

    private static boolean damageCell(int x, int y, Blocks[][] map) {
        if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) return false;
        if (map[x][y] == null) return true;
        if (map[x][y].isBreakable()) { map[x][y].onDestroy(); return false; }
        return !map[x][y].isSolid();
    }
}
