package com.bomberman.classes;

import com.badlogic.gdx.graphics.Texture;
import jdk.internal.org.commonmark.node.Block;

public class SolidBlock extends Blocks {
    public SolidBlock(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height, true, false);
    }

    @Override
    public void onDestroy() {
        // Solid block nie może zostać zniszczony
    }
}
