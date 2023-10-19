package com.battleship.game;

import com.battleship.game.enums.BotAlgorithmChoice;
import com.battleship.game.panels.ShipPlacementPanel;

import javax.swing.*;
import java.net.URL;

public class HumanVsBotGame extends Game {
    BotAlgorithmChoice algorithm;


    public HumanVsBotGame(Main main, BotAlgorithmChoice algorithm) {
        super(main);
        this.main = main;
        this.algorithm = algorithm;
    }

    @Override
    public void startNewGame() {
        ShipPlacementPanel shipPlacementPanel = new ShipPlacementPanel(main);
        main.getMainPanel().add(shipPlacementPanel);
        main.changeGameState(shipPlacementPanel.getPanelState());

    }

    @Override
    public void startSavedGame(String saveString) {
        URL saveURL = getClass()
                .getClassLoader()
                .getResource("com/battleship/game/assets/" + saveString + ".botSave");

        if (saveURL == null) {
            JOptionPane.showMessageDialog(main.getFrame(),
                    "No existing save found",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        //TODO: Load game data using url
    }

    public void saveGame() {
        //TODO: Save game to text file with data abt game using .botSave for file extension
    }
}
