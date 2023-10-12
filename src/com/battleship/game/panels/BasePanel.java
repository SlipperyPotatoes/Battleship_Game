package com.battleship.game.panels;

import com.battleship.game.Main;
import com.battleship.game.GameState;

import javax.swing.*;

// A template for other panels to extend
public abstract class BasePanel extends JPanel {
    protected Main main;

    final protected GameState panelState;

    public BasePanel(Main main, GameState state) {
        this.main = main;
        this.panelState = state;
    }

    public GameState getPanelState() {
        return panelState;
    }
}
