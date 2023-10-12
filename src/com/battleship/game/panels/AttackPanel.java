package com.battleship.game.panels;

import com.battleship.game.GameState;

import javax.swing.*;
import java.awt.*;

public class AttackPanel extends BasePanel {
    public AttackPanel(JPanel mainPanel) {
        super(mainPanel, GameState.ATTACK);
        this.setBackground(Color.RED);
    }
}
