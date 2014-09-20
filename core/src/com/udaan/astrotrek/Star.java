package com.udaan.astrotrek;

public class Star {

    private int x;
    private int y;
    private String type;
    private float tick;

    public Star(int x, float tick) {
        this.x = x;
        y = 470;
        //this.type = type;
        this.tick = tick;
    }

    public Star() {
        x = 530;
        y = 530;
        tick = 100.0f;

    };

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getTick() {
        return tick;
    }

    public void setTick(float tick) {
        this.tick = tick;
    }
}
