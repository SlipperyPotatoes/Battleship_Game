package com.battleship.game.utils;

import com.battleship.game.logic.ShipData;

public class Vector {
    public static Vector ZERO = new Vector(0, 0);

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

    // Adds another vector to this vector and returns a new result, not affecting either vec
    public Vector iAdd(Vector vec) {
        return new Vector(x + vec.getX(), y + vec.getY());
    }

    public Vector scale(int scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    public Vector iScale(int scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    // Converts the vector object into the format "x y"
    // Checks is this vector is within an array at array[y][x]

    public boolean isWithinArray(int[][] array) {
        return !(x < 0 || y < 0 || y >= array.length || x >= array[0].length);
    }
    public boolean isWithinArray(ShipData[][] array) {
        return !(x < 0 || y < 0 || y >= array.length || x >= array[0].length);
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

    @Override
    public boolean equals(Object obj) {
        Vector vec = (Vector) obj;
        return vec.getX() == x && vec.getY() == y;
    }
}
