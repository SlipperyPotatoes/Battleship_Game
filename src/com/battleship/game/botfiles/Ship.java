package com.battleship.game.botfiles;

import com.battleship.game.enums.Direction;
import com.battleship.game.logic.ShipData;
import com.battleship.game.utils.Vector;

import java.awt.*;
import java.util.Random;
//true value means vertical


public class Ship {
    int length;
    boolean rotation;
    Point locationStart = new Point();
    int hp;
    String name;
    boolean randomRotation;

    private final Vector position;

    public Ship(ShipData shipData) {
        this.length = shipData.getSize();
        this.hp = length;
        this.name = shipData.getName();
        this.rotation = Direction.VERTICAL == shipData.getDirection();
        this.position = shipData.getPosition();
    }

    Ship(int length, String name, boolean randomRotation) {
        this.length = length;
        this.hp = length;
        this.name = name;
        this.randomRotation = randomRotation;
        this.rotation = rotateShips();
        this.position = Vector.ZERO;
    }

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
                return rotate;
                
            } 
        }  else {
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
        return position.iAdd(getDirection().getVec().scale(length - 1));
    }

    public int getLength() {
        return length;
    }

    public Direction getDirection() {
        return rotation ? Direction.VERTICAL : Direction.HORIZONTAL;
    }
}
