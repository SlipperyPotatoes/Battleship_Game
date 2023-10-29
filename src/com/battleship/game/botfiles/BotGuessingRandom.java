package com.battleship.game.botfiles;

import com.battleship.game.logic.PlayerData;
import com.battleship.game.utils.Vector;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * This is the random algorithm for the attacks of the bot. It has a few rules
 * 1: randomly attack on the board
 * 2: when the bot hits a ship, it will check around the ship and attack the
 *    ship until the ship is sunk
 */
public class BotGuessingRandom extends BotGuessing {

    //things that do not need to be saved
    Random random = new Random();
    Vector currentBotAttack;

    //things that do need to be saved
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

    

    // Used for creating a bot when starting a new game
    public BotGuessingRandom(Ship[][] enemyShips) {
        whenNewGame(enemyShips);
    }

    // Used for creating a bot based on saved data
    public BotGuessingRandom(PlayerData enemyData, BotSaveData botSaveData) {
        whenLoadGame(enemyData, botSaveData);
    }

    private void whenNewGame(Ship[][] enemyShips) {
        firstShipAttack = true;
        attackingShip = false;
        attackDirectionFound = false;
        currentDirection = "";
        totalShips = 5;
        currentAttack = new Point();
        firstHit = new Point();
        this.enemyShips = enemyShips;
        botAttacks = new boolean[10][10];
        nextDirection = new ArrayList<>();
    }

    private void whenLoadGame(PlayerData enemyData, BotSaveData botSaveData) {
        firstShipAttack = botSaveData.getFirstShipAttack();
        attackingShip = botSaveData.getAttackingShip();
        attackDirectionFound = botSaveData.getAttackDirectionFound();
        currentDirection = botSaveData.getCurrentDirection();
        totalShips = 5;

        currentAttack = botSaveData.getCurrentAttack();
        firstHit = botSaveData.getFirstHit();
        this.enemyShips = enemyData.getShipGrid();
        botAttacks = enemyData.getPlacesBeenAttacked();
        nextDirection = botSaveData.getNextDirection();
    }

    /**
     * 
     * This method outputs the attack of the bot as a vector.   
     * 
     */
    @Override
    public Vector findNextAttack() {
        // this checks if the bot is currently attacking a ship.
        if (attackingShip) {

            //this checks if the bot hit a ship for the first time.
            if (firstShipAttack) {
                firstShipAttack = false;
                firstShipAttack();
            
            //this checks if the direction of the ship is found.
            } else if (attackDirectionFound) {
                //if the direction is found we attack in that direction
                attackShipInDirection();
            } else {
                //if the direction is not found we attack around the ship
                attackAroundShip();
            }

        //if the bot is not attacking a ship it will try to find a new ship.
        } else {
            randomAttack();
        }

        // this returns the bot attack as a vector.
        return currentBotAttack;
    }

    // if it is the first ship attack this will check what directions of the ship is possible
    private void firstShipAttack() {
        // create a new list with directions
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

    // this will attack a square around the ship with the given directions possible
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

    // if the direction is found the bot fill attack in that same direction
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
            // if the bot can not attack in the direction it will switch direction
            } else {
                currentAttack.x = firstHit.x;
                currentAttack.y = firstHit.y;
                switchDirection();
            }
        }
        attack();
    }

    // this switches the direction to the opposite
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
            default:
                break;
        } 
    }

    // this attacks a "random" square on the board
    private void randomAttack() {
        boolean foundNewHit = false;
        while (!foundNewHit) {
            currentAttack.y = getRandomNumber(0, 9);
            currentAttack.x = getRandomNumber(0, 9);

            // this checks if the spot is not already attacked
            if (checkCollide()) {
                foundNewHit = true;
            }
        }
        attack();
    }

    // this will attack a given square in the array
    private void attack() {

        // create a new vector with the current attack to be able to return it
        currentBotAttack = new Vector(currentAttack.x, currentAttack.y);

        // the currentShip is the ship that is attacked.
        Ship currentShip = enemyShips[currentAttack.y][currentAttack.x];

        //add the attack to the attack array, so we know what is already attacked
        botAttacks[currentAttack.y][currentAttack.x] = true;
        //if the attack is a hit
        if (currentShip != null) {
            
            //hp of the ship goes down
            currentShip.hp--;

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

    // this gives a random number when provided a range.
    private int getRandomNumber(int start, int end) {
        return random.nextInt((end - start) + 1) + start;
    }

    // this checks if the attack collides
    private boolean checkCollide() {
        return !botAttacks[currentAttack.y][currentAttack.x];
    }

    // this checks if a ship is sunk.
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
    public BotSaveData toSaveData() {
        boolean firstAttack = true;

        for (int y = 0; y < botAttacks.length; y++) {
            for (int x = 0; x < botAttacks[0].length; x++) {
                firstAttack = firstAttack && !botAttacks[y][x];
            }
        }

        return new BotSaveData(firstAttack, firstShipAttack, attackingShip,
                attackDirectionFound, currentDirection, false,
                currentAttack, firstHit, nextDirection);
    }
}
