package com.battleship.game;

import com.battleship.game.enums.Direction;
import com.battleship.game.utils.ShipUtils;
import com.battleship.game.utils.Vector;

public class Test {

    private void run() {
        assert Direction.VERTICAL.getNVec().getY() == -1: "getNVec not working";

        assert Direction.HORIZONTAL.getVec().add(Direction.HORIZONTAL.getVec()).getX() ==
                Direction.HORIZONTAL.getVec().scale(2).getX();

        assert !ShipUtils.canPlaceAt(new int[][]
                {{2, 2, 0},
                 {0, 0, 0},
                 {0, 0, 0}},
                2,
                new Vector(1, 1),
                Direction.HORIZONTAL);
    }

    public static void main(String[] args) {
        new Test().run();
    }

}
