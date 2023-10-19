package com.battleship.game;

// Player can be a user (human) or bot
public class PlayerData {
    boolean shipsSet;
    boolean[][] ships;
    boolean[][] placesAttacked;

    PlayerData() {
        shipsSet = false;
        ships = new boolean[Game.SIZE_Y][Game.SIZE_X];
        placesAttacked = new boolean[Game.SIZE_Y][Game.SIZE_X];
    }

    public void setShips(boolean[][] ships) {
        if (shipsSet) {
            throw new RuntimeException("Ships positions shouldn't be changed");
        }
        shipsSet = true;
        this.ships = ships;
    }

    public boolean[][] getShips() {
        return ships;
    }

    public boolean shipsSet() {
        return shipsSet;
    }

    public boolean shipAt(int y, int x) {
        return ships[y][x];
    }

    public boolean hasAttackedAt(int y, int x) {
        return placesAttacked[y][x];
    }
}
