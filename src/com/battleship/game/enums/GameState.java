package com.battleship.game.enums;

public enum GameState {
    MAIN_MENU("main menu"),
    PLAYER_ATTACK("player attack"),
    BOT_ATTACK("bot attack"),
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
