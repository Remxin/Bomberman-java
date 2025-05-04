package com.bomberman.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BombManager {

    private final List<Bomb> bombs = new ArrayList<>();
    private final Texture bombTexture;
    private final float tileSize;
    private final Blocks[][] map;
    private Player player;                // podpinany po stworzeniu

    public BombManager(Blocks[][] map, float tileSize, Texture bombTex) {
        this.map = map;
        this.tileSize = tileSize;
        this.bombTexture = bombTex;
    }
    public void setPlayer(Player p) { this.player = p; }

    public void placeBomb(float worldX, float worldY) {

        int gx = (int)((worldX + tileSize / 2f) / tileSize);
        int gy = (int)((worldY + tileSize / 2f) / tileSize);

        for (Bomb b : bombs) {
            if (!b.isExploded() && b.getGridX() == gx && b.getGridY() == gy)
                return;
        }
        bombs.add(new Bomb(worldX, worldY, tileSize, bombTexture));
    }

    public void update(float delta) {
        Iterator<Bomb> it = bombs.iterator();
        while (it.hasNext()) {
            Bomb b = it.next();
            b.update(delta);
            if (b.isExploded()) {
                b.explode(map, tileSize, player);
                it.remove();
            }
        }
    }
    public void render(SpriteBatch batch) {
        for (Bomb b : bombs) b.render(batch);
    }
}
