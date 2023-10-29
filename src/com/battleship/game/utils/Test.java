package com.battleship.game.utils;

import com.battleship.game.botfiles.Ship;
import com.battleship.game.enums.Direction;

/**
 * Tests for checking things are working, is run independently of rest of project.
 */
public class Test {

    public static void main(String[] args) {
        new Test().run();
    }

    private void run() {
        assert Direction.VERTICAL.getNVec().getY() == -1 : "getNVec not working";

        assert Direction.HORIZONTAL.getVec().indAdd(Direction.HORIZONTAL.getVec()).getX()
            == Direction.HORIZONTAL.getVec().indScale(2).getX();

        Ship ship = new Ship(5, "cool name", false);
        assert ship.toString().equals(new Ship(ship.toString()).toString());

    }

}
