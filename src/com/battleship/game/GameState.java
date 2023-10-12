package com.battleship.game;

public enum GameState {
    MAIN_MENU("main menu"),
    ATTACK("attack"),
    PLACE_SHIPS("place ships");

    private final String val;

    private GameState(String val) {
        this.val = val;
    }


    @Override
    public String toString() {
        return val;
    }
}
