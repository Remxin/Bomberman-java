package com.bomberman.classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;


public class BombManager {
    private List<Bomb> bombs = new ArrayList<>();
    private Texture bombTexture;
    private float tileSize;
    private int[][] map;
    private Player player;

    public BombManager(int[][] map, float tileSize, Texture bombTexture, Player player) {
        this.map         = map;
        this.tileSize    = tileSize;
        this.bombTexture = bombTexture;
        this.player      = player;
    }

    public float getTileSize(){
        return tileSize;
    }

    public void placeBomb(int gridX, int gridY){
        for(Bomb b : bombs){
            if(!b.isExploded() && b.getGridX() == gridX && b.getGridY() == gridY){
                return;
            }
        }
        bombs.add(new Bomb(gridX, gridY, tileSize, bombTexture));
    }

    public void update(float delta){
        Iterator<Bomb> it = bombs.iterator();
        while(it.hasNext()){
            Bomb b = it.next();
            b.update(delta);
            if(b.isExploded()){
                b.explode(map, tileSize, player);
                it.remove();
            }
        }
    }

    public void render(SpriteBatch batch){
        for(Bomb b : bombs){
            b.render(batch);
        }
    }

}
