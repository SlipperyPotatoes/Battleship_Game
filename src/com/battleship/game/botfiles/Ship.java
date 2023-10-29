package com.battleship.game.botfiles;

import com.battleship.game.enums.Direction;
import com.battleship.game.logic.ShipData;
import com.battleship.game.utils.Vector;
import java.awt.*;
import java.util.Random;


/**
 * This is for creating ships as objects.
 */
public class Ship {
    private final Vector position;
    int length;
    boolean rotation; // true value means vertical
    Point locationStart = new Point();
    int hp;
    String name;
    boolean randomRotation;

    /**
     * Loads the data for this ship object from a formatted string.
     */
    public Ship(String shipString) {
        int[] dataStartIndexes = new int[5];
        int[] dataEndIndexes = new int[4];
        for (int c = 0; c < shipString.length(); c++) {
            if (shipString.charAt(c) == ':') {
                for (int i = 0; i < 5; i++) {
                    if (dataStartIndexes[i] != 0) {
                        continue;
                    }
                    dataStartIndexes[i] = c + 2;
                    break;
                }
            }
            if (shipString.charAt(c) == ',') {
                for (int i = 0; i < 4; i++) {
                    if (dataEndIndexes[i] != 0) {
                        continue;
                    }
                    dataEndIndexes[i] = c;
                    break;
                }
            }

        }

        this.length = Integer.parseInt(
                shipString.substring(dataStartIndexes[0], dataEndIndexes[0]));
        this.hp = Integer.parseInt(
                shipString.substring(dataStartIndexes[1], dataEndIndexes[1]));
        this.name = shipString.substring(dataStartIndexes[2], dataEndIndexes[2]);
        this.rotation = Boolean.parseBoolean(
                shipString.substring(dataStartIndexes[3], dataEndIndexes[3]));
        this.position = new Vector(shipString.substring(dataStartIndexes[4]));
        this.locationStart.setLocation(position.getX(), position.getY());
    }

    /**
     *  Converts a ShipData object into this Ship object.
     */
    public Ship(ShipData shipData) {
        this.length = shipData.getSize();
        this.hp = length;
        this.name = shipData.getName();
        this.rotation = Direction.VERTICAL == shipData.getDirection();
        this.position = shipData.getPosition();
    }

    /**
     * Creates a Ship object using the passed parameters.
     */
    public Ship(int length, String name, boolean randomRotation) {
        this.length = length;
        this.hp = length;
        this.name = name;
        this.randomRotation = randomRotation;
        this.rotation = rotateShips();
        this.position = Vector.ZERO;
    }

    /** 
     * This method will give rotation to a ship for the algorithm so that there cannot be,
     * more than 3 ships rotated the same way.
     * or the random algorithm it just generates a random rotation
     */
    private boolean rotateShips() {
        Random random = new Random();
        boolean rotate = random.nextBoolean();

        if (!this.randomRotation) {
            if (rotate) {
                if (BotPlacingAlgorithm.vertical == 3) {
                    return false;
                }
                BotPlacingAlgorithm.vertical++;
                return true;

            } else {
                if (BotPlacingAlgorithm.horizontal == 3) {
                    return true;
                }
                BotPlacingAlgorithm.horizontal++;
                return false;

            }
        } else {
            return (random.nextBoolean());
        }
    }

    public void hit() {
        hp--;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public boolean isVertical() {
        return rotation;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getAltPosition() {
        return position.indAdd(getDirection().getVec().scale(length - 1));
    }

    public int getLength() {
        return length;
    }

    public Direction getDirection() {
        return rotation ? Direction.VERTICAL : Direction.HORIZONTAL;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Length: " + length + ", HP: " + hp + ", Name: " + name 
            + ", Vertical: " + rotation + ", Position: " + position;
    }
}
