package com.battleship.game.botfiles;

import com.battleship.game.enums.Direction;
import com.battleship.game.logic.ShipData;

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

    public Ship(ShipData shipData) {
        this.length = shipData.getSize();
        this.hp = length;
        this.name = shipData.getName();
        this.rotation = Direction.VERTICAL == shipData.getDirection();
    }

    Ship(int length, String name, boolean randomRotation) {
        this.length = length;
        this.hp = length;
        this.name = name;
        this.randomRotation = randomRotation;
        this.rotation = rotateShips();
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
}
