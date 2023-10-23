package com.battleship.game;

// Player can be a user (referred to as humans) or bot
public class PlayerData {
    private boolean shipsSet;
    private boolean[][] boolShips;
    private boolean[][] placesAttacked;

    PlayerData() {
        shipsSet = false;
        boolShips = new boolean[Game.SIZE_Y][Game.SIZE_X];
        placesAttacked = new boolean[Game.SIZE_Y][Game.SIZE_X];
    }

    public void setBoolShips(boolean[][] boolShips) {
        if (shipsSet) {
            throw new RuntimeException("Ships positions shouldn't be changed");
        }
        shipsSet = true;
        this.boolShips = boolShips;
    }

    public boolean[][] getBoolShips() {
        return boolShips;
    }

    public boolean shipsSet() {
        return shipsSet;
    }

    public boolean shipAt(int x, int y) {
        return boolShips[y][x];
    }

    public void attack(int x, int y) {
        placesAttacked[y][x] = true;
    }

    public boolean hasAttackedAt(int x, int y) {
        return placesAttacked[y][x];
    }
}
