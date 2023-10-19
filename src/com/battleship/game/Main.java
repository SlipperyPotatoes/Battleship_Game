package com.battleship.game;

import com.battleship.game.enums.BotAlgorithmChoice;
import com.battleship.game.enums.GameState;
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
    private Game Game;

    void run() {
        frame = new JFrame();


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel(this);
        attackPanel = new HumanAttackPanel(this);

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
        changeGameState(GameState.MAIN_MENU);
    }

    public void changeGameState(GameState gameState) {
        if (gameState == this.gameState) {
            return;
        }
        this.gameState = gameState;
        cardLayout.show(mainPanel, gameState.toString());
    }

    public void startBotNewGame(BotAlgorithmChoice algorithm) {
        Game = new HumanVsBotGame(this, algorithm);
        Game.startNewGame();
        changeGameState(GameState.PLACE_SHIPS);
    }

    public void loadPreviousBotGame(BotAlgorithmChoice algorithm) {
        Game = new HumanVsBotGame(this, algorithm);
        Game.startSavedGame("game" + ".botSave");
    }

    public void saveBotGame() {

    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Game getCurrentGame() {
        return Game;
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
