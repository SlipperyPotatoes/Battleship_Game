package com.battleship.game.logic;

// Player can be a user (referred to as humans) or bot
public class PlayerData {
    private boolean shipsSet;
    private ShipData[][] ships;
    private boolean[][] placesAttacked;

    PlayerData() {
        shipsSet = false;
        ships = new ShipData[Game.SIZE_Y][Game.SIZE_X];
        placesAttacked = new boolean[Game.SIZE_Y][Game.SIZE_X];
    }

    public void setShips(ShipData[][] ships) {
        if (shipsSet) {
            throw new RuntimeException("Ships positions shouldn't be changed");
        }
        shipsSet = true;
        this.ships = ships;
    }

    public ShipData[][] getShips() {
        return ships;
    }

    public boolean shipsSet() {
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
