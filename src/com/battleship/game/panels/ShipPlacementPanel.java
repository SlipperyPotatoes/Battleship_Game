package com.battleship.game.panels;

import com.battleship.game.logic.Game;
import com.battleship.game.Main;
import com.battleship.game.logic.ShipData;
import com.battleship.game.enums.Direction;
import com.battleship.game.enums.GameState;
import com.battleship.game.utils.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

import static com.battleship.game.utils.ShipUtils.*;

public class ShipPlacementPanel extends BasePanel implements ActionListener {
    ShipData[][] shipGrid;
    ArrayList<ShipData> shipArrayList;
    boolean shipSelected;
    ShipData selectedShip;
    JButton[][] gridButtons;
    JButton finishPlacementButton;
    JButton rotateShipButton;
    JLabel selectedShipNameLabel;
    JLabel selectedShipDirectionLabel;
    JLabel selectedShipSizeLabel;

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


    public ShipPlacementPanel(Main main) {
        super(main, GameState.PLACE_SHIPS);

        // Loads the images for the different parts of the boat
        URL boatMiddleHorizontalURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_middle_horizontal.png");
        assert boatMiddleHorizontalURL != null;
        boatMiddleHorizontal = new ImageIcon(boatMiddleHorizontalURL);
        URL boatMiddleVerticalURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_middle_vertical.png");
        assert boatMiddleVerticalURL != null;
        boatMiddleVertical = new ImageIcon(boatMiddleVerticalURL);
        URL boatEndLeftURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_end_left.png");
        assert boatEndLeftURL != null;
        boatEndLeft = new ImageIcon(boatEndLeftURL);
        URL boatEndRightURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_end_right.png");
        assert boatEndRightURL != null;
        boatEndRight = new ImageIcon(boatEndRightURL);
        URL boatEndDownURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_end_down.png");
        assert boatEndDownURL != null;
        boatEndDown = new ImageIcon(boatEndDownURL);
        URL boatEndUpURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_end_up.png");
        assert boatEndUpURL != null;
        boatEndUp = new ImageIcon(boatEndUpURL);



        shipGrid = new ShipData[Game.SIZE_Y][Game.SIZE_X];

        shipSelected = false;
        selectedShip = null;

        // Places ships next to each other with gaps in between
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
        
        updateShipArray();
        
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
                        gridButton.setBackground(Color.CYAN);
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


        GridLayout gridLayout = new GridLayout(0, 10);
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
            System.out.println(convertShipGridToString(shipGrid));

            main.getCurrentGame().setNextPlayerShips(shipGrid);
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

                updateShipArray();
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

                updateShipArray();
                updateGridImages();
                updateSelectedShipLabels();
            }
        }
    }

    private void updateShipArray() {
        shipArrayList = convertShipGridToShipArray(shipGrid);
    }

    private void updateGridImages() {
        for (JButton[] row : gridButtons) {
            for (JButton gridButton : row) {
                gridButton.setIcon(null);
            }
        }

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


        boatMiddleHorizontalScaled = new ImageIcon(boatMiddleHorizontal
                .getImage().getScaledInstance(w, h, 8));
        boatMiddleVerticalScaled = new ImageIcon(boatMiddleVertical
                .getImage().getScaledInstance(w, h, 8));
        boatEndLeftScaled = new ImageIcon(boatEndLeft
                .getImage().getScaledInstance(w, h, 8));
        boatEndRightScaled = new ImageIcon(boatEndRight
                .getImage().getScaledInstance(w, h, 8));
        boatEndDownScaled = new ImageIcon(boatEndDown
                .getImage().getScaledInstance(w, h, 8));
        boatEndUpScaled = new ImageIcon(boatEndUp
                .getImage().getScaledInstance(w, h, 8));

        updateGridImages();
    }
}
