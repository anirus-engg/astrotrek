package com.udaan.astrotrek;

public class Asteroid {
    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_3 = 2;
    public static final int TYPE_4 = 3;
    public static final int TYPE_5 = 4;
    public static final int TYPE_6 = 5;
    public static final int TYPE_7 = 6;
    public static final int TYPE_8 = 7;
    public static final int TYPE_9 = 8;
    public static final int TYPE_10 = 9;
    public static final int TYPE_11 = 10;
    public static final int TYPE_12 = 11;
    public static final int TYPE_13 = 12;
    public static final int TYPE_14 = 13;
    public static final int TYPE_15 = 14;

    enum AsteroidState {
        Active,
        Explode,
        Destroy,
        Clear
    }

    private int x, y;
    private int type;
    private float tick;
    private float tickTime = 0.0f;
    private AsteroidState state = AsteroidState.Active;


    public Asteroid(int x, int type, float tick) {
        this.x = x;
        y = 448;
        this.type = type;
        this.tick = tick;
    }

    public boolean checkCrash(int y, int x) {
        y = y + 25;
        return this.x == x && this.y >= y;
    }

    public void move() {
        y = y - 1;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getTick() {
        return tick;
    }

    public void setTick(float tick) {
        this.tick = tick;
    }

    public float getTickTime() {
        return tickTime;
    }

    public void setTickTime(float tickTime) {
        this.tickTime = tickTime;
    }

    public AsteroidState getState() {
        return state;
    }

    public void setState(AsteroidState state) {
        this.state = state;
    }
}
