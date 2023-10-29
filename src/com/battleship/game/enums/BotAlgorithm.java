package com.battleship.game.enums;

/**
 * Enum for representing the user's choice for either the simpler or more advanced bot algorithm.
 */
public enum BotAlgorithm {
    SIMPLE("simple"),
    ADVANCED("advanced");

    private final String val;

    BotAlgorithm(String val) {
        this.val = val;
    }

    /**
     * Returns the next enum in the list of enums. 
     * Since there are only two entries here, this just switches between simple and advanced.
     */
    public BotAlgorithm next() {
        BotAlgorithm[] values = BotAlgorithm.values();
        return values[(this.ordinal() + 1) % values.length];
    }

    @Override
    public String toString() {
        return val;
    }
}
