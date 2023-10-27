package com.battleship.game.logic;

import com.battleship.game.enums.Direction;
import com.battleship.game.utils.Vector;

public class ShipData {
    private final int size;
    private final String name;
    private Vector position;
    private Direction direction;


    public ShipData(Vector position, Direction direction, int size, String name) {
        this.position = position;
        this.direction = direction;
        this.size = size;
        this.name = name;
    }

    public void rotateShip() {
        direction = direction.switchDirection();
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getAltPosition() {
        return position.iAdd(direction.getVec().scale(size - 1));
    }

    @Override
    public String toString() {
        return "Pos: " + position.toString() + ", Direction: " + direction.getVec() + ", Size: " + size;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}
