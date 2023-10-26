package com.battleship.game.logic;


import com.battleship.game.botfiles.BotGuessingAlgorithm;
import com.battleship.game.botfiles.BotGuessingRandom;
import com.battleship.game.botfiles.BotPlacingAlgorithm;
import com.battleship.game.botfiles.Ship;
import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.enums.Direction;
import com.battleship.game.utils.Vector;

import java.util.Random;

import static com.battleship.game.utils.ShipUtils.*;

public class Bot {
    BotAlgorithm algorithm;
    BotGuessingRandom botGuessingRandom;
    BotGuessingAlgorithm botGuessingAlgorithm;

    public Bot(BotAlgorithm algorithm, Ship[][] enemyShips) {
        this.algorithm = algorithm;
        this.botGuessingRandom = new BotGuessingRandom();
        this.botGuessingAlgorithm = new BotGuessingAlgorithm();
    }

    public Ship[][] generateShipPositions() {
        return new BotPlacingAlgorithm().botPlacingAlgorithm();
    }


    // Completely random positions for now
    /*public ShipData[][] generateShipPositions() {
        ShipData[][] ships = new ShipData[Game.SIZE_Y][Game.SIZE_X];
        Random random = new Random();

        Vector position = new Vector();
        Direction direction;
        for (int i = 0; i < Game.SHIP_SIZES.length; i++) {
            int shipSize = Game.SHIP_SIZES[i];

            while (true) {
                position.setX(random.nextInt(Game.SIZE_X));
                position.setY(random.nextInt(Game.SIZE_Y));
                direction = Direction.getRandomDirection(random);
                if (canPlaceAt(ships, shipSize, position, direction)) {
                    //TODO: Add check for if follows bot rules
                    ShipData ship = new ShipData(position, direction, shipSize, Game.SHIP_NAMES[i]);
                    for (int l = 0; l < shipSize; l++) {
                        ships[position.getY()][position.getX()] = ship;
                        position.add(direction.getVec());
                    }

                    break;
                }
            }
        }

        return ships;
    }*/

    public Vector findNextAttackPos(boolean[][] placesAttacked) {
        //TODO: Implement both simple and advanced attack algorithms
        switch (algorithm) {
            case SIMPLE: {
                Vector attackPos = botGuessingRandom.botGuessingRandom();
                break;
            }
            case ADVANCED: {
                break;
            }
            default: {
                throw new RuntimeException("No bot algorithm selected");
            }
        }


        return new Vector();
    }
}
