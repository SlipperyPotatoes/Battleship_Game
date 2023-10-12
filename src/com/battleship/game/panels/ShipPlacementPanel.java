package com.battleship.game.panels;

import com.battleship.game.Game;
import com.battleship.game.GameState;
import com.battleship.game.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShipPlacementPanel extends BasePanel implements ActionListener {
    int[][] ships;
    JButton finishPlacementButton;

    public ShipPlacementPanel(Main main) {
        super(main, GameState.PLACE_SHIPS);
        ships = new int[Game.SIZE_Y][Game.SIZE_X];

        finishPlacementButton = new JButton();
        finishPlacementButton.setText("Finish");
        finishPlacementButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();

        if (finishPlacementButton == object) {

            main.changeGameState(GameState.PLAYER_ATTACK);

        }
    }
}
