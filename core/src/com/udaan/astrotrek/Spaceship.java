package com.udaan.astrotrek;

public class Spaceship {
    public static final int STABLE = 0;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private int x, y;
    private int speed;
    private float tick;
    private boolean handicap = false;

    public Spaceship(int x, int y, int speed, float tick) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.tick = tick;
    }

    public void goLeft() {
        speed += LEFT;
    }

    public void goRight() {
        speed += RIGHT;
    }

    public void move() {
        x = x + speed;
    }git

    public boolean checkCrash(int x, int y) {
        if(handicap)
            return false;

        x = x + 16;
        y = y + 16;
        return x > this.x && x < this.x + 64 - 1 && y > this.y && y < this.y + 42 - 1;
    }

    public boolean checkCollect(int x, int y) {
        x = x + 16;
        y = y + 16;
        return x > this.x && x < this.x + 64 - 1 && y > this.y && y < this.y + 42 - 1;
    }
    
    public boolean checkOutBounds() {
        return (x > 320 || x < -58);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getTick() {
        return tick;
    }

    public void setTick(float timeInitial) {
        this.tick = tick;
    }

    public boolean isHandicap() {
        return handicap;
    }

    public void setHandicap(boolean handicap) {
        this.handicap = handicap;
    }
}
