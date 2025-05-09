package com.bomberman.classes;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private final Vector2 position;
    private final Rectangle bounds;
    private final float tileSize;
    public PlayerColor color;

    private boolean isAlive = true;

    public Player(Vector2 spawnPos, float tileSize, PlayerColor color) {
        this.tileSize = tileSize;
        this.color = color;
        this.position = new Vector2(spawnPos);
        this.bounds = new Rectangle(position.x, position.y, tileSize, tileSize);
    }

    public PlayerColor getColor() {
        return color;
    }

    public void move(float dx, float dy) {
        if (!isAlive) return;
        position.add(dx, dy);
        bounds.setPosition(position.x, position.y);
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
    public void die() {
        isAlive = false;
        System.out.println("Player died!");
    }

    // Konwertuje współrzędną świata na indeks siatki
    public static int toGrid(float worldCoord, float tileSize) {
        return (int)(worldCoord / tileSize);
    }
}
