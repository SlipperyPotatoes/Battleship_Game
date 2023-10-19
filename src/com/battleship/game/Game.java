package com.battleship.game;

public abstract class Game {
    public final static int SIZE_X = 10;
    public final static int SIZE_Y = 10;
    public final static int[] SHIP_SIZES = {5, 3, 2};

    // Ensures before the game runs that all the ships can
    // fit on the grid side by side without issue and
    // that they are in descending order
    // Technically not necessary but useful for debugging
    static {
        assert SHIP_SIZES.length <= SIZE_Y;
        for (int i = 0; i < SHIP_SIZES.length; i++) {
            assert SHIP_SIZES[i] <= SIZE_X;
            if (i < SHIP_SIZES.length - 1) {
                assert SHIP_SIZES[i] > SHIP_SIZES[i + 1];
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

    public abstract void startSavedGame(String saveString);

    public PlayerData getPlayer1() {
        return player1;
    }

    public PlayerData getPlayer2() {
        return player2;
    }

    public void setPlayer1Ships(boolean[][] ships) {
        player1.setShips(ships);
    }

    public void setPlayer2Ships(boolean[][] ships) {
        player2.setShips(ships);
    }

    public void setNextPlayerShips(boolean[][] ships) {
        if (!player1.shipsSet) {
            player1.setShips(ships);
            return;
        }
        player2.setShips(ships);
    }
}
