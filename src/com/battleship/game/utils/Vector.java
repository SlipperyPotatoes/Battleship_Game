package com.battleship.game.utils;

import com.battleship.game.logic.ShipData;

/**
 * Class for creating vector objects with store pair of integers (x,y) and some methods.
 */
public class Vector {
    public static Vector ZERO = new Vector(0, 0);

    private int x;
    private int y;

    /**
     * Creates a vector with x,y as 0.
     */
    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Creates a new vector object with the given x and y.
     */
    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copies an existing vector.
     */
    public Vector(Vector vec) {
        this.x = vec.getX();
        this.y = vec.getY();
    }

    
    /**
     * Converts a string in the format "x y" to a vector.
     */
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

    /**
     *  Adds a vector to this vector's data.
     */
    public Vector add(Vector vec) {
        x += vec.getX();
        y += vec.getY();
        return this;
    }

    
    /**
     * Creates a new vector that is this vector added to the Vector vec.
     * Doesn't affect this object's data (i.e. mitigates side effects)
     */
    public Vector indAdd(Vector vec) {
        return new Vector(x + vec.getX(), y + vec.getY());
    }

    /**
     * Scales this vector's data by a scalar.
     */
    public Vector scale(int scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    /**
     * Creates a new vector that is this vector scaled by the scalar.
     * Doesn't affect this object's data (i.e. mitigates side effects)
     */
    public Vector indScale(int scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    /**  
     * Checks is this vector is within an array at array[y][x].
     */
    public boolean isWithinArray(ShipData[][] array) {
        return !(x < 0 || y < 0 || y >= array.length || x >= array[0].length);
    }

    /**
     * Converts the vector object into a string with the format "x y".
     */
    @Override
    public String toString() {
        return x + " " + y;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        Vector vec = (Vector) obj;
        return vec.getX() == x && vec.getY() == y;
    }
}
