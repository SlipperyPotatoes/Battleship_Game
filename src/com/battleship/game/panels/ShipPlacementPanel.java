package com.battleship.game.panels;

import static com.battleship.game.utils.AssetUtils.loadIcon;
import static com.battleship.game.utils.AssetUtils.scaleImage;
import static com.battleship.game.utils.ShipUtils.canPlaceAt;
import static com.battleship.game.utils.ShipUtils.convertShipDataGridToShipGrid;
import static com.battleship.game.utils.ShipUtils.convertShipGridToShipArray;

import com.battleship.game.Main;
import com.battleship.game.enums.Direction;
import com.battleship.game.enums.GameState;
import com.battleship.game.logic.Game;
import com.battleship.game.logic.ShipData;
import com.battleship.game.utils.Vector;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Class for a panel that the user(s) interact with in order to manipulate the positions and 
 * orientations of their ships.
 */
public class ShipPlacementPanel extends BasePanel implements ActionListener {
    private ShipData[][] shipGrid;
    private boolean shipSelected;
    private ShipData selectedShip;
    private JButton[][] gridButtons;
    private JButton finishPlacementButton;
    private JButton rotateShipButton;
    private JLabel selectedShipNameLabel;
    private JLabel selectedShipDirectionLabel;
    private JLabel selectedShipSizeLabel;

    private final ImageIcon boatMiddleHorizontal;
    private final ImageIcon boatMiddleVertical;
    private final ImageIcon boatEndLeft;
    private final ImageIcon boatEndRight;
    private final ImageIcon boatEndDown;
    private final ImageIcon boatEndUp;

    private ImageIcon boatMiddleHorizontalScaled;
    private ImageIcon boatMiddleVerticalScaled;
    private ImageIcon boatEndLeftScaled;
    private ImageIcon boatEndRightScaled;
    private ImageIcon boatEndDownScaled;
    private ImageIcon boatEndUpScaled;

    /**
     * Creates a new panel with the ships initially in the grid in the horizontal orientation
     * placed next to each-other with gaps in between.
     * 
     * @param main Reference to main
     */
    public ShipPlacementPanel(Main main) {
        super(main, GameState.PLACE_SHIPS);

        // Loads the images for the different parts of the boat
        boatMiddleHorizontal = loadIcon("boat_middle_horizontal.png");
        boatMiddleVertical = loadIcon("boat_middle_vertical.png");
        boatEndLeft = loadIcon("boat_end_left.png");
        boatEndRight = loadIcon("boat_end_right.png");
        boatEndDown = loadIcon("boat_end_down.png");
        boatEndUp = loadIcon("boat_end_up.png");



        shipGrid = new ShipData[Game.SIZE_Y][Game.SIZE_X];

        shipSelected = false;
        selectedShip = null;

        // Places ships next to each-other with gaps in between
        for (int i = 0; i < Game.SHIP_SIZES.length; i++) {
            int shipSize = Game.SHIP_SIZES[i];
            for (int y = 0; y < Game.SIZE_Y; y++) {
                if (canPlaceAt(shipGrid, shipSize, new Vector(0, y), Direction.HORIZONTAL)) {
                    ShipData shipData = new ShipData(new Vector(0, y), Direction.HORIZONTAL,
                            shipSize, Game.SHIP_NAMES[i]);
                    for (int x = 0; x < shipSize; x++) {
                        shipGrid[y][x] = shipData;
                    }
                    break;
                }
            }
        }
        
        gridButtons = new JButton[Game.SIZE_Y][Game.SIZE_X];
        for (int y = 0; y < Game.SIZE_Y; y++) {
            for (int x = 0; x < Game.SIZE_X; x++) {
                JButton gridButton = new JButton();
                gridButton.setBackground(Color.BLUE);
                gridButton.setBorderPainted(false);

                gridButton.setActionCommand(new Vector(x, y).toString());
                gridButton.addActionListener(this);
                gridButton.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        gridButton.setBackground(Color.BLUE.darker());
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        gridButton.setBackground(Color.BLUE);
                    }
                });

                gridButtons[y][x] = gridButton;

                this.add(gridButton);
            }
        }

        updateGridImages();

        rotateShipButton = new JButton();
        rotateShipButton.setText("Rotate");
        rotateShipButton.addActionListener(this);

        finishPlacementButton = new JButton();
        finishPlacementButton.setText("Finish");
        finishPlacementButton.addActionListener(this);

        selectedShipNameLabel = new JLabel();
        selectedShipDirectionLabel = new JLabel();
        selectedShipSizeLabel = new JLabel();
        updateSelectedShipLabels();


        GridLayout gridLayout = new GridLayout(0, Game.SIZE_X);
        this.setLayout(gridLayout);

        this.setBackground(Color.GRAY);
        this.add(rotateShipButton);
        this.add(finishPlacementButton);
        this.add(selectedShipNameLabel);
        this.add(selectedShipDirectionLabel);
        this.add(selectedShipSizeLabel);

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleBoatImageIcons();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
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

            main.getCurrentGame().setNextPlayerShips(convertShipDataGridToShipGrid(shipGrid));
            main.getMainPanel().remove(this);
            main.getCurrentGame().nextPlacement();
        } else if (rotateShipButton == object) {
            if (shipSelected) {
                selectedShip.rotateShip();
                updateSelectedShipLabels();
            }
        } else {
            // Grid button selected
            Vector buttonPos = new Vector(e.getActionCommand());

            if (shipSelected && canPlaceAt(shipGrid, selectedShip.getSize(),
                    buttonPos, selectedShip.getDirection())) {
                // If ship currently selected
                shipSelected = false;

                selectedShip.setPosition(new Vector(buttonPos));
                Vector placePos = new Vector(selectedShip.getPosition());

                for (int i = 0; i < selectedShip.getSize(); i++) {
                    shipGrid[placePos.getY()][placePos.getX()] = selectedShip;
                    placePos.add(selectedShip.getDirection().getVec());
                }

                selectedShip = null;

                updateGridImages();
                updateSelectedShipLabels();

            } else if (!shipSelected && shipGrid[buttonPos.getY()][buttonPos.getX()] != null) {
                // If no ship selected and space selected has a ship
                shipSelected = true;

                selectedShip = shipGrid[buttonPos.getY()][buttonPos.getX()];

                // Remove ship from array
                Vector checkPos = new Vector(selectedShip.getPosition());
                for (int i = 0; i < selectedShip.getSize(); i++) {
                    shipGrid[checkPos.getY()][checkPos.getX()] = null;
                    checkPos.add(selectedShip.getDirection().getVec());
                }

                updateGridImages();
                updateSelectedShipLabels();
            }
        }
    }


    private void updateGridImages() {
        for (JButton[] row : gridButtons) {
            for (JButton gridButton : row) {
                gridButton.setIcon(null);
            }
        }

        ArrayList<ShipData> shipArrayList = convertShipGridToShipArray(shipGrid);

        for (ShipData ship : shipArrayList) {
            Vector checkPos = new Vector(ship.getPosition());
            for (int i = 0; i < ship.getSize(); i++) {
                switch (ship.getDirection()) {
                    case VERTICAL:
                        if (ship.getPosition().equals(checkPos)) {
                            gridButtons[checkPos.getY()][checkPos.getX()]
                                    .setIcon(boatEndUpScaled);
                        }  else if (ship.getAltPosition().equals(checkPos)) {
                            gridButtons[checkPos.getY()][checkPos.getX()]
                                    .setIcon(boatEndDownScaled);
                        } else {
                            gridButtons[checkPos.getY()][checkPos.getX()]
                                    .setIcon(boatMiddleVerticalScaled);
                        }
                        break;
                    case HORIZONTAL:
                        if (ship.getPosition().equals(checkPos)) {
                            gridButtons[checkPos.getY()][checkPos.getX()]
                                    .setIcon(boatEndLeftScaled);
                        }  else if (ship.getAltPosition().equals(checkPos)) {
                            gridButtons[checkPos.getY()][checkPos.getX()]
                                    .setIcon(boatEndRightScaled);
                        } else {
                            gridButtons[checkPos.getY()][checkPos.getX()]
                                    .setIcon(boatMiddleHorizontalScaled);
                        }
                        break;
                    default:
                        throw new IllegalStateException("Ship direction in illegal state: " 
                                                        + ship.getDirection());
                }
                checkPos.add(ship.getDirection().getVec());
            }
        }
    }

    private void updateSelectedShipLabels() {
        String nameLabel = "Selected ship: ";
        String directionLabel = "Orientation: ";
        String sizeLabel = "Size: ";

        if (shipSelected) {
            nameLabel = nameLabel + selectedShip.getName();
            switch (selectedShip.getDirection()) {
                case HORIZONTAL -> directionLabel = directionLabel + "Horizontal";
                case VERTICAL -> directionLabel = directionLabel + "Vertical";
                default -> throw new RuntimeException("Selected Ship has no direction");
            }
            sizeLabel = sizeLabel + selectedShip.getSize();
        } else {
            nameLabel = nameLabel + "None";
            directionLabel = directionLabel + "None";
            sizeLabel = sizeLabel + "None";
        }

        selectedShipNameLabel.setText("<html>" + nameLabel + "</html>");
        selectedShipDirectionLabel.setText("<html>" + directionLabel + "</html>");
        selectedShipSizeLabel.setText("<html>" + sizeLabel + "</html>");
    }
    
    private void scaleBoatImageIcons() {
        Dimension dimensions = gridButtons[0][0].getSize();
        int w = (int) (dimensions.width * 1.2f);
        int h = (int) (dimensions.height * 1.2f);

        boatMiddleHorizontalScaled = scaleImage(boatMiddleHorizontal, w, h);
        boatMiddleVerticalScaled = scaleImage(boatMiddleVertical, w, h);
        boatEndLeftScaled = scaleImage(boatEndLeft, w, h);
        boatEndRightScaled = scaleImage(boatEndRight, w, h);
        boatEndDownScaled = scaleImage(boatEndDown, w, h);
        boatEndUpScaled = scaleImage(boatEndUp, w, h);

        updateGridImages();
    }
}
