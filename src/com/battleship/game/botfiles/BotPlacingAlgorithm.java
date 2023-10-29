package com.battleship.game.botfiles;

import java.awt.*;
import java.util.*;
import java.util.List;

/** this file places the ships of the bot on a map with an algorithm
 * <p>
 * Basic Rules for ship placement for the bot.
 * <p>
 * -5 ships. 1x2 2x3 1x4 1x5
 * -NOT more than 3 the same rotation.
 * -Ships can not touch each-other
 * -1 ship on the border parallel to the border
 */

public class BotPlacingAlgorithm {

    // this creates 5 new ships to put in the map
    Ship destroyer = new Ship(2, "Destroyer", false);
    Ship cruiser = new Ship(3, "Cruiser", false);
    Ship submarine = new Ship(3, "Submarine", false);
    Ship battleship = new Ship(4, "Battleship", false);
    Ship aircraftCarrier = new Ship(5, "Aircraft Carrier", false);
    Ship currentShip;

    // this creates a list for the ships
    List<Ship> list = new ArrayList<>();
    
    boolean[][] collisionMap;
    Ship[][] botShipPlacement;
    static int horizontal = 0;
    static int vertical = 0;
    int shipPlacingTries = 0;
    Random random = new Random();

    /**
     * this outputs an array with the correct ships at some positions.
     */
    public Ship[][] botPlacingAlgorithm() {

        boolean foundSolution = false;
        
        while (!foundSolution) {
            // this creates new maps
            collisionMap = new boolean[10][10];
            botShipPlacement = new Ship[10][10];
            list = new ArrayList<>();

            // this adds the ships to a list
            list.add(destroyer);
            list.add(cruiser);
            list.add(submarine);
            list.add(battleship);
            list.add(aircraftCarrier);

            shipPlacingTries = 0;

            // this places the first ship
            placeFirstShip();

            int randomAttacks;

            // this tries to place 4 ships
            for (randomAttacks = 0; randomAttacks < 4; randomAttacks++) {
                // if it cant find a place for ship break the loop
                if (!getRandomPosition()) {
                    break;
                }
            }

            // if it was successful in placing 4 ships, break the loop
            if (randomAttacks == 4) {
                foundSolution = true;
            }
        }
        return botShipPlacement;
    }


    // this takes a random ship from the list and removes it from the list.
    private Ship getRandomShip() {
        int listPlace = getRandomNumber(0, (list.size() - 1));
        Ship currentShip = list.get(listPlace);
        list.remove(listPlace);
        return currentShip;
    }

    // this gives a random number from a provided range
    private int getRandomNumber(int start, int end) {
        return random.nextInt((end - start) + 1) + start; // see explanation below
    }

    // this tries to find a new location for a ship
    private boolean getRandomPosition() {
        boolean foundLocation = false;
        currentShip = getRandomShip();

        while (!foundLocation) {
            if (currentShip.rotation) {
                currentShip.locationStart.y = getRandomNumber(1, 9 - currentShip.length);
                currentShip.locationStart.x = getRandomNumber(1, 8);
            } else {
                currentShip.locationStart.x = getRandomNumber(1, 9 - currentShip.length);
                currentShip.locationStart.y = getRandomNumber(1, 8);
            }

            // if the ships do not collide with each-other
            if (checkCollide(currentShip)) {
                foundLocation = true;
            }

            shipPlacingTries++;
            // if it takes too long to find a new ship try again with a new map
            if (shipPlacingTries >= 25) {
                return false;
            }
        } 
        placeShip(currentShip);
        return true;
    }

    // this checks if a ship collides with another ship
    private boolean checkCollide(Ship currentShip) {
        int startY = currentShip.locationStart.y;
        int startX = currentShip.locationStart.x;
        if (currentShip.rotation) {
            for (int i = 0; i < currentShip.length; i++) {
                if (collisionMap[startY + i][startX]) {
                    return false;
                }
                if ((collisionMap[startY + i][startX - 1]) 
                    || (collisionMap[startY + i][startX + 1])) {
                    return false;
                }
                if ((collisionMap[startY + currentShip.length][startX]) 
                    || (collisionMap[startY - 1][startX])) {
                    return false;
                }
            }
                
        } else {
            for (int i = 0; i < currentShip.length; i++) {
                if (collisionMap[startY][startX + i]) {
                    return false;
                }
                if (collisionMap[startY + 1][startX + i]) {
                    return false;
                }
                if (collisionMap[startY - 1][startX + i]) {
                    return false;
                }
                if (collisionMap[startY][startX - 1]) {
                    return false;
                }
                if (collisionMap[startY][startX + currentShip.length]) {
                    return false;
                }
            }
        }
        return true;
    }

    //this places the first ship in the map parallel to the border
    private void placeFirstShip() {

        currentShip = getRandomShip();
        boolean randomMap = random.nextBoolean();
        int mapLocation;

        if (randomMap) {
            mapLocation = 9;
        } else {
            mapLocation = 0;
        }

        if (currentShip.rotation) {
            currentShip.locationStart.y = getRandomNumber(0, 10 - currentShip.length);
            currentShip.locationStart.x = mapLocation;
        } else {
            currentShip.locationStart.x = getRandomNumber(0, 10 - currentShip.length);
            currentShip.locationStart.y = mapLocation;
        }
        placeShip(currentShip); 
    }

    // this places a ship on the map with a given location
    private void placeShip(Ship currentShip) {

        Point startPoint = currentShip.locationStart;
        Point currentPoint = new Point();

        for (int i = 0; i < currentShip.length; i++) {
            
            if (currentShip.rotation) {
                currentPoint.y = startPoint.y + i;
                currentPoint.x = startPoint.x;
            } else {
                currentPoint.x = startPoint.x + i;
                currentPoint.y = startPoint.y;
            }
            collisionMap[currentPoint.y][currentPoint.x] = true;
            botShipPlacement[currentPoint.y][currentPoint.x] = currentShip;
        }
    }
}




