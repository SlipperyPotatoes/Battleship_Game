package com.battleship.game;

import com.battleship.game.panels.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MenuPanel menuPanel;
    private AttackPanel attackPanel;
    private GameState gameState;
    private Game game;

    void run() {
        frame = new JFrame();


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel(this);
        attackPanel = new PlayerAttackPanel(this);

        mainPanel.add(menuPanel, menuPanel.getPanelState().toString());
        mainPanel.add(attackPanel, attackPanel.getPanelState().toString());

        // Setup frame
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        // Show menu
        cardLayout.show(mainPanel, GameState.MAIN_MENU.toString());
    }

    public void changeGameState(GameState gameState) {
        if (gameState == this.gameState) {
            return;
        }
        this.gameState = gameState;
        cardLayout.show(mainPanel, gameState.toString());
    }

    public void startBotNewGame(BotAlgorithm algorithm) {
        game = new BotVsPlayerGame(this, algorithm);

    }

    public void loadPreviousBotGame(BotAlgorithm algorithm) {
        game = new BotVsPlayerGame(this, algorithm);

    }

    public JFrame getFrame() {
        return frame;
    }
    public static void main(String[] args) {
        new Main().run();
    }
}
