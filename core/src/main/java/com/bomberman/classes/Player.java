package com.bomberman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private final Vector2 position;
    private final Vector2 spawnPosition;
    private final Rectangle bounds;
    public int bombExplosionRadius;
    public Map<Direction, Animation<TextureRegion>> walkAnimations;
    public Direction walkDirection = Direction.DOWN;
    public float animationStateTimer = 0f;
    public boolean isMoving = false;
    private final float playerSize;

    public int hearth;
    public PlayerColor color;

    private boolean isAlive = true;

    public Player(Vector2 spawnPos, float tileSize, PlayerColor color, int hearth, int bombExplosionRadius, float playerSize) {
        this.color = color;
        this.position = new Vector2(spawnPos);
        this.spawnPosition = new Vector2(spawnPos);
        this.bounds = new Rectangle(position.x, position.y, tileSize, tileSize);
        this.hearth = hearth;
        this.bombExplosionRadius = bombExplosionRadius;
        this.playerSize = playerSize;
        this.walkAnimations = new HashMap<>();
    }

    public void loadAnimations(Texture spriteSheet, int frameWidth, int frameHeight) {
        TextureRegion[][] splitSheet = TextureRegion.split(spriteSheet, frameWidth, frameHeight);
        int playerColumn = color == PlayerColor.RED ? 2 : 6;

        walkAnimations.put(Direction.DOWN,  createAnimation(splitSheet, 0, playerColumn, 3));
        walkAnimations.put(Direction.RIGHT, createAnimation(splitSheet, 3, playerColumn, 3));
        walkAnimations.put(Direction.LEFT,  createAnimation(splitSheet, 6, playerColumn, 3));
        walkAnimations.put(Direction.UP,    createAnimation(splitSheet, 9, playerColumn, 3));
    }

    private Animation<TextureRegion> createAnimation(TextureRegion[][] sheet, int startRow, int col, int frames) {
        TextureRegion[] regions = new TextureRegion[frames];
        for (int i = 0; i < frames; i++) {
            regions[i] = sheet[startRow + i][col];
        }
        return new Animation<>(0.2f, regions);
    }

    public void setDirection(Direction direction, boolean moving) {
        if (this.walkDirection != direction) {
            this.walkDirection = direction;
        }
        isMoving = moving;
    }

    public void update(float delta) {
        if (isMoving) animationStateTimer += delta;
    }

    public void render(SpriteBatch batch) {
        TextureRegion frame;
        Gdx.app.log("Player", "timer" + animationStateTimer + " isMoving" + isMoving);
        if (isMoving) {
            frame = walkAnimations.get(walkDirection).getKeyFrame(animationStateTimer, true);
        } else {
            frame = walkAnimations.get(walkDirection).getKeyFrame(0); // standing frame
        }
        batch.draw(frame, position.x, position.y, playerSize, playerSize);
    }

    public PlayerColor getColor() {
        return color;
    }

    public void move(float dx, float dy) {
        if (!isAlive) return;
        position.add(dx, dy);
        bounds.setPosition(position.x, position.y);
    }

    public void respawn() {
        position.x = spawnPosition.x;
        position.y = spawnPosition.y;
    }

    public void setPosition(Vector2 newPos) {
        position.set(newPos);
        bounds.setPosition(position.x, position.y);
    }


    public void updateBounds() {
        bounds.setPosition(position.x, position.y);
    }

    public Vector2 getPosition() { return position; }
    public Rectangle getBounds() { return bounds; }

    public boolean isAlive() { return isAlive; }
    public void takeDamage() {
        this.hearth--;
        if (hearth <= 0) {
            die();
        }
    }

    public void die() {
        isAlive = false;
        System.out.println("Player died!");
    }

    // Konwertuje współrzędną świata na indeks siatki
    public static int toGrid(float worldCoord, float tileSize) {
        return (int)(worldCoord / tileSize);
    }
}
