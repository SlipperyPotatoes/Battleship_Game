package com.battleship.game.panels;

import com.battleship.game.Main;
import com.battleship.game.enums.GameState;
import com.battleship.game.logic.Game;
import com.battleship.game.logic.ShipData;
import com.battleship.game.utils.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HumanAttackPanel extends AttackPanel implements ActionListener {
    JButton[][] buttonGrid;
    JButton saveGameButton;
    JLabel shipsSunkLabel;
    JLabel numOfHitsLabel;
    JLabel numOfMissesLabel;

    public HumanAttackPanel(Main main, GameState gameState) {
        super(main, gameState);

        buttonGrid = new JButton[Game.SIZE_Y][Game.SIZE_X];

        for (int y = 0; y < Game.SIZE_Y; y++) {
            for (int x = 0; x < Game.SIZE_X; x++) {
                JButton gridButton = new JButton();
                gridButton.setBackground(Color.BLUE);
                gridButton.setBorderPainted(false);

                gridButton.setActionCommand(new Vector(x, y).toString());
                gridButton.addActionListener(this);

                this.add(gridButton);
            }
        }


    }

    @Override
    void updateGridImages() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: Figure out which button was press and check if its been pressed before and if
        Object object = e.getSource();

        if (saveGameButton == object) {
            main.getCurrentGame().saveGame();
            return;
        }

        Vector buttonPos = new Vector(e.getActionCommand());
        this.getPanelState();
    }
}
