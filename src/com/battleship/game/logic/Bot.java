package com.battleship.game.logic;


import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.enums.Direction;
import com.battleship.game.utils.Vector;

import java.util.Random;

import static com.battleship.game.utils.ShipUtils.*;

public class Bot {
    BotAlgorithm algorithm;

    public Bot(BotAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    // Completely random positions for now
    public boolean[][] generateShipPositions() {
        int[] shipSizes = Game.SHIP_SIZES;
        int[][] ships = new int[Game.SIZE_Y][Game.SIZE_X];
        Random random = new Random();

        Vector position = new Vector();
        Direction direction;
        for (int shipSize : shipSizes) {
            while (true) {
                position.setX(random.nextInt(Game.SIZE_X));
                position.setY(random.nextInt(Game.SIZE_Y));
                direction = Direction.getRandomDirection(random);
                if (canPlaceAt(ships, shipSize, position, direction)) {
                    //TODO: Add check for if follows rules
                    for (int l = 0; l < shipSize; l++) {
                        ships[position.getY()][position.getX()] = shipSize;
                        position.add(direction.getVec());
                    }

                    break;
                }
            }
        }

        System.out.println(convertIntArrayToString(ships));

        return convertIntToBoolArray(ships);
    }

    public Vector findNextAttackPos(boolean[][] placesAttacked) {
        //TODO: Implement both simple and advanced attack algorithms
        return new Vector();
    }
}
