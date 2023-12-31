package com.battleship.game.panels;

import static com.battleship.game.utils.AssetUtils.loadIcon;
import static com.battleship.game.utils.AssetUtils.scaleImage;

import com.battleship.game.Main;
import com.battleship.game.botfiles.Ship;
import com.battleship.game.enums.GameState;
import com.battleship.game.logic.Game;
import com.battleship.game.logic.PlayerData;
import com.battleship.game.utils.Vector;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;



/**
 * Class for showing the attackPanel when the bot is attacking the user.
 * <p></p>
 * Does not allow for any user interaction. Only uses timers which exist
 * in the Game object and call methods in this object to show the bots actions.
 */
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

    /**
     * Creates an attack panel with labels to show the bots actions.
     * 
     * @param main Reference to main
     * @param enemyPlayerData Reference to the enemy's playerData for graphics
     */
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

        JLabel turnLabel = new JLabel("<html>" + "TURN: BOT" + "</html>");
        this.add(turnLabel);

        addStatLabels();

        updateGridImages();
        updateLabels();
    }

    @Override
    public void updateGridImages() {
        boolean[][] placesAttacked = enemyPlayerData.getPlacesBeenAttacked();
        ArrayList<Ship> shipArray = enemyPlayerData.getShipArray();

        for (int y = 0; y < Game.SIZE_Y; y++) {
            for (int x = 0; x < Game.SIZE_X; x++) {
                gridLabels[y][x].setIcon(placesAttacked[y][x] ? attackMissScaled : null);
            }
        }

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
                                    .setIcon(hit ? boatMiddleVerticalXScaled
                                        : boatMiddleVerticalScaled);
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
                                    .setIcon(hit ? boatMiddleHorizontalXScaled
                                        : boatMiddleHorizontalScaled);
                        }
                        break;
                    default:
                        break;
                }
                checkPos.add(ship.getDirection().getVec());
            }
        }

    }


    /**
     * When the window is resized this is called to scale the images
     * so that they fit evenly within the labels in the labelGrid.
     */
    void scaleImages() {
        Dimension dimensions = gridLabels[0][0].getSize();
        int w = dimensions.width;
        int h = dimensions.height;

        attackHitScaled = scaleImage(attackHit, w, h);
        attackMissScaled = scaleImage(attackMiss, w, h);

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
