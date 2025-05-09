package com.bomberman.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private final Vector2 position;
    private final Vector2 spawnPosition;
    private final Rectangle bounds;
    private final float tileSize;
    public int bombExplosionRadius;

    public int hearth;
    public PlayerColor color;

    private boolean isAlive = true;

    public Player(Vector2 spawnPos, float tileSize, PlayerColor color, int hearth, int bombExplosionRadius) {
        this.tileSize = tileSize;
        this.color = color;
        this.position = new Vector2(spawnPos);
        this.spawnPosition = new Vector2(spawnPos);
        this.bounds = new Rectangle(position.x, position.y, tileSize, tileSize);
        this.hearth = hearth;
        this.bombExplosionRadius = bombExplosionRadius;
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
