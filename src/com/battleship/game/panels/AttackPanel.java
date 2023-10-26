package com.battleship.game.panels;

import com.battleship.game.Main;
import com.battleship.game.enums.GameState;
import com.battleship.game.logic.Game;
import com.battleship.game.logic.PlayerData;
import com.battleship.game.utils.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.net.URL;

import static com.battleship.game.utils.assetsUtils.loadIcon;
import static com.battleship.game.utils.assetsUtils.scaleImage;

public abstract class AttackPanel extends BasePanel {
    final ImageIcon attackHit;
    final ImageIcon attackMiss;

    ImageIcon attackHitScaled;
    ImageIcon attackMissScaled;
    PlayerData enemyPlayerData;

    JLabel shipsSunkLabel;
    JLabel numOfHitsLabel;
    JLabel numOfMissesLabel;

    public AttackPanel(Main main, GameState gameState, PlayerData enemyPlayerData) {
        super(main, gameState);

        this.setBackground(Color.BLUE);
        this.setLayout(new GridLayout(0, Game.SIZE_X));

        this.enemyPlayerData = enemyPlayerData;

        attackHit = loadIcon("attack_hit.png");
        attackMiss = loadIcon("attack_miss.png");

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
}
