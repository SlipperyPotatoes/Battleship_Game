package com.battleship.game.botfiles;

import java.awt.*;
import java.util.*;
import java.util.List;


/** this file places ships randomly on a map.
 */
public class BotPlacingRandom {

    // this creates 5 new ships to put in the map
    Ship destroyer = new Ship(2, "Destroyer", true);
    Ship cruiser = new Ship(3, "Cruiser", true);
    Ship submarine = new Ship(3, "Submarine", true);
    Ship battleship = new Ship(4, "Battleship", true);
    Ship aircraftCarrier = new Ship(5, "Aircraft Carrier", true);
    Ship currentShip;

    // this creates a list for the ships
    List<Ship> list = new ArrayList<>();
    
    boolean[][] collisionMap;
    Ship[][] botShipPlacement;
    private int shipPlacingTries;


    Random random = new Random();

    /**
     * this outputs an array with the correct ships at some positions.
     */
    public Ship[][] botPlacingRandom() {

        boolean foundSolution = false;
        
        while (!foundSolution) {
            // this creates new maps
            collisionMap = new boolean[10][10];
            botShipPlacement = new Ship[10][10];
            list = new ArrayList<>();

            // add the ships to a list
            list.add(destroyer);
            list.add(cruiser);
            list.add(submarine);
            list.add(battleship);
            list.add(aircraftCarrier);

            shipPlacingTries = 0;
            int randomAttacks;

            // this tries to place 5 ships
            for (randomAttacks = 0; randomAttacks < 5; randomAttacks++) {
                // if it cant find a place for ship break the loop
                if (!getRandomPosition()) {
                    break;
                }
            }
            // if it was successfully in placing 5 ships, break the loop
            if (randomAttacks == 5) {
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
        return random.nextInt((end - start) + 1) + start;
    }

    // this tries to find a new location for a ship
    private boolean getRandomPosition() {
        boolean foundLocation = false;
        currentShip = getRandomShip();

        while (!foundLocation) {
            if (currentShip.rotation) {
                currentShip.locationStart.y = getRandomNumber(0, 10 - currentShip.length);
                currentShip.locationStart.x = getRandomNumber(0, 9);
            } else {
                currentShip.locationStart.x = getRandomNumber(0, 10 - currentShip.length);
                currentShip.locationStart.y = getRandomNumber(0, 9);
            }
            // if the ships do not collide with each-other
            if (checkCollide(currentShip)) {
                foundLocation = true;
            }

            // if it takes too long to find a new ship try again with a new map
            shipPlacingTries++;
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
        int shipLength = currentShip.length;

        if (currentShip.rotation) {
            for (int i = 0; i < shipLength; i++) {
                if (collisionMap[startY + i][startX]) {
                    return false;
                }
                if (currentShip.locationStart.x != 0) {
                    if (collisionMap[startY + i][startX - 1]) {
                        return false;
                    }
                }
                if (currentShip.locationStart.x != 9) {
                    if (collisionMap[startY + i][startX + 1]) {
                        return false;
                    }
                }
                if (startY != 0) {
                    if (collisionMap[startY - 1][startX]) {
                        return false;
                    }
                }
                if ((startY + shipLength) != 10) {
                    if (collisionMap[startY + currentShip.length][startX]) {
                        return false;
                    }
                }               
            }     
        } else {
            for (int i = 0; i < shipLength; i++) {
                if (collisionMap[startY][startX + i]) {
                    return false;
                }
                if (startY != 9) {
                    if (collisionMap[startY + 1][startX + i]) {
                        return false;
                    }
                }
                if (startY != 0) {
                    if (collisionMap[startY - 1][startX + i]) {
                        return false;
                    }
                }
                if (startX != 0) {
                    if (collisionMap[startY][startX - 1]) {
                        return false;
                    }
                }
                if (startX + currentShip.length != 10) {
                    if (collisionMap[startY][startX + currentShip.length]) {
                        return false;
                    }
                }
            }
        }
        return true;
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




