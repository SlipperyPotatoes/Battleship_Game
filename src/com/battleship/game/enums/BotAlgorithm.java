package com.battleship.game.enums;

/**
 * TODO ADD COMMENT.
 */
public enum BotAlgorithm {
    SIMPLE("simple"),
    ADVANCED("advanced");

    private final String val;

    BotAlgorithm(String val) {
        this.val = val;
    }

    /**
     *  TODO ADD COMMENT.
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
