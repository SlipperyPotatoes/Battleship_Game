package com.battleship.game;

import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.enums.GameState;
import com.battleship.game.panels.MenuPanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private GameState gameState;
    private Game game;

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        frame = new JFrame();


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        MenuPanel menuPanel = new MenuPanel(this);
        mainPanel.add(menuPanel, menuPanel.getPanelState().toString());

        // Setup frame
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        // Show menu
        changeGameState(GameState.MAIN_MENU);
    }

    public void changeGameState(GameState gameState) {
        if (gameState == this.gameState) {
            return;
        }
        this.gameState = gameState;
        cardLayout.show(mainPanel, gameState.toString());
    }

    public GameState getGameState() {
        return gameState;
    }

    public void startNewBotGame(BotAlgorithm algorithm) {
        game = new HumanVsBotGame(this, algorithm);
        game.startNewGame();
    }

    public void loadPreviousBotGame(BotAlgorithm algorithm) {
        game = new HumanVsBotGame(this, algorithm);
        game.startSavedGame("game");
    }

    public void finishGame(String winner) {
        //TODO: Add popup or something that says who won the game
        game = null;
        changeGameState(GameState.MAIN_MENU);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Game getCurrentGame() {
        return game;
    }
}
