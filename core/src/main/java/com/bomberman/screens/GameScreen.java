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
import com.bomberman.game.Main;
import com.sun.tools.javac.comp.Todo;
import com.bomberman.classes.PlayerController;
import com.badlogic.gdx.Input;


public class GameScreen implements Screen {
    private Main game;
    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 608;
    private static final float TILE_SIZE = 32f;
    private static final float PLAYER_SIZE = TILE_SIZE - 8f;
    private static final float BOMB_SIZE = TILE_SIZE - 8f;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    private static final int Map_W = 25;
    private static final int Map_H = 19;
    private static final int[][] SPAWNS = {
            {1,1}, {Map_W - 2, Map_H - 2},
    };

    private Blocks[][] map;
    private Texture wallTex, brickTex, bombTex;
    private BombManager bombManager;
    private Player p1, p2;
    private PlayerController c1, c2;


    public GameScreen(Game g) {
        game = (Main) g;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        wallTex  = new Texture(Gdx.files.internal("solid_block.png"));
        brickTex = new Texture(Gdx.files.internal("breakable_block.png"));
        bombTex  = new Texture(Gdx.files.internal("bomb.png"));

        map = new Blocks[Map_W][Map_H];
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if(isSpawnArea(x,y)){
                    map[x][y] = null;
                    continue;
                }
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


        p1 = new Player(new Vector2(SPAWNS[0][0]*TILE_SIZE, SPAWNS[0][1]*TILE_SIZE), PLAYER_SIZE);
        p2 = new Player(new Vector2(SPAWNS[1][0]*TILE_SIZE, SPAWNS[1][1]*TILE_SIZE), PLAYER_SIZE);
        bombManager = new BombManager(map, TILE_SIZE, bombTex);
        bombManager.addPlayer(p1);
        bombManager.addPlayer(p2);

        c1 = new PlayerController(p1, bombManager, TILE_SIZE, Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D, Input.Keys.SPACE, (float)game.context.initialPlayerSpeed);
        c2 = new PlayerController(p2, bombManager, TILE_SIZE, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.ENTER, (float)game.context.initialPlayerSpeed);

    }

    private void checkPlayerCollision(Player player) {
        Rectangle playerBounds = player.getBounds();

        int startX = Math.max(0, (int)(playerBounds.x / TILE_SIZE));
        int startY = Math.max(0, (int)(playerBounds.y / TILE_SIZE));
        int endX = Math.min(map.length - 1, (int)((playerBounds.x + playerBounds.width) / TILE_SIZE));
        int endY = Math.min(map[0].length - 1, (int)((playerBounds.y + playerBounds.height) / TILE_SIZE));

        boolean collisionDetected = false;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                Blocks block = map[x][y];
                if (block != null && block.isSolid() && block.getBounds().overlaps(playerBounds)) {
                    collisionDetected = true;

                    float overlapLeft = (block.getBounds().x + block.getBounds().width) - playerBounds.x;
                    float overlapRight = (playerBounds.x + playerBounds.width) - block.getBounds().x;
                    float overlapTop = (block.getBounds().y + block.getBounds().height) - playerBounds.y;
                    float overlapBottom = (playerBounds.y + playerBounds.height) - block.getBounds().y;

                    float minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

                    if (minOverlap == overlapLeft) {
                        player.setPosition(new Vector2(block.getBounds().x + block.getBounds().width, player.getPosition().y));
                    } else if (minOverlap == overlapRight) {
                        player.setPosition(new Vector2(block.getBounds().x - playerBounds.width, player.getPosition().y));
                    } else if (minOverlap == overlapTop) {
                        player.setPosition(new Vector2(player.getPosition().x, block.getBounds().y + block.getBounds().height));
                    } else if (minOverlap == overlapBottom) {
                        player.setPosition(new Vector2(player.getPosition().x, block.getBounds().y - playerBounds.height));
                    }

                    player.updateBounds();
                    playerBounds = player.getBounds();
                }
            }
        }

        if (!collisionDetected) {
            return;
        }
    }

    private boolean isSpawnArea(int x, int y) {
        if(x == 0 || y == 0 || x == map.length-1 || y == map[0].length-1) return false;
        for (int[] s : SPAWNS) {
            int sx = s[0];
            int sy = s[1];
            if (x == sx && y == sy) return true;
            if (x == sx && Math.abs(y - sy) == 1) return true;
            if (y == sy && Math.abs(x - sx) == 1) return true;
        }
        return false;
    }

    private void update(float delta) {
        c1.update(delta);
        c2.update(delta);
        checkPlayerCollision(p1);
        checkPlayerCollision(p2);
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

        // Rysowanie mapy
        for (Blocks[] blocks : map) {
            for (int y = 0; y < map[0].length; y++) {
                if (blocks[y] != null) {
                    blocks[y].render(batch);
                }
            }
        }

        // Rysowanie bomb
        bombManager.render(batch);
        batch.end();

        // Rysowanie graczy (tylko jeśli są żywi)
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (p1.isAlive()) {
            Vector2 v1 = p1.getPosition();
            shapeRenderer.setColor(1, 0, 0, 1); // Czerwony dla gracza 1
            shapeRenderer.rect(v1.x, v1.y, PLAYER_SIZE, PLAYER_SIZE);
        }

        if (p2.isAlive()) {
            Vector2 v2 = p2.getPosition();
            shapeRenderer.setColor(0, 0, 1, 1); // Niebieski dla gracza 2
            shapeRenderer.rect(v2.x, v2.y, PLAYER_SIZE, PLAYER_SIZE);
        }

        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.end();

        // Sprawdzenie warunków zakończenia gry
        checkGameOver();
    }

    // Dodaj tę metodę do klasy GameScreen
    private void checkGameOver() {
        if (!p1.isAlive() && !p2.isAlive()) {
            Gdx.app.log("GAME", "Game Over! Both players died!");
            // Można dodać tutaj logikę zakończenia gry lub powrotu do menu
        } else if (!p1.isAlive()) {
            Gdx.app.log("GAME", "Player 2 wins!");
            // Logika dla zwycięstwa gracza 2
        } else if (!p2.isAlive()) {
            Gdx.app.log("GAME", "Player 1 wins!");
            // Logika dla zwycięstwa gracza 1
        }
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
