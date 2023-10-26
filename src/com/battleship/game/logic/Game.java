package com.battleship.game.logic;

import com.battleship.game.Main;
import com.battleship.game.botfiles.Ship;
import com.battleship.game.enums.GameState;

import javax.swing.*;
import java.net.URL;

public abstract class Game {
    public final static int SIZE_X = 10;
    public final static int SIZE_Y = 10;
    public final static int[] SHIP_SIZES = {5, 4, 3, 3, 2};
    public final static String[] SHIP_NAMES = {"Aircraft Carrier", "Battleship",
            "Submarine", "Cruiser", "Destroyer"};

    // Ensures before the game runs that all the ships can
    // fit on the grid side by side without issue and
    // that they are in descending order
    // Technically not necessary but useful for debugging
    static {
        assert SHIP_SIZES.length <= SIZE_Y / 2 : "Too many ships in SHIP_SIZES";
        for (int i = 0; i < SHIP_SIZES.length; i++) {
            assert SHIP_SIZES[i] <= SIZE_X : "Ship in SHIP_SIZES larger than width of grid";
            if (i < SHIP_SIZES.length - 1) {
                assert SHIP_SIZES[i] >= SHIP_SIZES[i + 1] : "Ships in SHIP_SIZES not in descending order";
            }
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
            return;
        }
        //TODO: Load game data using url, storing PlayerData for players 1 and 2 in their variables
    }

    public void saveGame() {
        //TODO: Save game data to text file with .save extension,
        // the only thing that needs to be saved is the both player's PlayerData
        main.endGame();
    }

    public void nextAttack() {
        switch (main.getGameState()) {
            case PLAYER_1_ATTACK -> main.changeGameState(GameState.PLAYER_2_ATTACK);
            case PLAYER_2_ATTACK -> main.changeGameState(GameState.PLAYER_1_ATTACK);
            default -> throw new IllegalStateException
                    ("Illegal state reached, nextAttack called on: " + main.getGameState().toString());
        }
    }

    public void setNextPlayerShips(Ship[][] ships) {
        if (!player1.areShipsSet()) {
            player1.setShips(ships);
            return;
        }
        player2.setShips(ships);
    }
}
