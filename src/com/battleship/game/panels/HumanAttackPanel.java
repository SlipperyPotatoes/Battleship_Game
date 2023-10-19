package com.battleship.game.panels;

import com.battleship.game.enums.GameState;
import com.battleship.game.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HumanAttackPanel extends AttackPanel implements ActionListener {

    public HumanAttackPanel(Main main) {
        super(main, GameState.PLAYER_ATTACK);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: Figure out which button was press and check if its been pressed before and if
    }
}
