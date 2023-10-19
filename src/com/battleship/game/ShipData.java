package com.battleship.game;

import com.battleship.game.enums.Direction;
import com.battleship.game.utils.Vector;

public class ShipData {
    private Vector position;
    private Direction direction;
    private int size;

    public ShipData(Vector position, Direction direction, int size) {
        this.position = position;
        this.direction = direction;
        this.size = size;
    }

    public void rotateShipClockwise() {
        direction = direction.rotateClockwise();
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
