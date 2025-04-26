package com.bomberman.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bomb {
    public static final float DETONATION_TIME = 2f;
    private static final int DEFAULT_RADIUS = 1;
    public static final int CELL_EMPTY = 0;
    public static final int CELL_WALL  = 1;
    public static final int CELL_BRICK = 2;


    private Vector2 worldPos;
    private int gridX, gridY;
    private float timer;
    private int radius;
    private boolean exploded;

    private Texture bombTexture;

    public Bomb(int gridX, int gridY, float tileSize, Texture bombTexture) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.worldPos = new Vector2(gridX * tileSize, gridY * tileSize);
        this.radius = DEFAULT_RADIUS;
        this.timer = 0f;
        this.exploded = false;
        this.bombTexture = bombTexture;
    }

    public int getGridX() {
        return gridX;
    }
    public int getGridY() {
        return gridY;
    }

    public void update(float delta){
        timer += delta;
        if (timer >= DETONATION_TIME) {
            exploded = true;
        }
    }

    public void render(SpriteBatch batch){
        if (!exploded) {
            batch.draw(bombTexture, worldPos.x, worldPos.y);
        }
    }

    public void explode(Blocks[][] map, float tileSize, Player player){

        damageCell(gridX, gridY, map);

        for (int dx = 1; dx <= radius; dx++) {
            if (!damageCell(gridX + dx, gridY, map)) break;
        }
        for (int dx = 1; dx <= radius; dx++) {
            if (!damageCell(gridX - dx, gridY, map)) break;
        }
        for (int dy = 1; dy <= radius; dy++) {
            if (!damageCell(gridX, gridY + dy, map)) break;
        }
        for (int dy = 1; dy <= radius; dy++) {
            if (!damageCell(gridX, gridY - dy, map)) break;
        }

        Vector2 p = player.getPosition();
        int px = (int)(p.x /tileSize), py = (int)(p.y / tileSize);
        if (Math.abs(px - gridX) + Math.abs(py - gridY) <= radius) {
            player.die();
        }

    }


    private boolean damageCell(int x, int y, Blocks[][] map){
        if(x<0 || y<0 || x>=map.length || y>=map[0].length) return false;
        if (map[x][y] == null) return true;
        if (map[x][y].isSolid()) {
            return false;
        }
        if(map[x][y].isBreakable()){
            map[x][y].onDestroy();
        }

        return true;
    }

    public boolean isExploded() {
        return exploded;
    }

}
