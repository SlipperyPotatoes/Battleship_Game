package com.battleship.game.panels;

import com.battleship.game.Main;
import com.battleship.game.enums.GameState;
import javax.swing.*;

/**
 * A template for other panels to extend.
 * <p></p>
 * The intention behind the panels is to help organize data so that any data that only
 * needs to exist within that gameState is only stored within that panel and other data
 * that needs to exist between different gameStates is also stored in non-panel objects.
 */ 
public abstract class BasePanel extends JPanel {
    protected Main main;
    protected final GameState panelState;

    /**
     * Creates a panel with a reference to the Main object and a state which
     * represents the state that main should be in when showing this panel.
     */
    public BasePanel(Main main, GameState state) {
        this.main = main;
        this.panelState = state;
    }

    public GameState getPanelState() {
        return panelState;
    }
}
