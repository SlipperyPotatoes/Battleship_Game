package com.battleship.game.enums;

import com.battleship.game.utils.Vector;

/**
 * Enum used for representing if a ship is vertical or horizontal.
 */
public enum Direction {
    VERTICAL(new Vector(0, 1)),
    HORIZONTAL(new Vector(1, 0));

    private final Vector val;

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
        return this == Direction.VERTICAL ? HORIZONTAL : VERTICAL;
    }
}
