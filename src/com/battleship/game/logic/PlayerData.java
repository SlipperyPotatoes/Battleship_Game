package com.battleship.game.logic;

import static com.battleship.game.utils.ShipUtils.convertShipGridToShipArray;

import com.battleship.game.botfiles.Ship;
import com.battleship.game.utils.Vector;
import java.util.ArrayList;



/**
 * Class to represent data specific to a player such as where their ships are placed and where
 * they have been attacked.
 * <p>
 * A player can be a user (also referred to as humans) or bot
 */
public class PlayerData {
    private boolean shipsSet;
    private Ship[][] ships;
    private boolean[][] placesBeenAttacked;

    /**
     * Creates a new PlayerData object and initializes all internal variables.
     */
    public PlayerData() {
        shipsSet = false;
        ships = new Ship[Game.SIZE_Y][Game.SIZE_X];
        placesBeenAttacked = new boolean[Game.SIZE_Y][Game.SIZE_X];
    }

    /**
     * Creates a new PlayerData object using the parameters.
     */
    public PlayerData(Ship[][] ships, boolean[][] placesBeenAttacked) {
        shipsSet = true;
        this.ships = ships;
        this.placesBeenAttacked = placesBeenAttacked;
    }

    /**
     * Sets the var ships and throws an exception if the ships have already been set.
     */
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


    public boolean[][] getPlacesBeenAttacked() {
        return placesBeenAttacked;
    }

    public boolean shipsNotSet() {
        return !shipsSet;
    }

    public boolean shipAt(Vector vec) {
        return ships[vec.getY()][vec.getX()] != null;
    }

    public boolean shipAt(int x, int y) {
        return ships[y][x] != null;
    }

    /**
     * Used for the bot attacking since it already has the player's shipGrid, so,
     * it already hits the ship grid itself.
     *
     * @param vec Place to attack at
     */
    public void botAttackAt(Vector vec) {
        placesBeenAttacked[vec.getY()][vec.getX()] = true;
    }

    /**
     * Sets places attacked to true at the position and hits the ship there if there is one.
     * <p>
     * Doesn't check if the place has already been attacked.
     *
     * @param vec Place to attack at
     */
    public void attackAt(Vector vec) {
        placesBeenAttacked[vec.getY()][vec.getX()] = true;
        if (shipAt(vec)) {
            ships[vec.getY()][vec.getX()].hit();
        }
    }

    public boolean hasBeenAttackedAt(int x, int y) {
        return placesBeenAttacked[y][x];
    }

    /**
     * Checks if all ships stored in this object's grid are dead.
     */
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
