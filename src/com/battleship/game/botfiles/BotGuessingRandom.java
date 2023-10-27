package com.battleship.game.botfiles;

import com.battleship.game.logic.PlayerData;
import com.battleship.game.utils.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * This bot guesses a random
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class BotGuessingRandom extends BotGuessing {

    private boolean firstShipAttack;
    private boolean attackingShip;
    private boolean attackDirectionFound;
    private String currentDirection;
    private int totalShips;
    Point currentAttack;
    Point firstHit;
    // with the hp of the objects in it
    Ship[][] enemyShips;
    boolean[][] botAttacks;
    List<String> nextDirection;

    //things that do not need to be saved
    Random random = new Random();
    Vector currentBotAttack;

    // Used for creating a bot when starting a new game
    public BotGuessingRandom(Ship[][] enemyShips) {
        whenNewGame(enemyShips);
    }

    // Used for creating a bot based on saved data
    public BotGuessingRandom(PlayerData enemyData) {
        whenLoadGame(enemyData);
    }

    private void whenNewGame(Ship[][] enemyShips) {
        firstShipAttack = true;
        attackingShip = false;
        attackDirectionFound = false;
        totalShips = 5;
        currentAttack = new Point();
        firstHit = new Point();
        this.enemyShips = enemyShips;
        botAttacks = new boolean[10][10];
    }

    private void whenLoadGame(PlayerData enemyData) {
        /** 
         * LOAD THESE
         * 
        firstShipAttack;
        attackingShip;
        attackDirectionFound;
        currentDirection;
        totalShips;
    
        currentAttack;
        firstHit;
        enemyShips;
        currentSquares;
        botAttacks;
        List<String> nextDirection;
        */
    }

    /**
     * 
     * 
     * 
     */

    @Override
    public Vector findNextAttack() {
        if (attackingShip) {
            if (firstShipAttack) {
                firstShipAttack = false;
                firstShipAttack();
            } else if (attackDirectionFound) {
                //if the direction is found we attack in that direction
                attackShipInDirection();
            } else {
                //if the direction is not found we attack around the ship
                attackAroundShip();
            }
        } else {
            randomAttack();
        }
        return currentBotAttack;
    }

    private void firstShipAttack() {
        // stores the first attack to a point for later use
        nextDirection = new ArrayList<>();

        if ((currentAttack.y != 0) && (!botAttacks[currentAttack.y - 1][currentAttack.x])) {
            nextDirection.add("NORTH");
        }
        if ((currentAttack.x != 9) && (!botAttacks[currentAttack.y][currentAttack.x + 1])) {
            nextDirection.add("EAST");        
        }
        if ((currentAttack.y != 9) && (!botAttacks[currentAttack.y + 1][currentAttack.x])) {
            nextDirection.add("SOUTH");
        }
        if ((currentAttack.x != 0) && (!botAttacks[currentAttack.y][currentAttack.x - 1])) {
            nextDirection.add("WEST");
        }
        attackAroundShip();
    }

    private void attackAroundShip() {

        boolean foundNewHit = false;
        while (!foundNewHit) {
            if (nextDirection.size() == 1) {
                currentDirection = nextDirection.get(0);
            } else {
                int listPlace = getRandomNumber(0, (nextDirection.size() - 1));
                currentDirection = nextDirection.get(listPlace);
                nextDirection.remove(listPlace);
            }
            
            switch (currentDirection) {
                case "NORTH":
                    currentAttack.y--;
                    break;
                case "EAST":
                    currentAttack.x++;
                    break;
                case "SOUTH":
                    currentAttack.y++;
                    break;
                case "WEST":
                    currentAttack.x--;
                    break;
                default:
                    break;
            } 
            //if it doesn't collide we can attack
            if (checkCollide()) {
                foundNewHit = true;
            } else {
                // if it does collide we go back to the original point and try again
                currentAttack.x = firstHit.x;
                currentAttack.y = firstHit.y;
            }
        }
        attack();
    }

    private void attackShipInDirection() {
        boolean foundNewHit = false;

        while (!foundNewHit) {
            switch (currentDirection) {
                case "NORTH":
                    if (currentAttack.y != 0) {
                        currentAttack.y--;
                    }
                    break;
                case "EAST":
                    if (currentAttack.x != 9) {
                        currentAttack.x++;
                        }
                        break;
                    case "SOUTH":
                    if (currentAttack.y != 9) {
                        currentAttack.y++;
                    }
                        break;
                    case "WEST":
                    if (currentAttack.x != 0) {
                        currentAttack.x--;
                    }
                        break;
                    default:
                        break;
            }
            if (checkCollide()) {
                foundNewHit = true;
            } else {
                currentAttack.x = firstHit.x;
                currentAttack.y = firstHit.y;
                switchDirection();
            }
        }
        attack();
    }

    private void switchDirection() {
        switch (currentDirection) {
            case "NORTH":
                currentDirection = "SOUTH";
                break;
            case "EAST":
                currentDirection = "WEST";
                break;
            case "SOUTH":
                currentDirection = "NORTH";
                break;
            case "WEST":
                currentDirection = "EAST";
                break;
        } 
    }

    private void randomAttack() {
        boolean foundNewHit = false;
        while (!foundNewHit) {
            currentAttack.y = getRandomNumber(0, 9);
            currentAttack.x = getRandomNumber(0, 9);

            if (checkCollide()) {
                foundNewHit = true;
            }
        }
        attack();
    }

    private void attack() {

        currentBotAttack = new Vector(currentAttack.x, currentAttack.y);

        Ship currentShip = enemyShips[currentAttack.y][currentAttack.x];

        //add the attack to the attack array, so we know what is already attacked
        botAttacks[currentAttack.y][currentAttack.x] = true;
        //if the attack is a hit
        if (currentShip != null) {
            
            //hp of the ship goes down
            currentShip.hp--;
            // if the ship is sunk we stop attacking the ship
            
            // if we were attacking a ship and we hit it
            if (attackingShip) {
                // we found the direction
                attackDirectionFound = true;
            } else {
                //if we weren't already we start attacking the ship
                attackingShip = true;
 
                firstHit.x = currentAttack.x;
                firstHit.y = currentAttack.y;
            }

            if (checkSunk(currentShip)) {
                attackingShip = false;
                firstShipAttack = true;
                attackDirectionFound = false;
            }
        } else {
            if (attackingShip) {
                currentAttack.x = firstHit.x;
                currentAttack.y = firstHit.y;
            }  
        }
    }

    private int getRandomNumber(int start, int end) {
        int number = random.nextInt((end - start) + 1) + start; // see explanation below
        return number;
    }

    private boolean checkCollide() {
        if (botAttacks[currentAttack.y][currentAttack.x]) {
            return false;
        }
        return true;
    }

    private boolean checkSunk(Ship currentShip) {
        if (currentShip.hp == 0) {
            totalShips--;
            if (totalShips == 0) {
                endGame();
            }
            return true;
        }
        return false;
    }

    private void endGame() {
    }

    @Override
    public boolean[][] getPlacesAttacked() {
        return botAttacks;
    }
}
