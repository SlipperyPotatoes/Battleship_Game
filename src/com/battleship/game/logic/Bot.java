package com.battleship.game.logic;


import com.battleship.game.botfiles.*;
import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.utils.Vector;

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

    public Bot(BotAlgorithm algorithm, PlayerData enemyData, BotSaveData botSaveData) {
        this.algorithm = algorithm;
        switch (algorithm) {
            case SIMPLE -> botGuessing = new BotGuessingRandom(enemyData, botSaveData);
            case ADVANCED -> botGuessing = new BotGuessingAlgorithm(enemyData, botSaveData);
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

    public Vector findNextAttackPos() {
        return botGuessing.findNextAttack();
    }

    public BotSaveData getBotSaveData() {
        return botGuessing.toSaveData();
    }
}
