package com.battleship.game.utils;

import com.battleship.game.ShipData;
import com.battleship.game.enums.Direction;
import com.battleship.game.Game;

public class ShipPlacementUtil {

    public static boolean checkForCollisions(int[][] currentShips, int shipLen,
                                             Vector pos, Direction direction) {
        // Check if ship's main position lands out of bounds
        if (pos.getY() < 0 || pos.getX() < 0 ||
            pos.getY() >= Game.SIZE_Y || pos.getX() >= Game.SIZE_X) {
            return false;
        }

        // Check if rest of ship lands out of bounds
        // altPos is the other end of the ship
        Vector altPos = new Vector(pos);
        altPos.add(direction.getVal().scale(shipLen));
        if (altPos.getY() < 0 || altPos.getX() < 0 ||
                altPos.getY() >= Game.SIZE_Y || altPos.getX() >= Game.SIZE_X) {
            return false;
        }

        // Check if ship collides with any other ships
        Vector checkPos = new Vector(pos);
        for (int l = 0; l < shipLen; l++) {
            if (currentShips[checkPos.getY()][checkPos.getX()] != 0) {
                return false;
            }
            checkPos.add(direction.getVal());
        }

        return true;
    }

    public static boolean checkFollowsBotRules() {
        //TODO: At meeting figure this out
        return true;
    }

    // Always makes ships face either north or east since they are equivalent to their counterparts
    public static ShipData getShipAt(int[][] ships, Vector pos) {
        int size = ships[pos.getY()][pos.getX()];

        boolean vertical = false;

        if (pos.getX() > 0) {
            vertical = ships[pos.getY()][pos.getX() - 1] != 0;
        }
        if (pos.getX() < Game.SIZE_X - 1) {
            vertical = vertical || ships[pos.getY()][pos.getX() + 1] != 0;
        }

        Direction direction = vertical ? Direction.NORTH : Direction.EAST;


    }

    public static boolean[][] convertIntToBoolArray(int[][] ints) {
        boolean[][] bools = new boolean[ints.length][ints[0].length];

        for (int y = 0; y < ints[0].length; y++) {
            for (int x = 0; x < ints.length; x++) {
                bools[y][x] = ints[y][x] != 0;
            }
        }

        return bools;
    }
}
