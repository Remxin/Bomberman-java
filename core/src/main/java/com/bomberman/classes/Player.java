package com.bomberman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 position;
    private static final float SPEED = 200f;

    private BombManager bombManager;
    private boolean isAlive = true;


    public Player(Vector2 spawnPosition, BombManager bombManager) {
        this.position = new Vector2(spawnPosition);
        this.bombManager = bombManager;
    }

//    public void speedUp(double velocity) {
//        currentSpeed += velocity;
//        if (currentSpeed > MAX_SPEED) {
//            currentSpeed = MAX_SPEED;
//        }
//    }
//
//    public void speedDown(double velocity) {
//        currentSpeed -= velocity;
//        if (currentSpeed < 0) {
//            currentSpeed = 0;
//        }
//    }
//
//    public void move(Direction direction) {
//        switch (direction) {
//            case UP:
//                position.y -= currentSpeed;
//                break;
//            case DOWN:
//                position.y += currentSpeed;
//                break;
//            case LEFT:
//                position.x -= currentSpeed;
//                break;
//            case RIGHT:
//                position.x += currentSpeed;
//                break;
//        }
//    }


    public void update(float delta){
        if(!isAlive) return;

        int dx = 0;
        int dy = 0;
        if      (Gdx.input.isKeyPressed(Input.Keys.UP))    dy =  1;
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  dy = -1;
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  dx = -1;
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dx =  1;

        position.x += dx * SPEED * delta;
        position.y += dy * SPEED * delta;

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            placeBomb();
        }
    }


    public void placeBomb() {
        int gridX = (int)(position.x / bombManager.getTileSize());
        int gridY = (int)(position.y / bombManager.getTileSize());
        bombManager.placeBomb(gridX, gridY);
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void die() {
        isAlive = false;
    }

}
