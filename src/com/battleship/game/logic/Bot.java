package com.battleship.game.logic;


import com.battleship.game.botfiles.*;
import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.enums.Direction;
import com.battleship.game.utils.Vector;

import java.util.Random;

import static com.battleship.game.utils.ShipUtils.*;

public class Bot {
    BotAlgorithm algorithm;
    BotGuessing botGuessing;

    public Bot(BotAlgorithm algorithm, Ship[][] enemyShips) {
        this.algorithm = algorithm;
        switch (algorithm) {
            case SIMPLE -> botGuessing = new BotGuessingRandom(enemyShips);
            case ADVANCED -> botGuessing = new BotGuessingAlgorithm(enemyShips);
            default -> throw new IllegalStateException("BotAlgorithm not simple nor advanced");
        }
    }

    public Bot(BotAlgorithm algorithm, Ship[][] enemyShips, PlayerData botData) {
        this.algorithm = algorithm;
        switch (algorithm) {
            case SIMPLE -> botGuessing = new BotGuessingRandom(enemyShips, botData);
            case ADVANCED -> botGuessing = new BotGuessingAlgorithm(enemyShips, botData);
            default -> throw new IllegalStateException("BotAlgorithm not simple nor advanced");
        }
    }


    public Ship[][] generateShipPositions() {
        switch (algorithm) {
            case SIMPLE -> {
                return new BotPlacingRandom().botPlacingRandom();
            }
            case ADVANCED -> {
                return new BotPlacingAlgorithm().botPlacingAlgorithm();
            }
            default -> throw new IllegalStateException("BotAlgorithm not simple nor advanced");
        }
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

    public Vector findNextAttackPos() {
        return botGuessing.findNextAttack();
    }
}
