package com.bomberman.classes;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private static int MAX_SPEED = 200;

    private Vector2 position;
    private double currentSpeed;
    private boolean isAlive = true;
    private BombManager bombManager;


    public Player(Vector2 position, BombManager bombManager) {
        this.position = new Vector2(position);
        this.bombManager = bombManager;
    }

    public Vector2 getPosition() {
        return position;
    }


    public void speedUp(double velocity) {
        currentSpeed += velocity;
        if (currentSpeed > MAX_SPEED) {
            currentSpeed = MAX_SPEED;
        }
    }

    public void speedDown(double velocity) {
        currentSpeed -= velocity;
        if (currentSpeed < 0) {
            currentSpeed = 0;
        }
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                position.y -= currentSpeed;
                break;
            case DOWN:
                position.y += currentSpeed;
                break;
            case LEFT:
                position.x -= currentSpeed;
                break;
            case RIGHT:
                position.x += currentSpeed;
                break;
        }
    }

    public void placeBomb() {
        int gridX = (int)(position.x / bombManager.getTileSize());
        int gridY = (int)(position.y / bombManager.getTileSize());
        bombManager.placeBomb(gridX, gridY);
    }

    public void die() {
        currentSpeed = 0;
        isAlive = false;
    }
}
