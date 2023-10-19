package com.battleship.game.enums;

public enum BotAlgorithmChoice {
    SIMPLE("simple"),
    ADVANCED("advanced");

    private final String val;

    BotAlgorithmChoice(String val) {
        this.val = val;
    }

    public BotAlgorithmChoice next() {
        BotAlgorithmChoice[] values = BotAlgorithmChoice.values();
        return values[(this.ordinal() + 1) % values.length];
    }

    @Override
    public String toString() {
        return val;
    }
}
