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
    private SpriteBatch batch;
    private Texture image;

    @Override
    public void create() {
        //batch = new SpriteBatch();
        //image = new Texture("libgdx.png");
        setScreen(new MainScreen(this));
        context = new GlobalContext();
    }

//    @Override
//    public void render() {
//        super.render();
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//        batch.begin();
//        batch.draw(image, 140, 210);
//        batch.end();
//    }
//
//    @Override
//    public void dispose() {
//        batch.dispose();
//        image.dispose();
//    }
}
