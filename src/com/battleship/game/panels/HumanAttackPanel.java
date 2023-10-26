package com.battleship.game.panels;

import com.battleship.game.Main;
import com.battleship.game.botfiles.Ship;
import com.battleship.game.enums.GameState;
import com.battleship.game.logic.Game;
import com.battleship.game.logic.PlayerData;
import com.battleship.game.utils.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import static com.battleship.game.utils.assetsUtils.loadIcon;
import static com.battleship.game.utils.assetsUtils.scaleImage;

public class HumanAttackPanel extends AttackPanel implements ActionListener {
    private final ImageIcon attackLineHorizontal;
    private final ImageIcon attackLineVertical;
    private ImageIcon attackLineHorizontalScaled;
    private ImageIcon attackLineVerticalScaled;





    private JButton[][] buttonGrid;
    private JButton saveGameButton;


    public HumanAttackPanel(Main main, GameState gameState, PlayerData enemyPlayerData) {
        super(main, gameState, enemyPlayerData);

        attackLineHorizontal = loadIcon("attack_line_horizontal.png");
        attackLineVertical = loadIcon("attack_line_vertical.png");

        buttonGrid = new JButton[Game.SIZE_Y][Game.SIZE_X];

        for (int y = 0; y < Game.SIZE_Y; y++) {
            for (int x = 0; x < Game.SIZE_X; x++) {
                JButton gridButton = new JButton();
                gridButton.setBackground(Color.BLUE);
                gridButton.setBorderPainted(false);

                gridButton.setActionCommand(new Vector(x, y).toString());
                gridButton.addActionListener(this);

                buttonGrid[y][x] = gridButton;
                this.add(gridButton);
            }
        }

        updateGridImages();
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
        if (enemyPlayerData.hasBeenAttackedAt(buttonPos.getX(), buttonPos.getY())) {
            return;
        }

        enemyPlayerData.attackAt(buttonPos);

        updateGridImages();

        main.getCurrentGame().nextAttack();
    }

    @Override
    void updateGridImages() {
        boolean[][] placesAttacked = enemyPlayerData.getPlacesBeenAttacked();
        Ship[][] enemyShips = enemyPlayerData.getShipGrid();
        for (int y = 0; y < placesAttacked.length; y++) {
            for (int x = 0; x < placesAttacked[0].length; x++) {
                if (placesAttacked[y][x]) {
                    Ship ship = enemyShips[y][x];
                    ImageIcon attackImage;
                    if (ship == null) {
                        attackImage = attackMissScaled;
                    } else if (ship.isDead()) {
                        attackImage = ship.isVertical() ? attackLineVerticalScaled : attackLineHorizontalScaled;
                    } else {
                        attackImage = attackHitScaled;
                    }

                    buttonGrid[y][x].setIcon(attackImage);
                } else {
                    buttonGrid[y][x].setIcon(null);
                }
            }
        }
    }

    @Override
    void scaleImages() {
        Dimension dimensions = buttonGrid[0][0].getSize();
        int w = (int) (dimensions.width * 1f);
        int h = (int) (dimensions.height * 1f);

        attackHitScaled = scaleImage(attackHit, w, h);
        attackMissScaled = scaleImage(attackMiss, w, h);

        attackLineHorizontalScaled = scaleImage(attackLineHorizontal, w, h);
        attackLineVerticalScaled = scaleImage(attackLineVertical, w, h);

        updateGridImages();
    }
}
