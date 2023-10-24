package com.battleship.game.botfiles;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 
 * todo shipsizestuff and dont hit next to sunk ship.
 */

public class BotGuessingAlgorithm {

    Random random = new Random();

    private boolean firstAttack = true;
    private boolean isUnevenSquares;
    private boolean firstShipAttack = true;
    private boolean attackingShip = false;
    private boolean attackDirectionFound = false;

    private String currentDirection;

    private int totalShips = 5;

    Point currentAttack = new Point();
    Point firstHit = new Point();

    Ship[][] enemyShips = new BotPlacingRandom().botPlacingRandom();
    int[][] currentSquares;

    boolean[][] botAttacks = new boolean[10][10];
    List<String> nextDirection;

    Vector<Integer> currentBotAttack;

    /**
     * 
     * 
     * 
     */
    public Vector<Integer> botGuessingAlgorithm() {

        if (firstAttack) {
            firstAttack = false;
            firstAttack();
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

            if (checkCollide()) {
                foundNewHit = true;
            }
        }
        attack();
    }

    private void attack() {

        currentBotAttack = new Vector<Integer>(currentAttack.x, currentAttack.y);
        
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
        System.out.println("yahoo");
    }
}
