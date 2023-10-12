package com.battleship.game.panels;

import com.battleship.game.GameState;

import javax.swing.*;

// A template for other panels to extend
public abstract class BasePanel extends JPanel {
    protected JPanel mainPanel;
    final protected GameState panelState;

    public BasePanel(JPanel mainPanel, GameState state) {
        this.mainPanel = mainPanel;
        this.panelState = state;
    }

    public GameState getPanelState() {
        return panelState;
    }
}
