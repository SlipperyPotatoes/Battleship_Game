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

public abstract class AttackPanel extends BasePanel {
    private final ImageIcon attackHit;
    private final ImageIcon attackMiss;

    ImageIcon attackHitScaled;
    ImageIcon attackMissScaled;

    private PlayerData playerData;
    private PlayerData enemyPlayerData;

    public AttackPanel(Main main, GameState gameState) {
        super(main, gameState);

        this.setBackground(Color.BLUE);
        this.setLayout(new GridLayout(0, Game.SIZE_X));

        URL attackHitURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_middle_horizontal.png");
        assert attackHitURL != null;
        attackHit = new ImageIcon(attackHitURL);
        URL attackMissURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_middle_horizontal.png");
        assert attackMissURL != null;
        attackMiss = new ImageIcon(attackMissURL);

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

    void attackEnemyAt(Vector pos) {

    }


    abstract void updateGridImages();

    void scaleImages() {
        Dimension dimensions = this.getSize();
        int w = (int) (dimensions.width * 1f);
        int h = (int) (dimensions.height * 1f);

        attackHitScaled = new ImageIcon(attackHit
                .getImage().getScaledInstance(w, h, 8));
        attackMissScaled = new ImageIcon(attackMiss
                .getImage().getScaledInstance(w, h, 8));
    }
}
