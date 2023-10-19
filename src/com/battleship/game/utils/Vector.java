package com.battleship.game.utils;

public class Vector {
    private int x;
    private int y;

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Copies an existing vector
    public Vector(Vector vec) {
        this.x = vec.getX();
        this.y = vec.getY();
    }

    // Converts a string in the format "x y" to a vector
    public Vector(String vecStr) {
        int spaceIndex = vecStr.indexOf(" ");
        this.x = Integer.parseInt(vecStr.substring(0, spaceIndex));
        this.y = Integer.parseInt(vecStr.substring(spaceIndex + 1));
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

    public Vector add(Vector vec) {
        x += vec.getX();
        y += vec.getY();
        return this;
    }

    public Vector scale(int scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    // Converts the vector object into the format "x y"
    @Override
    public String toString() {
        return x + " " + y;
    }
}
