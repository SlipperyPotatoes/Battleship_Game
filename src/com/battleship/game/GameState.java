package com.battleship.game;

public enum GameState {
    MAIN_MENU("main menu"),
    PLAYER_ATTACK("player_attack"),
    PLACE_SHIPS("place ships");

    private final String val;

    GameState(String val) {
        this.val = val;
    }


    @Override
    public String toString() {
        return val;
    }
}
