package com.battleship.game.utils;

import com.battleship.game.botfiles.Ship;
import com.battleship.game.enums.Direction;
import com.battleship.game.logic.ShipData;

import java.util.ArrayList;

public class ShipUtils {
    public static boolean canPlaceAt(ShipData[][] currentShips, ShipData newShip) {
        return canPlaceAt(currentShips, newShip.getSize(),
                newShip.getPosition(), newShip.getDirection());
    }

    public static boolean canPlaceAt(ShipData[][] currentShips, int shipSize,
                                     Vector pos, Direction direction) {
        // Check if ship's main position lands out of bounds
        if (!pos.isWithinArray(currentShips)) {
            return false;
        }

        // Check if rest of ship lands out of bounds
        // Finds the position of the other end of the ship by adding the main position
        // by the ships direction vector scaled by its size - 1
        if (!pos.iAdd(direction.getVec().scale(shipSize - 1)).isWithinArray(currentShips)) {
            return false;
        }

        Vector checkPos = new Vector(pos);

        // Checks if there is any boats behind this boat
        if (checkPos.iAdd(direction.getNVec()).isWithinArray(currentShips)) {
            Vector behind = checkPos.iAdd(direction.getNVec());
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

            if (checkPos.iAdd(altDirection.getVec()).isWithinArray(currentShips)) {
                Vector leftSide = checkPos.iAdd(altDirection.getVec());
                if (currentShips[leftSide.getY()][leftSide.getX()] != null) {
                    return false;
                }
            }

            if (checkPos.iAdd(altDirection.getNVec()).isWithinArray(currentShips)) {
                Vector rightSide = checkPos.iAdd(altDirection.getNVec());
                if (currentShips[rightSide.getY()][rightSide.getX()] != null) {
                    return false;
                }
            }

            checkPos.add(direction.getVec());
        }

        // checkPos is already at the cell in front of the end of the boat due to the loop's structure before it
        // Checks if there is any boats in front of this boat
        if (checkPos.isWithinArray(currentShips)) {
            // Since this is the last statement, the value can be returned instead of checked then returned
            return currentShips[checkPos.getY()][checkPos.getX()] == null;
        }


        return true;
    }

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

    public static String convertShipDataGridToString(ShipData[][] ships) {
        StringBuilder builder = new StringBuilder();
        for (ShipData[] row : ships) {
            for (ShipData ship : row) {
                if (ship == null) {
                    builder.append(" ");
                } else {
                    builder.append(ship.getSize());
                }
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    /*public static boolean canPlaceAt(int[][] currentShips, ShipData ship) {
        return canPlaceAt(currentShips, ship.getSize(),
                ship.getPosition(), ship.getDirection());
    }

    public static boolean canPlaceAt(int[][] currentShips, int shipSize,
                                     Vector pos, Direction direction) {
        // Check if ship's main position lands out of bounds
        if (!pos.isWithinArray(currentShips)) {
            return false;
        }

        // Check if rest of ship lands out of bounds
        // Finds the position of the other end of the ship by adding the main position
        // by the ships direction vector scaled by its size - 1
        if (!pos.iAdd(direction.getVec().scale(shipSize - 1)).isWithinArray(currentShips)) {
            return false;
        }

        Vector checkPos = new Vector(pos);

        // Checks if there is any boats behind this boat
        if (checkPos.iAdd(direction.getNVec()).isWithinArray(currentShips)) {
            Vector behind = checkPos.iAdd(direction.getNVec());
            if (currentShips[behind.getY()][behind.getX()] != 0) {
                return false;
            }
        }

        // Check if there are any boats colliding with this new boat or next
        // to the sides of this boat
        Direction altDirection = direction.switchDirection();
        for (int l = 0; l < shipSize; l++) {
            if (currentShips[checkPos.getY()][checkPos.getX()] != 0) {
                return false;
            }

            if (checkPos.iAdd(altDirection.getVec()).isWithinArray(currentShips)) {
                Vector leftSide = checkPos.iAdd(altDirection.getVec());
                if (currentShips[leftSide.getY()][leftSide.getX()] != 0) {
                    return false;
                }
            }

            if (checkPos.iAdd(altDirection.getNVec()).isWithinArray(currentShips)) {
                Vector rightSide = checkPos.iAdd(altDirection.getNVec());
                if (currentShips[rightSide.getY()][rightSide.getX()] != 0) {
                    return false;
                }
            }

            checkPos.add(direction.getVec());
        }

        // checkPos is already at the cell in front of the end of the boat due to the loop's structure before it
        // Checks if there is any boats in front of this boat
        if (checkPos.isWithinArray(currentShips)) {
            // Since this is the last statement, the value can be returned instead of checked then returned
            return currentShips[checkPos.getY()][checkPos.getX()] == 0;
        }


        return true;
    }

    public static boolean followsBotRules() {
        //TODO: At meeting figure this out
        return true;
    }

    // Always makes ships face either north or east since they are equivalent to their counterparts
    public static ShipData getShipAt(int[][] ships, Vector pos) {
        int size = ships[pos.getY()][pos.getX()];

        boolean vertical = false;

        if (pos.getY() > 0) {
            vertical = ships[pos.getY() - 1][pos.getX()] != 0;
        }
        if (pos.getY() < Game.SIZE_Y - 1) {
            vertical = vertical || ships[pos.getY() + 1][pos.getX()] != 0;
        }

        Direction direction = vertical ? Direction.VERTICAL : Direction.HORIZONTAL;

        Vector checkPos = new Vector(pos);
        do {
            checkPos.add(direction.getNVec());
        } while (ships[checkPos.getY()][checkPos.getX()] != 0);

        checkPos.add(direction.getNVec());

        return new ShipData(checkPos, direction, size);
    }

    public static boolean[][] convertIntToBoolArray(int[][] intArr) {
        boolean[][] boolArr = new boolean[intArr.length][intArr[0].length];

        for (int y = 0; y < intArr[0].length; y++) {
            for (int x = 0; x < intArr.length; x++) {
                boolArr[y][x] = intArr[y][x] != 0;
            }
        }

        return boolArr;
    }

    public static String convertIntArrayToString(int[][] ints) {
        StringBuilder builder = new StringBuilder();
        for (int[] row : ints) {
            for (int cell : row) {
                if (cell == 0) {
                    builder.append(" ");
                } else {
                    builder.append(cell);
                }
            }
            builder.append("\n");
        }

        return builder.toString();
    }*/
}
