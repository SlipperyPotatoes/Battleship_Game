package com.battleship.game.panels;

import com.battleship.game.Main;
import com.battleship.game.botfiles.Ship;
import com.battleship.game.enums.GameState;
import com.battleship.game.logic.Game;
import com.battleship.game.logic.PlayerData;
import com.battleship.game.logic.ShipData;
import com.battleship.game.utils.ShipUtils;
import com.battleship.game.utils.Vector;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

import static com.battleship.game.utils.ShipUtils.convertShipGridToShipArray;
import static com.battleship.game.utils.assetsUtils.loadIcon;
import static com.battleship.game.utils.assetsUtils.scaleImage;

public class BotAttackPanel extends AttackPanel {
    private final ImageIcon boatMiddleHorizontal;
    private final ImageIcon boatMiddleVertical;
    private final ImageIcon boatEndLeft;
    private final ImageIcon boatEndRight;
    private final ImageIcon boatEndDown;
    private final ImageIcon boatEndUp;

    private final ImageIcon boatMiddleHorizontalX;
    private final ImageIcon boatMiddleVerticalX;
    private final ImageIcon boatEndLeftX;
    private final ImageIcon boatEndRightX;
    private final ImageIcon boatEndDownX;
    private final ImageIcon boatEndUpX;

    private ImageIcon boatMiddleHorizontalScaled;
    private ImageIcon boatMiddleVerticalScaled;
    private ImageIcon boatEndLeftScaled;
    private ImageIcon boatEndRightScaled;
    private ImageIcon boatEndDownScaled;
    private ImageIcon boatEndUpScaled;

    private ImageIcon boatMiddleHorizontalXScaled;
    private ImageIcon boatMiddleVerticalXScaled;
    private ImageIcon boatEndLeftXScaled;
    private ImageIcon boatEndRightXScaled;
    private ImageIcon boatEndDownXScaled;
    private ImageIcon boatEndUpXScaled;

    JLabel[][] gridLabels;

    public BotAttackPanel(Main main, PlayerData enemyPlayerData) {
        super(main, GameState.PLAYER_2_ATTACK, enemyPlayerData);
        // Loads the images for the different parts of the boat
        boatMiddleHorizontal = loadIcon("boat_middle_horizontal.png");
        boatMiddleVertical = loadIcon("boat_middle_vertical.png");
        boatEndLeft = loadIcon("boat_end_left.png");
        boatEndRight = loadIcon("boat_end_right.png");
        boatEndDown = loadIcon("boat_end_down.png");
        boatEndUp = loadIcon("boat_end_up.png");

        boatMiddleHorizontalX = loadIcon("boat_middle_horizontal_x.png");
        boatMiddleVerticalX = loadIcon("boat_middle_vertical_x.png");
        boatEndLeftX = loadIcon("boat_end_left_x.png");
        boatEndRightX = loadIcon("boat_end_right_x.png");
        boatEndDownX = loadIcon("boat_end_down_x.png");
        boatEndUpX = loadIcon("boat_end_up_x.png");

        gridLabels = new JLabel[Game.SIZE_Y][Game.SIZE_X];

        for (int y = 0; y < Game.SIZE_Y; y++) {
            for (int x = 0; x < Game.SIZE_X; x++) {
                JLabel gridLabel = new JLabel();
                gridLabel.setBackground(Color.BLUE);

                gridLabels[y][x] = gridLabel;
                this.add(gridLabel);
            }
        }


    }

    @Override
    public void updateGridImages() {
        boolean[][] placesAttacked = enemyPlayerData.getPlacesBeenAttacked();
        ArrayList<Ship> shipArray = convertShipGridToShipArray(enemyPlayerData.getShipGrid());

        for (Ship ship : shipArray) {
            Vector checkPos = new Vector(ship.getPosition());
            for (int i = 0; i < ship.getLength(); i++) {
                boolean hit = placesAttacked[checkPos.getY()][checkPos.getX()];
                switch (ship.getDirection()) {
                    case VERTICAL:
                        if (ship.getPosition().equals(checkPos)) {
                            gridLabels[checkPos.getY()][checkPos.getX()]
                                    .setIcon(hit ? boatEndUpXScaled : boatEndUpScaled);
                        }  else if (ship.getAltPosition().equals(checkPos)) {
                            gridLabels[checkPos.getY()][checkPos.getX()]
                                    .setIcon(hit ? boatEndDownXScaled : boatEndDownScaled);
                        } else {
                            gridLabels[checkPos.getY()][checkPos.getX()]
                                    .setIcon(hit ? boatMiddleVerticalXScaled : boatMiddleVerticalScaled);
                        }
                        break;
                    case HORIZONTAL:
                        if (ship.getPosition().equals(checkPos)) {
                            gridLabels[checkPos.getY()][checkPos.getX()]
                                    .setIcon(hit ? boatEndLeftXScaled : boatEndLeftScaled);
                        }  else if (ship.getAltPosition().equals(checkPos)) {
                            gridLabels[checkPos.getY()][checkPos.getX()]
                                    .setIcon(hit ? boatEndRightXScaled : boatEndRightScaled);
                        } else {
                            gridLabels[checkPos.getY()][checkPos.getX()]
                                    .setIcon(hit ? boatMiddleHorizontalXScaled : boatMiddleHorizontalScaled);
                        }
                        break;
                }
                checkPos.add(ship.getDirection().getVec());
            }
        }

    }


    void scaleImages() {
        Dimension dimensions = gridLabels[0][0].getSize();
        int w = dimensions.width;
        int h = dimensions.height;

        attackHitScaled = scaleImage(attackHit, w, h);
        attackMissScaled = scaleImage(attackMiss, w, h);

        w = (int) (w * 1.2f);
        h = (int) (h * 1.2f);

        boatMiddleHorizontalScaled = scaleImage(boatMiddleHorizontal, w, h);
        boatMiddleVerticalScaled = scaleImage(boatMiddleVertical, w, h);
        boatEndLeftScaled = scaleImage(boatEndLeft, w, h);
        boatEndRightScaled = scaleImage(boatEndRight, w, h);
        boatEndDownScaled = scaleImage(boatEndDown, w, h);
        boatEndUpScaled = scaleImage(boatEndUp, w, h);

        boatMiddleHorizontalXScaled = scaleImage(boatMiddleHorizontalX, w, h);
        boatMiddleVerticalXScaled = scaleImage(boatMiddleVerticalX, w, h);
        boatEndLeftXScaled = scaleImage(boatEndLeftX, w, h);
        boatEndRightXScaled = scaleImage(boatEndRightX, w, h);
        boatEndDownXScaled = scaleImage(boatEndDownX, w, h);
        boatEndUpXScaled = scaleImage(boatEndUpX, w, h);

        updateGridImages();
    }
}
