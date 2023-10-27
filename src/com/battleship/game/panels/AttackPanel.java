package com.battleship.game.panels;

import com.battleship.game.Main;
import com.battleship.game.botfiles.Ship;
import com.battleship.game.enums.GameState;
import com.battleship.game.logic.Game;
import com.battleship.game.logic.PlayerData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import static com.battleship.game.utils.AssetUtils.loadIcon;

public abstract class AttackPanel extends BasePanel {
    final ImageIcon attackHit;
    final ImageIcon attackMiss;

    ImageIcon attackHitScaled;
    ImageIcon attackMissScaled;
    PlayerData enemyPlayerData;

    private JLabel shipsSunkLabel;
    private JLabel attackCountLabel;
    private JLabel hitCountLabel;
    private JLabel missCountLabel;



    public AttackPanel(Main main, GameState gameState, PlayerData enemyPlayerData) {
        super(main, gameState);

        this.setBackground(Color.GRAY);
        this.setLayout(new GridLayout(0, Game.SIZE_X));

        this.enemyPlayerData = enemyPlayerData;

        attackHit = loadIcon("attack_hit.png");
        attackMiss = loadIcon("attack_miss.png");

        shipsSunkLabel = new JLabel();
        shipsSunkLabel.setBackground(Color.GRAY);
        attackCountLabel = new JLabel();
        attackCountLabel.setBackground(Color.GRAY);
        hitCountLabel = new JLabel();
        hitCountLabel.setBackground(Color.GRAY);
        missCountLabel = new JLabel();
        missCountLabel.setBackground(Color.GRAY);

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleImages();
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

    abstract void updateGridImages();

    abstract void scaleImages();

    public void updateLabels() {
        int shipsSunk = 0;
        int attacks = 0;
        int hits = 0;

        ArrayList<Ship> shipArray = enemyPlayerData.getShipArray();
        for (Ship ship : shipArray) {
            if (ship.isDead()) {
                shipsSunk++;
            }
        }

        boolean[][] placesAttacked = enemyPlayerData.getPlacesBeenAttacked();
        for (int y = 0; y < placesAttacked.length; y++) {
            for (int x = 0; x < placesAttacked[0].length; x++) {
                if (placesAttacked[y][x]) {
                    attacks++;
                    if (enemyPlayerData.shipAt(x, y)) {
                        hits++;
                    }
                }
            }
        }

        shipsSunkLabel.setText("<html>" + "Ships sunk: " + shipsSunk + "</html>");
        attackCountLabel.setText("<html>" + "Attack count: " + attacks + "</html>");
        hitCountLabel.setText("<html>" + "Hit count: " + hits + "</html>");
        missCountLabel.setText("<html>" + "Miss count: " + (attacks - hits) + "</html>");
    }

    void addStatLabels() {
        this.add(shipsSunkLabel);
        this.add(attackCountLabel);
        this.add(hitCountLabel);
        this.add(missCountLabel);
    }
}
