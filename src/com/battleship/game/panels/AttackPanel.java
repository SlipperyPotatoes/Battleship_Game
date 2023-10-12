package com.battleship.game.panels;

import com.battleship.game.Game;
import com.battleship.game.Main;
import com.battleship.game.GameState;

import java.awt.*;

public abstract class AttackPanel extends BasePanel {
    private boolean[][] previousAttacks;

    public AttackPanel(Main main) {
        super(main, GameState.PLAYER_ATTACK);
        this.previousAttacks = new boolean[Game.SIZE_Y][Game.SIZE_X];
        this.setBackground(Color.RED);
    }

    public boolean hasAttacked(int x, int y) {
        return previousAttacks[y][x];
    }
}
