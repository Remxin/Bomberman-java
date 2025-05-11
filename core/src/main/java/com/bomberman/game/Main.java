package com.bomberman.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bomberman.screens.GameScreen;
import com.bomberman.screens.MainScreen;
import com.bomberman.context.GlobalContext;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public GlobalContext context;

    @Override
    public void create() {
        setScreen(new MainScreen(this));
        context = new GlobalContext();
    }

}
