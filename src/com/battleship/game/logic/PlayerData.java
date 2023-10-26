package com.battleship.game.logic;

import com.battleship.game.botfiles.Ship;

// Player can be a user (referred to as humans) or bot
public class PlayerData {
    private boolean shipsSet;
    private Ship[][] ships;
    private boolean[][] placesAttacked;

    public PlayerData() {
        shipsSet = false;
        ships = new Ship[Game.SIZE_Y][Game.SIZE_X];
        placesAttacked = new boolean[Game.SIZE_Y][Game.SIZE_X];
    }

    public PlayerData(Ship[][] ships, boolean[][] placesAttacked) {
        shipsSet = true;
        this.ships = ships;
        this.placesAttacked = placesAttacked;
    }

    public void setShips(Ship[][] ships) {
        if (shipsSet) {
            throw new RuntimeException("Ships positions shouldn't be changed");
        }
        shipsSet = true;
        this.ships = ships;
    }

    public Ship[][] getShips() {
        return ships;
    }

    public boolean[][] getPlacesAttacked() { return placesAttacked; }

    public boolean areShipsSet() {
        return shipsSet;
    }

    public boolean shipAt(int x, int y) {
        return ships[y][x] != null;
    }

    public void attack(int x, int y) {
        placesAttacked[y][x] = true;
    }

    public boolean hasAttackedAt(int x, int y) {
        return placesAttacked[y][x];
    }
}
