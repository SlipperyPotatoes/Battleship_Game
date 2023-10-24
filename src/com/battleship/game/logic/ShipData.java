package com.battleship.game.logic;

import com.battleship.game.enums.Direction;
import com.battleship.game.utils.Vector;

public class ShipData {
    private Vector position;
    private Direction direction;
    private final int size;
    private int hp;
    private final String name;


    public ShipData(Vector position, Direction direction, int size, String name) {
        this.position = position;
        this.direction = direction;
        this.size = size;
        this.hp = size;
        this.name = name;
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

    public Vector getAltPosition() {
        return position.iAdd(direction.getVec().scale(size - 1));
    }

    @Override
    public String toString() {
        return "Pos: " + position.toString() + ", Direction: " + direction.getVec().toString() + ", Size: " + size;
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

    public String getName() {
        return name;
    }
}
