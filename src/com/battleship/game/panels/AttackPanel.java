package com.battleship.game.panels;

import com.battleship.game.Main;
import com.battleship.game.enums.GameState;

import java.awt.*;

public abstract class AttackPanel extends BasePanel {


    public AttackPanel(Main main, GameState gameState) {
        super(main, gameState);
        this.setBackground(Color.RED);
        //TODO: Add stuff for drawing places attacked
    }


}
