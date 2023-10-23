package com.battleship.game;

import com.battleship.game.enums.Direction;
import com.battleship.game.utils.Vector;

public class Test {

    private void run() {
        assert Direction.VERTICAL.getNVec().getY() == -1: "getNVec not working";

        assert Direction.HORIZONTAL.getVec().add(Direction.HORIZONTAL.getVec()).getX() ==
                Direction.HORIZONTAL.getVec().scale(2).getX();

    }

    public static void main(String[] args) {
        new Test().run();
    }

}
