package com.battleship.game.botfiles;

import java.util.*;
import java.awt.*;
import java.util.List;

/** Basic Rules for ship placement for the bot.
 * 
 * -5 ships. 1x2 2x3 1x4 1x5
 * -NOT more then 3 the same rotation.
 * -Ships can not touch eachother
 * -1 ship on the border perpendicular to the border
 */

public class BotPlacingAlgorithm {

    Ship destroyer = new Ship(2, "Destroyer", false);
    Ship cruiser = new Ship(3, "Cruiser",false);
    Ship submarine = new Ship(3, "Submarine",false);
    Ship battleship = new Ship(4, "Battleship",false);
    Ship aircraftcarrier = new Ship(5, "Aircraft Carrier",false);
    Ship currentShip;

    List<Ship> list = new ArrayList<>();
    
    boolean[][] collisionMap;
    Ship[][] botShipPlacement;
    String[][] mapString = new String[10][10];

    static int horizontal = 0;
    static int vertical = 0;
    int shipPlacingTries = 0;
    Random random = new Random();

    Ship[][] botPlacingAlgorithm() {

        boolean foundsolution = false;
        
        while (!foundsolution) {
            collisionMap = new boolean[10][10];
            botShipPlacement = new Ship[10][10];
            list = new ArrayList<>();

            list.add(destroyer);
            list.add(cruiser);
            list.add(submarine);
            list.add(battleship);
            list.add(aircraftcarrier);

            shipPlacingTries = 0;

            placeFirstShip();

            int randomAttacks = 0;

            for (randomAttacks = 0; randomAttacks < 4; randomAttacks++) {
                if (!getRandomPosition()) {
                    break;
                }
            }

            if (randomAttacks == 4){
                foundsolution = true;
            }
        }
        return botShipPlacement;
    }


    private Ship getRandomShip() {
        int listplace = getRandomNumber(0, (list.size() - 1));
        Ship currentShip = list.get(listplace);
        list.remove(listplace);
        return currentShip;
    }

    private int getRandomNumber(int start, int end) {
        int number = random.nextInt((end - start) + 1) + start; // see explanation below
        return number;
    }

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

            if (checkCollide(currentShip)) {
                foundLocation = true;
            }
            shipPlacingTries++;
            if (shipPlacingTries >= 25) {
                return false;
            }
        } 
        placeShip(currentShip);
        return true;
    }

    private boolean checkCollide(Ship currentShip) {
        int startY = currentShip.locationStart.y;
        int startX = currentShip.locationStart.x;
        if (currentShip.rotation) {
            for (int i = 0; i < currentShip.length; i++) {
                if (collisionMap[startY + i][startX]) {
                    return false;
                }
                if ((collisionMap[startY + i][startX - 1]) || (collisionMap[startY + i][startX + 1])) {
                    return false;
                }
                if ((collisionMap[startY + currentShip.length][startX]) || (collisionMap[startY - 1][startX])) {
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

    private void placeFirstShip() {

        currentShip = getRandomShip();
        boolean randommap = random.nextBoolean();
        int maplocation;

        if (randommap) {
            maplocation = 9;
        } else {
            maplocation = 0;
        }

        if (currentShip.rotation) {
            currentShip.locationStart.y = getRandomNumber(0, 10 - currentShip.length);
            currentShip.locationStart.x = maplocation;
        } else {
            currentShip.locationStart.x = getRandomNumber(0, 10 - currentShip.length);
            currentShip.locationStart.y = maplocation;
        }
        placeShip(currentShip); 
    }

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
    public static void main(String[] args) {
        new BotPlacingAlgorithm().botPlacingAlgorithm();
    }
}




