package com.bomberman.classes;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Blocks {
    protected Texture blockTexture;
    protected boolean isSolid;
    protected boolean isBreakable;
    protected Rectangle bounds;
    protected boolean isDestroyed = false;

    public Blocks(Texture blockTexture, float x, float y, float width, float height, boolean isSolid, boolean isBreakable) {
        this.blockTexture = blockTexture;
        this.isSolid = isSolid;
        this.isBreakable = isBreakable;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void render(SpriteBatch batch) {
        if (!isDestroyed) {
            batch.draw(blockTexture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public boolean isBreakable() {
        return isBreakable;
    }

    public abstract void onDestroy();
}
