package com.bomberman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.classes.Blocks;
import com.bomberman.classes.BreakableBlock;
import com.bomberman.classes.Player;
import com.bomberman.classes.BombManager;
import com.bomberman.classes.Bomb;
import com.bomberman.classes.SolidBlock;
import com.sun.tools.javac.comp.Todo;

public class GameScreen implements Screen {
    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 600;
    private static final float TILE_SIZE = 32f;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    private Blocks[][] map;
    private Texture wallTex, brickTex, bombTex;
    private BombManager bombManager;
    private Player player;


    public GameScreen(Game game) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        wallTex  = new Texture(Gdx.files.internal("solid_block.png"));
        brickTex = new Texture(Gdx.files.internal("breakable_block.png"));
        bombTex  = new Texture(Gdx.files.internal("bomb.png"));

        map = new Blocks[25][19];
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (x == 0 || y == 0 || x == map.length-1 || y == map[0].length-1) {
                    map[x][y] = new SolidBlock(wallTex, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else if (x % 2 == 0 && y % 2 == 0) {
                    map[x][y] = new SolidBlock(wallTex, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else {
                    if (Math.random() < 0.3) {
                        map[x][y] = new BreakableBlock(brickTex, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }


        // TODO: POPRAWIC TĄ LOGIKE
        bombManager = new BombManager(map, TILE_SIZE, bombTex, null);
        player = new Player(new Vector2(3 * TILE_SIZE, 3 * TILE_SIZE), bombManager);
        bombManager.setPlayer(player);


    }

    private void checkPlayerCollision() {
        Rectangle playerBounds = player.getBounds();

        int startX = (int) (playerBounds.x / TILE_SIZE);
        int startY = (int) (playerBounds.y / TILE_SIZE);
        int endX = (int) (playerBounds.x + playerBounds.width / TILE_SIZE);
        int endY = (int) (playerBounds.y + playerBounds.height / TILE_SIZE);

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (x >= 0 && x < map.length && y >= 0 && y < map[0].length){
                    Blocks block = map[x][y];
                    if (block != null && block.isSolid() && block.getBounds().overlaps(playerBounds)){
                        player.revertPosition();
                        player.updateBounds();
                        return;
                    }
                }
            }
        }
    }

    private void update(float delta) {
        player.update(delta);
        checkPlayerCollision();
        bombManager.update(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y] != null) {
                    map[x][y].render(batch);
                }
            }
        }
        bombManager.render(batch);
        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Vector2 pos = player.getPosition();
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(pos.x, pos.y, TILE_SIZE, TILE_SIZE);
        shapeRenderer.end();
    }

    @Override public void show()    {}
    @Override public void resize(int w, int h)   {}
    @Override public void pause()   {}
    @Override public void resume()  {}
    @Override public void hide()    {}
    @Override public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        wallTex.dispose();
        brickTex.dispose();
        bombTex.dispose();
    }
}
