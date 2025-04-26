package com.bomberman.classes;

import com.badlogic.gdx.graphics.Texture;

public class BreakableBlock extends Blocks{
    public BreakableBlock(Texture texture, float x, float y, float size) {
        super(texture, x, y, size, size, false, true);
    }

    @Override
    public void onDestroy() {
        isDestroyed = true;
    }
}
