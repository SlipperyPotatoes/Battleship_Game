package com.battleship.game;

import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.enums.GameState;
import com.battleship.game.logic.Game;
import com.battleship.game.logic.HumanVsBotGame;
import com.battleship.game.panels.MainMenuPanel;
import com.battleship.game.utils.AssetUtils;
import java.awt.*;
import javax.swing.*;

/**
 * TODO ADD COMMENT.
 */
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

        mainPanel.setPreferredSize(new Dimension(800, 700));


        MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
        mainPanel.add(mainMenuPanel, mainMenuPanel.getPanelState().toString());

        // Setup frame
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("SUPER AMAZING BATTLESHIP GAME");
        frame.setIconImage(AssetUtils.loadIcon("pixel_art_is_my_passion.png").getImage());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Show menu
        changeGameState(GameState.MAIN_MENU);
    }

    /**  
     * Switches between panels shown.
     */
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

    /**
     * TODO ADD COMMENT.
     */
    public void startNewBotGame(BotAlgorithm algorithm) {
        game = new HumanVsBotGame(this, algorithm);
        game.startNewGame();
    }

    /**
     * TODO ADD COMMENT.
     */
    public void loadPreviousBotGame(BotAlgorithm algorithm) {
        game = new HumanVsBotGame(this, algorithm);
        game.startSavedGame("saveGame");
    }

    /**
     * TODO ADD COMMENT.
     */
    public void endGame() {
        changeGameState(GameState.MAIN_MENU);
        for (int i = 0; i < mainPanel.getComponentCount(); i++) {
            if (!mainPanel.getComponent(i).getClass().isAssignableFrom(MainMenuPanel.class)) {
                mainPanel.remove(i);
                i--;
            }
        }
        game = null;
    }

    /**
     * when a player or bot wins the game show a message that the player or bot has won.
     */
    public void finishGame(String winner) {
        JOptionPane.showMessageDialog(frame,
                winner + " won!",
                "Winner",
                JOptionPane.PLAIN_MESSAGE);
        endGame();
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
