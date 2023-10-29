package com.battleship.game.utils;

import com.battleship.game.botfiles.Ship;
import com.battleship.game.enums.Direction;
import com.battleship.game.logic.PlayerData;
import com.battleship.game.logic.ShipData;
import java.awt.*;
import java.util.ArrayList;

/**
 * Utility class that stores methods for doing ship related things.
 */
public class ShipUtils {

    /**
    * Checks if a ship with the given data can be added to the shipGrid without colliding with other
    * ships or with the edges of the grid.
    *
    * @param currentShips shipGrid before adding the new ship
    * @param shipSize length of the Ship
    * @param pos start position of the ship
    * @param direction whether ship is vertical or horizontal
    *
    * @return if ship can be placed
    */
    public static boolean canPlaceAt(ShipData[][] currentShips, int shipSize,
                                     Vector pos, Direction direction) {
        // Check if ship's main position lands out of bounds
        if (!pos.isWithinArray(currentShips)) {
            return false;
        }

        // Check if rest of ship lands out of bounds
        // Finds the position of the other end of the ship by adding the main position
        // by the ships direction vector scaled by its size - 1
        if (!pos.indAdd(direction.getVec().scale(shipSize - 1)).isWithinArray(currentShips)) {
            return false;
        }

        Vector checkPos = new Vector(pos);

        // Checks if there is any boats behind this boat
        if (checkPos.indAdd(direction.getNVec()).isWithinArray(currentShips)) {
            Vector behind = checkPos.indAdd(direction.getNVec());
            if (currentShips[behind.getY()][behind.getX()] != null) {
                return false;
            }
        }

        // Check if there are any boats colliding with this new boat or next
        // to the sides of this boat
        Direction altDirection = direction.switchDirection();
        for (int l = 0; l < shipSize; l++) {
            if (currentShips[checkPos.getY()][checkPos.getX()] != null) {
                return false;
            }

            if (checkPos.indAdd(altDirection.getVec()).isWithinArray(currentShips)) {
                Vector leftSide = checkPos.indAdd(altDirection.getVec());
                if (currentShips[leftSide.getY()][leftSide.getX()] != null) {
                    return false;
                }
            }

            if (checkPos.indAdd(altDirection.getNVec()).isWithinArray(currentShips)) {
                Vector rightSide = checkPos.indAdd(altDirection.getNVec());
                if (currentShips[rightSide.getY()][rightSide.getX()] != null) {
                    return false;
                }
            }

            checkPos.add(direction.getVec());
        }

        // checkPos is already at the cell in front 
        // of the end of the boat due to the loop's structure before it

        // Checks if there is any boats in front of this boat
        if (checkPos.isWithinArray(currentShips)) {
            // Since this is the last statement,
            // the value can be returned instead of checked then returned
            return currentShips[checkPos.getY()][checkPos.getX()] == null;
        }


        return true;
    }

    /**
    * Converts a shipGrid into an ArrayList with all the unique ships that are on the grid.
    * 
    * @param grid shipGrid
    *
    * @return An ArrayList of all unique ships that exist on the grid
    */
    public static ArrayList<ShipData> convertShipGridToShipArray(ShipData[][] grid) {
        ArrayList<ShipData> shipArray = new ArrayList<>();

        for (ShipData[] row : grid) {
            for (ShipData gridShip : row) {
                if (gridShip == null) {
                    continue;
                }

                boolean notInArray = true;
                for (ShipData ship : shipArray) {
                    if (ship.getPosition().equals(gridShip.getPosition())) {
                        notInArray = false;
                        break;
                    }
                }

                if (notInArray) {
                    shipArray.add(gridShip);
                }
            }
        }

        return shipArray;
    }

    /**
    * Converts a shipGrid into an ArrayList with all the unique ships that are on the grid.
    * 
    * @param grid shipGrid
    *
    * @return An ArrayList of all unique ships that exist on the grid
    */
    public static ArrayList<Ship> convertShipGridToShipArray(Ship[][] grid) {
        ArrayList<Ship> shipArray = new ArrayList<>();

        for (Ship[] row : grid) {
            for (Ship gridShip : row) {
                if (gridShip == null) {
                    continue;
                }

                boolean notInArray = true;
                for (Ship ship : shipArray) {
                    if (ship.getName().equals(gridShip.getName())) {
                        notInArray = false;
                        break;
                    }
                }

                if (notInArray) {
                    shipArray.add(gridShip);
                }
            }
        }

        return shipArray;
    }

    /**
    * Converts a shipGrid with ships represented using the shipData object into a shipGrid
    * with ships represented using the Ship object.
    */
    public static Ship[][] convertShipDataGridToShipGrid(ShipData[][] shipDataGrid) {
        ArrayList<ShipData> shipDataArray = convertShipGridToShipArray(shipDataGrid);

        Ship[][] shipGrid = new Ship[shipDataGrid.length][shipDataGrid[0].length];

        for (ShipData shipData : shipDataArray) {
            Ship ship = new Ship(shipData);
            Vector setPos = new Vector(shipData.getPosition());
            for (int i = 0; i < shipData.getSize(); i++) {
                shipGrid[setPos.getY()][setPos.getX()] = ship;
                setPos.add(shipData.getDirection().getVec());
            }
        }

        return shipGrid;
    }

    /**
    * Checks if the ship with the name shipName in the player's ships has been sunk.
    */
    public static boolean isShipSunk(PlayerData playerData, String shipName) {
        for (Ship ship : playerData.getShipArray()) {
            if (ship.getName().equals(shipName)) {
                return ship.isDead();
            }
        }
        throw new IllegalArgumentException("Ship name, " + shipName + ", does not exist");
    }

    /**
    * Converts a point into a string with the format "x y".
    */
    public static String pointToStr(Point point) {
        return point.x + " " + point.y;
    }

    /**
    * Converts a string with the format "x y" to a point.
    */
    public static Point strToPoint(String str) {
        int spaceIndex = str.indexOf(" ");
        int x = Integer.parseInt(str.substring(0, spaceIndex));
        int y = Integer.parseInt(str.substring(spaceIndex + 1));
        return new Point(x, y);
    }
}
