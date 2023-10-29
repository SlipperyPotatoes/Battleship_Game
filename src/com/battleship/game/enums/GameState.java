package com.battleship.game.enums;

/**
 * TODO ADD COMMENT.
 */
public enum GameState {
    MAIN_MENU("main menu"),
    PLAYER_1_ATTACK("player 1 attack"),
    PLAYER_2_ATTACK("player 2 attack"),
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
