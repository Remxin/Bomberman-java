package com.bomberman.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.Screen;

import java.util.function.Supplier;

public class ScreenRedirectButton extends ImageButton {

    public ScreenRedirectButton(Game game, Texture buttonTexture, Supplier<Screen> targetScreen, float x, float y) {
        super(createStyle(buttonTexture));
        setPosition(x, y);

        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(targetScreen.get());
            }
        });
    }

    private static ImageButtonStyle createStyle(Texture texture) {
        ImageButtonStyle style = new ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(texture));
        style.down = new TextureRegionDrawable(new TextureRegion(texture));
        return style;
    }
}
