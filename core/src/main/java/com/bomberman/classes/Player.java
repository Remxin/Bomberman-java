package com.bomberman.classes;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Player {
    private Vector position;
    private int maxHealth = 3;
    private int currentHealth;
    private double maxSpeed;
    private double currentSpeed;

    public Player(int maxHealth, Vector position) {
        this.position = new Vector(0.0, 0.0);
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.position = position;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void takeDamage() {
        currentHealth -= 1;
    }

    public void speedUp(double velocity) {
        currentSpeed += velocity;
        if (currentSpeed > maxSpeed) {
            currentSpeed = maxSpeed;
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
        throw new NotImplementedException();
    }

    public void die() {
        currentHealth = 0;
        currentSpeed = 0;
        maxSpeed = 0;
    }
}
