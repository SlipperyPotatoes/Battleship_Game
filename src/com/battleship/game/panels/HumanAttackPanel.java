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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static com.battleship.game.utils.AssetUtils.loadIcon;
import static com.battleship.game.utils.AssetUtils.scaleImage;

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

                buttonGrid[y][x] = gridButton;
                this.add(gridButton);
            }
        }

        String playerTurn;
        switch (gameState) {
            case PLAYER_1_ATTACK -> playerTurn = "PLAYER 1";
            case PLAYER_2_ATTACK -> playerTurn = "PLAYER 2";
            default -> throw new IllegalStateException("Attack panel set to state: " + gameState);
        }
        JLabel turnLabel = new JLabel("<html>" + "TURN: " + playerTurn + "</html>");
        this.add(turnLabel);

        addStatLabels();

        for (int i = 0; i < 4; i++) {
            this.add(new JLabel());
        }

        if (gameState == GameState.PLAYER_1_ATTACK) {
            saveGameButton = new JButton("<html>" + "Save game" + "</html>");
            saveGameButton.addActionListener(this);
            this.add(saveGameButton);
        }

        updateGridImages();
        updateLabels();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();

        if (!main.getCurrentGame().canDoAction()) {
            return;
        }

        if (saveGameButton == object) {
            main.getCurrentGame().saveGame();
            return;
        }

        Vector buttonPos = new Vector(e.getActionCommand());
        if (enemyPlayerData.hasBeenAttackedAt(buttonPos.getX(), buttonPos.getY())) {
            return;
        }
        main.getCurrentGame().setCanDoAction(false);

        enemyPlayerData.attackAt(buttonPos);

        updateGridImages();
        updateLabels();
        if (enemyPlayerData.allShipsDead()) {
            Timer timer;
            switch (main.getGameState()) {
                case PLAYER_1_ATTACK -> timer = new Timer(1000, unused -> main.finishGame("PLAYER 1"));
                case PLAYER_2_ATTACK -> timer = new Timer(1000, unused -> main.finishGame("PLAYER 2"));
                default -> throw new IllegalStateException("Attacking with gameState: " + main.getGameState());
            }

            timer.setRepeats(false);
            timer.start();

            return;
        }

        Timer timer = new Timer(1500, unused -> main.getCurrentGame().nextAttack());
        timer.setRepeats(false);
        timer.start();
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
