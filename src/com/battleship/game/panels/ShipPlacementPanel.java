package com.battleship.game.panels;

import com.battleship.game.Game;
import com.battleship.game.ShipData;
import com.battleship.game.enums.GameState;
import com.battleship.game.Main;
import com.battleship.game.utils.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShipPlacementPanel extends BasePanel implements ActionListener {
    int[][] ships;
    JButton finishPlacementButton;
    JButton rotateShipButton;

    boolean shipSelected;
    ShipData selectedShipData;
    ShipData newShipData;


    public ShipPlacementPanel(Main main) {
        super(main, GameState.PLACE_SHIPS);
        ships = new int[Game.SIZE_Y][Game.SIZE_X];

        shipSelected = false;
        selectedShipData = null;
        newShipData = null;

        // Places ships next to each other
        for (int y = 0; y < Game.SHIP_SIZES.length; y++) {
            for (int x = 0; x < Game.SHIP_SIZES[y]; x++) {
                ships[y][x] = Game.SHIP_SIZES[y];
            }
        }


        for (int y = 0; y < Game.SIZE_Y; y++) {
            for (int x = 0; x < Game.SIZE_X; x++) {
                JButton gridButton = new JButton();
                gridButton.setBackground(Color.BLUE);
                gridButton.setActionCommand(new Vector(x, y).toString());
            }
        }

        //TODO: Add code that draws ships and if a ship is selected,

        rotateShipButton = new JButton();
        rotateShipButton.setText("Rotate");
        rotateShipButton.addActionListener(this);

        finishPlacementButton = new JButton();
        finishPlacementButton.setText("Finish");
        finishPlacementButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();

        if (finishPlacementButton == object) {
            boolean[][] boolShips = new boolean[Game.SIZE_Y][Game.SIZE_X];
            for (int y = 0; y < Game.SIZE_Y; y++) {
                for (int x = 0; x < Game.SIZE_X; x++) {
                    boolShips[y][x] = ships[y][x] != 0;
                }
            }
            main.getCurrentGame().setNextPlayerShips(boolShips);
        } else if (rotateShipButton == object) {
            newShipData.rotateShipClockwise();
        } else {
            Vector buttonPos = new Vector(e.getActionCommand());

            // Ship currently selected
            if (shipSelected) {

            } else if (ships[buttonPos.getY()][buttonPos.getY()] != 0) {
                // No ship selected and space selected has a ship
                shipSelected = true;

                // Determine ship's direction and actual pos
                // Remove ship from array

            }
        }
    }
}
