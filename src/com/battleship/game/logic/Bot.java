package com.battleship.game.logic;

import com.battleship.game.botfiles.BotGuessing;
import com.battleship.game.botfiles.BotGuessingAlgorithm;
import com.battleship.game.botfiles.BotGuessingRandom;
import com.battleship.game.botfiles.BotPlacingAlgorithm;
import com.battleship.game.botfiles.BotPlacingRandom;
import com.battleship.game.botfiles.BotSaveData;
import com.battleship.game.botfiles.Ship;
import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.utils.Vector;

/**
 * Has the main logic for the bot. Here the bot creates maps and finds attacks.
 */
public class Bot {
    BotAlgorithm algorithm;
    BotGuessing botGuessing;

    /**
     * Checks what algorithm the player has selected and initialises the corresponding object.
     */
    public Bot(BotAlgorithm algorithm, Ship[][] enemyShips) {
        this.algorithm = algorithm;
        switch (algorithm) {
            case SIMPLE -> botGuessing = new BotGuessingRandom(enemyShips);
            case ADVANCED -> botGuessing = new BotGuessingAlgorithm(enemyShips);
            default -> throw new IllegalStateException("BotAlgorithm not simple nor advanced");
        }
    }

    /**
     * When the game is loaded it checks what algorithm the player has selected and 
     * initialises the corresponding BotGuessing object.
     */
    public Bot(BotAlgorithm algorithm, PlayerData enemyData, BotSaveData botSaveData) {
        this.algorithm = algorithm;
        switch (algorithm) {
            case SIMPLE -> botGuessing = new BotGuessingRandom(enemyData, botSaveData);
            case ADVANCED -> botGuessing = new BotGuessingAlgorithm(enemyData, botSaveData);
            default -> throw new IllegalStateException("BotAlgorithm not simple nor advanced");
        }
    }


    /**
     * This checks what algorithm the player has selected and generates
     * the ships using the corresponding placing algorithm.
     */
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

    /**
     * Returns a new attack from the previous created guessing algorithm.
    */
    public Vector findNextAttackPos() {
        return botGuessing.findNextAttack();
    }

    // gets the saved data.
    public BotSaveData getBotSaveData() {
        return botGuessing.toSaveData();
    }
}
