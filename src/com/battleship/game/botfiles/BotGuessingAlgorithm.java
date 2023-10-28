package com.battleship.game.botfiles;

import com.battleship.game.logic.PlayerData;
import com.battleship.game.utils.ShipUtils;
import com.battleship.game.utils.Vector;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is the algorithm for the attacks of the bot. It has a few rules

 * 1: The first hit is one of the 4 squares in the middle.
 * 2: when looking for a hit attack in a checkerboard pattern
 * 3: you cannot hit a place where it is not possible that the smallest ship fits there
 * 4: you cannot attack a spot that is already attacked
 * 5: when a ship is sunk don't attack around the ship

 * 6: when the bot hits a ship, it will check around the ship and attack the
 *    ship until the ship is sunk
 */

public class BotGuessingAlgorithm extends BotGuessing {

    // things that do not need to be saved
    Random random = new Random();
    Vector currentBotAttack;

    //things that do need to be saved
    private boolean firstAttack;
    private boolean firstShipAttack;
    private boolean attackingShip;
    private boolean attackDirectionFound;
    private boolean isUnevenSquares;
    private String currentDirection;
    private int totalShips;
    private int smallestShipSize;

    private boolean destroyerSunk;
    private boolean cruiserSunk;
    private boolean submarineSunk;
    private boolean battleshipSunk;
    private boolean aircraftCarrierSunk;

    Point currentAttack;
    Point firstHit;
    Ship[][] enemyShips;
    int[][] currentSquares;
    boolean[][] attackMap;
    boolean[][] botAttacks;
    List<String> nextDirection;

    // Used for creating a bot when starting a new game
    public BotGuessingAlgorithm(Ship[][] enemyShips) {
        whenNewGame(enemyShips);
    }

    // Used for creating a bot based on saved data
    public BotGuessingAlgorithm(PlayerData enemyData, BotSaveData botSaveData) {
        whenLoadGame(enemyData, botSaveData);
    }


    //when doing a new game:
    private void whenNewGame(Ship[][] enemyShips) {
        firstAttack = true;
        firstShipAttack = true;
        attackingShip = false;
        attackDirectionFound = false;
        isUnevenSquares = false;
        currentDirection = "";
        totalShips = 5;
        smallestShipSize = 2;

        destroyerSunk = false;
        cruiserSunk = false;
        submarineSunk = false;
        battleshipSunk = false;
        aircraftCarrierSunk = false;


        currentAttack = new Point();
        firstHit = new Point();
        this.enemyShips = enemyShips;
        currentSquares = new int[5][10];
        attackMap = new boolean[10][10];
        botAttacks = new boolean[10][10];
        nextDirection = new ArrayList<>();
    }

    private void whenLoadGame(PlayerData enemyData, BotSaveData botSaveData) {
        firstAttack = botSaveData.getFirstAttack();
        firstShipAttack = botSaveData.getFirstShipAttack();
        attackingShip = botSaveData.getAttackingShip();

        attackDirectionFound = botSaveData.getAttackDirectionFound();
        currentDirection = botSaveData.getCurrentDirection();
        totalShips = 5;
        smallestShipSize = 2;

        destroyerSunk = ShipUtils.isShipSunk(enemyData, "Destroyer");
        cruiserSunk = ShipUtils.isShipSunk(enemyData, "Cruiser");
        submarineSunk = ShipUtils.isShipSunk(enemyData, "Submarine");
        battleshipSunk = ShipUtils.isShipSunk(enemyData, "Battleship");
        aircraftCarrierSunk = ShipUtils.isShipSunk(enemyData, "Aircraft Carrier");

        currentAttack = botSaveData.getCurrentAttack();
        firstHit = botSaveData.getFirstHit();
        this.enemyShips = enemyData.getShipGrid();
        currentSquares = botSaveData.getCurrentSquares();
        attackMap = enemyData.getPlacesBeenAttacked();
        botAttacks = attackMap.clone();
        for (Ship ship : enemyData.getShipArray()) {
            if (ship.isDead()) {
                addBorder(ship);
            }
        }
        nextDirection = botSaveData.getNextDirection();

    }
    
    
    /**
     * This method outputs the attack of the bot as a vector.
     */
    @Override
    public Vector findNextAttack() {
        // this checks if it's the first attack.
        if (firstAttack) {
            firstAttack();
            firstAttack = false;

        // this checks if the bot is currently attacking a ship.
        } else if (attackingShip) {

            //this checks if the bot hit a ship for the first time.
            if (firstShipAttack) {
                firstShipAttack = false;
                firstShipAttack();
            
            //this checks if the direction of the ship is found.
            } else if (attackDirectionFound) {

                //if the direction is found we attack in that direction.
                attackShipInDirection();

            } else {
                //if the direction is not found we attack around the ship.
                attackAroundShip();
            }
        
        //if the bot is not attacking a ship it will try to find a new ship.
        } else {
            randomAttack();
        }

        // this returns the bot attack as a vector.
        return currentBotAttack;
    }

    // this will attack 1 of the 4 squares in the middle 
    // and create an attack that belongs to the square.
    private void firstAttack() {
        // first randomly hit 1 of the 4 squares in the center and creates an attack pattern
        switch (getRandomNumber(1, 4)) {
            case 1:
                currentAttack.y = 4;
                currentAttack.x = 4;
                isUnevenSquares = false;
                break;
            case 2:
                currentAttack.y = 5;
                currentAttack.x = 4;
                isUnevenSquares = true;
                break;
            case 3:
                currentAttack.y = 4;
                currentAttack.x = 5;
                isUnevenSquares = true;
                break;
            case 4:
                currentAttack.y = 5;
                currentAttack.x = 5;
                isUnevenSquares = false;
                break;
            default:
                isUnevenSquares = true;
                break;
        }
        if (isUnevenSquares) {
            currentSquares = new int[][]{
                {1, 3, 5, 7, 9},
                {0, 2, 4, 6, 8},              
                {1, 3, 5, 7, 9},
                {0, 2, 4, 6, 8},              
                {1, 3, 5, 7, 9},
                {0, 2, 4, 6, 8},              
                {1, 3, 5, 7, 9},
                {0, 2, 4, 6, 8},              
                {1, 3, 5, 7, 9},
                {0, 2, 4, 6, 8},
            };

        } else {
            currentSquares = new int[][]{
                {0, 2, 4, 6, 8},              
                {1, 3, 5, 7, 9},
                {0, 2, 4, 6, 8},              
                {1, 3, 5, 7, 9},
                {0, 2, 4, 6, 8},              
                {1, 3, 5, 7, 9},
                {0, 2, 4, 6, 8},              
                {1, 3, 5, 7, 9},
                {0, 2, 4, 6, 8},
                {1, 3, 5, 7, 9},  
            };
        }
        attack();
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
            currentAttack.x = currentSquares[currentAttack.y][getRandomNumber(0, 4)];

            // this checks if the attack is possible 
            // and if the attack is not in a place where a ship can not fit
            if (checkCollide() && (checkX() || checkY())) {
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

        ///add the attack to the attack array, so we know what is already attacked
        botAttacks[currentAttack.y][currentAttack.x] = true;
        attackMap[currentAttack.y][currentAttack.x] = true;

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

            // check if we sunk a ship and/or all the ships
            if (checkSunk(currentShip)) {
                attackingShip = false;
                firstShipAttack = true;
                attackDirectionFound = false;
            }
        // if the bot missed and if we are attacking go back to the first hit
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

    // this adds a border around the ship when the ship is sunk
    private void addBorder(Ship currentShip) {
        int startX = currentShip.locationStart.x;
        int startY = currentShip.locationStart.y;
        if (currentShip.rotation) {
            for (int i = 0; i < currentShip.length; i++) {
                if (startX != 9) {
                    botAttacks[startY + i][startX + 1] = true;
                }
                if (startX != 0) {
                    botAttacks[startY + i][startX - 1] = true;
                }
            }
            if (startY != 0) {
                botAttacks[startY - 1][startX] = true;
            }
            if (startY != 9) {
                botAttacks[startY + 1][startX] = true;
            }
        } else {
            for (int i = 0; i < currentShip.length; i++) {
                if (startY != 9) {
                    botAttacks[startY + 1][startX + i] = true;
                }
                if (startY != 0) {
                    botAttacks[startY - 1][startX + i] = true;
                }
            }
            if (startX != 0) {
                botAttacks[startY][startX - 1] = true;
            }
            if (startX != 9) {
                botAttacks[startY][startX + 1] = true;
            }
        }
    }
    
    // this checks if a ship is sunk, and it checks the smallest ship currently
    private boolean checkSunk(Ship currentShip) {
        if (currentShip.hp == 0) {
            addBorder(currentShip);
            totalShips--;
            String shipName = currentShip.name;
            switch (shipName) {
                case "Destroyer" -> destroyerSunk = true;
                case "Cruiser" -> cruiserSunk = true;
                case "Submarine" -> submarineSunk = true;
                case "Battleship" -> battleshipSunk = true;
                case "Aircraft Carrier" -> aircraftCarrierSunk = true;
            }
            if (!aircraftCarrierSunk) {
                smallestShipSize = 5;
            }
            if (!battleshipSunk) {
                smallestShipSize = 4;
            }
            if (!cruiserSunk) {
                smallestShipSize = 3;
            }
            if (!submarineSunk) {
                smallestShipSize = 3;
            }
            if (!destroyerSunk) {
                smallestShipSize = 2;
            }
            if (totalShips == 0) {
                endGame();
            }
            return true;
        }
        return false;
    }

    // if the game has ended
    private void endGame() {
    }

    // this checks the x-axis if a ship can fit
    private boolean checkX() {
        int fit = 0;
        int currentX = currentAttack.x;
        int currentY = currentAttack.y;

        for (int p = 0; p < smallestShipSize; p++) {
            for (int i = 0; i < smallestShipSize; i++) {
                if (((currentX - smallestShipSize + 1 + i) >= 0) && (currentX + p) <= 9) {
                    if (!botAttacks[currentY][currentX - smallestShipSize + 1 + i + p]) {
                        fit++;
                    }
                }
                if (fit == smallestShipSize) {
                    return true;
                }
            }
        }
        return false;
    }

    // this checks the y-axis if a ship can fit
    private boolean checkY() {
        int fit = 0;
        int currentX = currentAttack.x;
        int currentY = currentAttack.y;

        for (int p = 0; p < smallestShipSize; p++) {
            for (int i = 0; i < smallestShipSize; i++) {
                if (((currentY - smallestShipSize + 1 + i) >= 0) && (currentY + p) <= 9) {
                    if (!botAttacks[currentY - smallestShipSize + 1 + i + p][currentX]) {
                        fit++;
                    }
                }
                if (fit == smallestShipSize) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public BotSaveData toSaveData() {
        return new BotSaveData(firstAttack, firstShipAttack, attackingShip,
                attackDirectionFound, currentDirection, isUnevenSquares,
                currentAttack, firstHit, nextDirection);
    }
}
