package com.battleship.game.logic;

// TODO DONT KNOW WHAT TO CHANGE HERE.
import com.battleship.game.botfiles.*;
import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.utils.Vector;

/**
 * this is main logic for the bot here the bot creates maps, and finds attacks.
 */
public class Bot {
    BotAlgorithm algorithm;
    BotGuessing botGuessing;

    /**
     * this checks what algorithm the player has selected and initialises the corresponding file.
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
     * initialises the corresponding file.
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
     *  this checks what algorithm the player has selected and generates 
     *  the ships at the corresponding file.
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
     * this returns a new attack from the previous created guessingalgorithm.
    */
    public Vector findNextAttackPos() {
        return botGuessing.findNextAttack();
    }

    // gets the saved data.
    public BotSaveData getBotSaveData() {
        return botGuessing.toSaveData();
    }
}
