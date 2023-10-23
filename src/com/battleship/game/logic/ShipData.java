package com.battleship.game.logic;

import com.battleship.game.enums.Direction;
import com.battleship.game.utils.Vector;

public class ShipData {
    private Vector position;
    private Direction direction;
    private final int size;
    private int hp;

    public ShipData(Vector position, Direction direction, int size) {
        this.position = position;
        this.direction = direction;
        this.size = size;
        this.hp = size;
    }

    public void rotateShip() {
        direction = direction.switchDirection();
    }

    public void hitShip() {
        hp--;
    }

    public boolean isDead() {
        return hp == 0;
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

}
