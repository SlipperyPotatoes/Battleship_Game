package com.battleship.game.panels;

import com.battleship.game.logic.Game;
import com.battleship.game.Main;
import com.battleship.game.logic.ShipData;
import com.battleship.game.enums.Direction;
import com.battleship.game.enums.GameState;
import com.battleship.game.utils.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.battleship.game.utils.ShipUtils.*;

public class ShipPlacementPanel extends BasePanel implements ActionListener {
    int[][] intShips;
    JButton finishPlacementButton;
    JButton rotateShipButton;

    boolean shipSelected;
    ShipData selectedShip;

    public ShipPlacementPanel(Main main) {
        super(main, GameState.PLACE_SHIPS);
        intShips = new int[Game.SIZE_Y][Game.SIZE_X];

        shipSelected = false;
        selectedShip = null;

        // Places ships next to each other with gaps in between
        for (int size : Game.SHIP_SIZES) {
            for (int y = 0; y < Game.SIZE_Y; y++) {
                if (canPlaceAt(intShips, size, new Vector(0, y), Direction.HORIZONTAL)) {
                    for (int x = 0; x < size; x++) {
                        intShips[y][x] = size;
                    }
                    break;
                }
            }
        }


        GridLayout gridLayout = new GridLayout(Game.SIZE_Y, Game.SIZE_X);
        this.setLayout(gridLayout);

        for (int y = 0; y < Game.SIZE_Y; y++) {
            for (int x = 0; x < Game.SIZE_X; x++) {
                JButton gridButton = new JButton();
                gridButton.setBackground(Color.BLUE);
                String buttonStr = new Vector(x, y).toString();
                gridButton.setActionCommand(buttonStr);

                this.add(gridButton);
            }
        }


        //TODO: Add code that draws ships and if a ship is selected

        rotateShipButton = new JButton();
        rotateShipButton.setText("Rotate");
        rotateShipButton.addActionListener(this);

        finishPlacementButton = new JButton();
        finishPlacementButton.setText("Finish");
        finishPlacementButton.addActionListener(this);

        this.add(rotateShipButton);
        this.add(finishPlacementButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();

        if (finishPlacementButton == object) {
            if (shipSelected) {
                JOptionPane.showMessageDialog(main.getFrame(),
                        "Ship currently selected, please place this down first",
                        "Cannot Finish Ship Placement",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            System.out.println(convertIntArrayToString(intShips));
            System.out.println("----------");

            main.getCurrentGame().setNextPlayerShips(convertIntToBoolArray(intShips));
            main.getMainPanel().remove(this);
            main.getCurrentGame().nextPlacement();
        } else if (rotateShipButton == object) {
            if (shipSelected) {
                selectedShip.rotateShip();
            }
        } else {
            // Grid button selected
            Vector buttonPos = new Vector(e.getActionCommand());

            if (shipSelected && canPlaceAt(intShips, selectedShip)) {
                // If ship currently selected
                shipSelected = false;

                Vector placePos = new Vector(selectedShip.getPosition());

                for (int i = 0; i < selectedShip.getSize(); i++) {
                    intShips[placePos.getY()][placePos.getY()] = selectedShip.getSize();
                    placePos.add(selectedShip.getDirection().getVec());
                }

                selectedShip = null;

            } else if (!shipSelected && intShips[buttonPos.getY()][buttonPos.getY()] != 0) {
                // If no ship selected and space selected has a ship
                shipSelected = true;

                // Determine ship's direction and actual pos
                selectedShip = getShipAt(intShips, buttonPos);

                // Remove ship from array
                Vector checkPos = new Vector(selectedShip.getPosition());
                for (int i = 0; i < selectedShip.getSize(); i++) {
                    intShips[checkPos.getY()][checkPos.getY()] = 0;
                    checkPos.add(selectedShip.getDirection().getVec());
                }

            }
        }
    }
}
