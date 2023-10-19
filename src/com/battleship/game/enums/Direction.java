package com.battleship.game.enums;

import com.battleship.game.utils.Vector;

import java.util.Random;

public enum Direction {
    NORTH(new Vector(0, 1)),
    EAST(new Vector(1, 0)),
    SOUTH(new Vector(0, -1)),
    WEST(new Vector(-1, 0));

    private final Vector val;

    private static final Direction[] directions = values();

    Direction(Vector val) {
        this.val = val;
    }

    public Vector getVal() {
        return val;
    }

    public Direction rotateClockwise() {

        for (int i = 0; i < directions.length; i++) {
            if (directions[i] == this) {
                return directions[(i + 1) % directions.length];
            }
        }

        throw new RuntimeException("Clockwise rotated direction not found");
    }

    public static Direction getRandomDirection(Random random) {
        return directions[random.nextInt(directions.length)];
    }
}
