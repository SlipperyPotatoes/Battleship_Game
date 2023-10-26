package com.battleship.game.botfiles;

import com.battleship.game.logic.PlayerData;
import com.battleship.game.utils.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * todo shipsizestuff and dont hit next to sunk ship.
 */

public class BotGuessingAlgorithm extends BotGuessing{

    // things that do not need to be saved
    Random random = new Random();
    Vector currentBotAttack;

    private boolean firstAttack;
    private boolean firstShipAttack;
    private boolean attackingShip;
    private boolean attackDirectionFound;
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
    boolean[][] botAttacks;
    boolean[][] shipBorders;
    List<String> nextDirection;

    // Used for creating a bot when starting a new game
    public BotGuessingAlgorithm(Ship[][] enemyShips) {
        whenNewGame(enemyShips);
    }

    // Used for creating a bot based on saved data
    public BotGuessingAlgorithm(Ship[][] enemyShips, PlayerData botData) {
        whenLoadGame(enemyShips, botData);
    }


    //when doing a new game:
    private void whenNewGame(Ship[][] enemyShips) {
        smallestShipSize = 2;
        firstAttack = true;
        firstShipAttack = true;
        attackingShip = false;
        attackDirectionFound = false;
        totalShips = 5;

        currentAttack = new Point();
        firstHit = new Point();
        this.enemyShips = enemyShips;
        botAttacks = new boolean[10][10];
        shipBorders = new boolean[10][10];

        destroyerSunk = false;
        cruiserSunk = false;
        submarineSunk = false;
        battleshipSunk = false;
        aircraftCarrierSunk = false;

    }

    private void whenLoadGame(Ship[][] enemyShips, PlayerData botData) {
        /** 
         * LOAD THESE
         * 
        firstAttack;
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
        
        destroyerSunk;
        cruiserSunk;
        submarineSunk;
        battleshipSunk;
        aircraftCarrierSunk;
        smallestShipSize;
        */
    }
    
    
    /**
     * 
     * 
     * 
     */
    @Override
    public Vector findNextAttack() {
        if (firstAttack) {
            firstAttack();
            firstAttack = false;

        } else if (attackingShip) {
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


    private void firstAttack() {
        boolean isUnevenSquares;
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

    private void firstShipAttack() {
        // stores the first attack to a point for later use

        nextDirection = new ArrayList<String>();

        if (currentAttack.y != 0) {
            if ((!botAttacks[currentAttack.y - 1][currentAttack.x])) {
                nextDirection.add("NORTH");
            }
        }
        if (currentAttack.x != 9) {
            if (!botAttacks[currentAttack.y][currentAttack.x + 1]) {
                nextDirection.add("EAST");        
            }
        }
        if (currentAttack.y != 9) {
            if (!botAttacks[currentAttack.y + 1][currentAttack.x]) {
                nextDirection.add("SOUTH");
            }
        }
        if (currentAttack.x != 0) {
            if (!botAttacks[currentAttack.y][currentAttack.x - 1]) {
                nextDirection.add("WEST");
            }
        }
        attackAroundShip();
    }

    private void attackAroundShip() {

        boolean foundNewHit = false;

        while (!foundNewHit) {
            if (nextDirection.size() == 1) {
                currentDirection = nextDirection.get(0);
            } else {
                int listplace = getRandomNumber(0, (nextDirection.size() - 1));
                currentDirection = nextDirection.get(listplace);
                nextDirection.remove(listplace);
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
            //if it doesnt collide we can attack
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
            default:
                break;
        } 
    }

    private void randomAttack() {
        boolean foundNewHit = false;
        while (!foundNewHit) {
            currentAttack.y = getRandomNumber(0, 9);
            currentAttack.x = currentSquares[currentAttack.y][getRandomNumber(0, 4)];

            if (checkCollide() && (checkX() || checkY())) {
                foundNewHit = true;
            }
        }
        attack();
    }

    private void attack() {

        currentBotAttack = new Vector(currentAttack.x, currentAttack.y);
        
        Ship currentShip = enemyShips[currentAttack.y][currentAttack.x];

        ///add the attack to the attack array so we know what is already attacked
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
                //if we  werent already we start attacking the ship
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

    private void addBorder(Ship currentShip) {
        if (currentShip.rotation) {
            for (int i = 0; i < currentShip.length; i++) {
                if (currentShip.locationStart.x != 9) {
                    botAttacks[currentShip.locationStart.y + i][currentShip.locationStart.x + 1] = true;
                }
                if (currentShip.locationStart.x != 0) {
                    botAttacks[currentShip.locationStart.y + i][currentShip.locationStart.x - 1] = true;
                }
            }
            if (currentShip.locationStart.y != 0) {
                botAttacks[currentShip.locationStart.y - 1][currentShip.locationStart.x] = true;
            }
            if (currentShip.locationStart.y != 9) {
                botAttacks[currentShip.locationStart.y + 1][currentShip.locationStart.x] = true;
            }
        } else {
            for (int i = 0; i < currentShip.length; i++) {
                if (currentShip.locationStart.y != 9) {
                    botAttacks[currentShip.locationStart.y + 1][currentShip.locationStart.x + i] = true;
                }
                if (currentShip.locationStart.y != 0) {
                    botAttacks[currentShip.locationStart.y - 1][currentShip.locationStart.x + i] = true;
                }
            }
            if (currentShip.locationStart.x != 0) {
                botAttacks[currentShip.locationStart.y][currentShip.locationStart.x - 1] = true;
            }
            if (currentShip.locationStart.x != 9) {
                botAttacks[currentShip.locationStart.y][currentShip.locationStart.x + 1] = true;
            }
        }
    }

    private boolean checkSunk(Ship currentShip) {
        if (currentShip.hp == 0) {
            totalShips--;
            String shipName = currentShip.name;
            if (shipName.equals("Destroyer")) {
                destroyerSunk = true;
            } else if (shipName.equals("Cruiser")) {
                cruiserSunk = true;
            } else if (shipName.equals("Submarine")) {
                submarineSunk = true;
            } else if (shipName.equals("Battleship")) {
                battleshipSunk = true;
            } else if (shipName.equals("Aircraft Carrier")) {
                aircraftCarrierSunk = true;
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

    private void endGame() {
        System.out.println("yahoo");
    }

    private boolean checkX() {
        int test = 0;
        for (int p = 0; p < smallestShipSize; p++) {
            for (int i = 0; i < smallestShipSize; i++) {
                if (((currentAttack.x - smallestShipSize + 1 + i) >= 0) && (currentAttack.x + p) <= 9) {
                    if (!botAttacks[currentAttack.y][currentAttack.x - smallestShipSize + 1 + i + p]) {
                        test++;
                    }
                }
                if (test == smallestShipSize) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkY() {
        int test = 0;
        for (int p = 0; p < smallestShipSize; p++) {
            for (int i = 0; i < smallestShipSize; i++) {
                if (((currentAttack.y - smallestShipSize + 1 + i) >= 0) && (currentAttack.y + p) <= 9) {
                    if (!botAttacks[currentAttack.y - smallestShipSize + 1 + i + p][currentAttack.x]) {
                        test++;
                    }
                }
                if (test == smallestShipSize) {
                    return true;
                }
            }
        }
        return false;
    }


    
}
