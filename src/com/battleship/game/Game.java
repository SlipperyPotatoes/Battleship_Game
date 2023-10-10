package com.battleship.game;

import com.battleship.game.panels.*;

import javax.swing.*;
import java.awt.*;

public class Game {
    private JFrame frame;
    private JPanel mainPanel;
    private MenuPanel menuPanel;
    private AttackPanel attackPanel;
    private GameState gameState;

    public void run() {
        frame = new JFrame();


        CardLayout cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        menuPanel = new MenuPanel(mainPanel);
        attackPanel = new AttackPanel(mainPanel);
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



    public static void main(String[] args) {
        new Game().run();
    }
}
