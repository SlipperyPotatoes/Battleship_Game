package com.battleship.game;

import com.battleship.game.enums.GameState;

import javax.swing.*;
import java.net.URL;

public abstract class Game {
    public final static int SIZE_X = 10;
    public final static int SIZE_Y = 10;
    public final static int[] SHIP_SIZES = {5, 4, 3, 3, 2};

    // Ensures before the game runs that all the ships can
    // fit on the grid side by side without issue and
    // that they are in descending order
    // Technically not necessary but useful for debugging
    static {
        assert SHIP_SIZES.length <= SIZE_Y / 2;
        for (int i = 0; i < SHIP_SIZES.length; i++) {
            assert SHIP_SIZES[i] <= SIZE_X;
            assert i >= SHIP_SIZES.length - 1 || SHIP_SIZES[i] > SHIP_SIZES[i + 1];
        }
    }

    PlayerData player1;
    PlayerData player2;

    Main main;

    public Game(Main main) {
        this.main = main;
        this.player1 = new PlayerData();
        this.player2 = new PlayerData();
    }

    public abstract void startNewGame();

    public abstract void nextPlacement();

    public void startSavedGame(String saveString) {
        URL saveURL = getClass()
                .getClassLoader()
                .getResource("com/battleship/game/assets/" + saveString + ".save");

        if (saveURL == null) {
            JOptionPane.showMessageDialog(main.getFrame(),
                    "No existing save found",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        //TODO: Load game data using url
    }

    public void saveGame() {
        //TODO: Save game data to text file with .save extension
    }

    public void nextAttack() {
        switch (main.getGameState()) {
            case PLAYER_1_ATTACK -> main.changeGameState(GameState.PLAYER_2_ATTACK);
            case PLAYER_2_ATTACK -> main.changeGameState(GameState.PLAYER_1_ATTACK);
            default -> throw new RuntimeException
                    ("Illegal state reached, nextAttack called on: " + main.getGameState().toString());
        }
    }

    public void setNextPlayerShips(boolean[][] ships) {
        if (!player1.shipsSet()) {
            player1.setBoolShips(ships);
            return;
        }
        player2.setBoolShips(ships);
    }
}
