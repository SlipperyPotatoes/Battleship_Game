package com.battleship.game.logic;

import com.battleship.game.botfiles.Ship;
import com.battleship.game.utils.ShipUtils;
import com.battleship.game.utils.Vector;

import java.util.ArrayList;

import static com.battleship.game.utils.ShipUtils.convertShipGridToShipArray;

// Player can be a user (referred to as humans) or bot
public class PlayerData {
    private boolean shipsSet;
    private Ship[][] ships;
    private boolean[][] placesBeenAttacked;

    public PlayerData() {
        shipsSet = false;
        ships = new Ship[Game.SIZE_Y][Game.SIZE_X];
        placesBeenAttacked = new boolean[Game.SIZE_Y][Game.SIZE_X];
    }

    public PlayerData(Ship[][] ships, boolean[][] placesBeenAttacked) {
        shipsSet = true;
        this.ships = ships;
        this.placesBeenAttacked = placesBeenAttacked;
    }

    public void setShips(Ship[][] ships) {
        if (shipsSet) {
            throw new RuntimeException("Ships positions shouldn't be changed");
        }
        shipsSet = true;
        this.ships = ships;
    }

    public Ship[][] getShipGrid() {
        return ships;
    }

    public ArrayList<Ship> getShipArray() {
        return convertShipGridToShipArray(ships);
    }


    public boolean[][] getPlacesBeenAttacked() { return placesBeenAttacked; }

    public boolean areShipsSet() {
        return shipsSet;
    }

    public boolean shipAt(Vector vec) {
        return ships[vec.getY()][vec.getX()] != null;
    }
    public boolean shipAt(int x, int y) {
        return ships[y][x] != null;
    }

    public void botAttackAt(Vector vec) {
        placesBeenAttacked[vec.getY()][vec.getX()] = true;
    }

    public void attackAt(Vector vec) {
        placesBeenAttacked[vec.getY()][vec.getX()] = true;
        if (shipAt(vec)) {
            ships[vec.getY()][vec.getX()].hit();
        }
    }

    public boolean hasBeenAttackedAt(int x, int y) {
        return placesBeenAttacked[y][x];
    }

    public boolean allShipsDead() {
        for (int y = 0; y < ships.length; y++) {
            for (int x = 0; x < ships[0].length; x++) {
                Ship ship = ships[y][x];
                if (ship == null) {
                    continue;
                }
                if (!ship.isDead()) {
                    return false;
                }
            }
        }

        return true;
    }
}
