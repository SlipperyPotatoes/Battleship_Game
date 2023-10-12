package com.battleship.game;

public abstract class Game {
    public final static int SIZE_X = 10;
    public final static int SIZE_Y = 10;

    Main main;

    public Game(Main main) {
        this.main = main;
    }

    public abstract void startNewGame();

    public abstract void startSavedGame(String saveString);
}
