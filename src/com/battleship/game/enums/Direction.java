package com.battleship.game.enums;

import com.battleship.game.utils.Vector;

import java.util.Random;

public enum Direction {
    VERTICAL(new Vector(0, 1)),
    HORIZONTAL(new Vector(1, 0));

    private final Vector val;

    private static final Direction[] directions = values();

    Direction(Vector val) {
        this.val = val;
    }

    public Vector getVec() {
        return new Vector(val);
    }

    // Returns the vector of this enum with its values scaled by -1
    public Vector getNVec() {
        return getVec().scale(-1);
    }

    public Direction switchDirection() {
        return this == Direction.VERTICAL ? Direction.HORIZONTAL : this;
    }

    public static Direction getRandomDirection(Random random) {
        return directions[random.nextInt(directions.length)];
    }
}
