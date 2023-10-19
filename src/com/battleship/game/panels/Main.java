package com.battleship.game.panels;

import java.awt.*; 
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;



public class Main implements ActionListener {
    /** 
    URL titleUrl = getClass().getClassLoader().getResource("com/battleship/game/assets/title_pixelart.png");
    assert titleUrl != null;

    URL crossUrl = getClass().getClassLoader().getResource("com/battleship/game/assets/Cross.png");
    assert crossUrl != null;
    URL stripeHorizontalUrl = getClass().getClassLoader().getResource("com/battleship/game/assets/StripeHorizontal.png");
    assert stripeHorizontalUrl != null;
    URL stripeVerticalUrl = getClass().getClassLoader().getResource("com/battleship/game/assets/StripeVertical.png");
    assert stripeVerticalUrl != null;
    */
    


    int totalShips = 5;

    JFrame mainFrame;
    
    JPanel mainPanel;

    JPanel boardPanel;

    JButton[][] map = new JButton[10][10];

    Ship[][] enemyShips = new BotPlacing().botPlacing();

    public boolean[][] enemyHit = new boolean[10][10];



    private void checkButton(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            for (int p = 0; p < 10; p++) {
                if (("button" + i + p).equals(e.getActionCommand())) {
                    checkHitOrMiss(enemyShips[i][p],i,p);
                }
            }
        }
    }


    private void createScreen() {

        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();

        mainFrame = new JFrame("panel");

        mainPanel = new JPanel();
        
        boardPanel = new JPanel();

        boardPanel.setBackground(Color.blue);

        GridLayout layout = new GridLayout(10,10);
        layout.setHgap(5); 
        layout.setVgap(5); 

        boardPanel.setPreferredSize(new Dimension(600, 600));

        boardPanel.setLayout(layout);

        mainPanel.add(boardPanel);
        
        mainFrame.add(mainPanel);

        mainFrame.setVisible(true);

        mainFrame.setResizable(false);

        device.setFullScreenWindow(mainFrame);
        createMap();
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }

    private void createMap() {
        for (int i = 0; i < 10; i++) {
            for (int p = 0; p < 10; p++) {
                map[i][p] = new JButton("x");
                map[i][p].setActionCommand("button" + i + p);
                map[i][p].addActionListener(this);
                boardPanel.add(map[i][p]);
            }
        }
    }

    private void checkHit(Ship currentShip, int hitY, int hitX) {
        currentShip.hp = currentShip.hp - 1;
        if (currentShip.hp == 0) {
            sinkShip(currentShip);
            totalShips--;
            if (totalShips == 0){
                gameEnd();
            }
        } else {
            map[hitY][hitX].setIcon(null);
        }
    }

    private void sinkShip(Ship currentShip) {
        if (currentShip.rotation) {
            for (int i = 0; i < currentShip.length; i++) {
                 map[currentShip.locationStart.y + i][currentShip.locationStart.x].setIcon(null);
                 //message Ship currentShip.name sunk!
            }
        } else {
            for (int i = 0; i < currentShip.length; i++) {
                map[currentShip.locationStart.y][currentShip.locationStart.x + i].setIcon(null);
                //message Ship currentShip.name sunk!
            }
        }
    }

    private void gameEnd() {

    }

    private void checkHitOrMiss(Ship currentShip, int checkY, int checkX) {
        map[checkY][checkX].setEnabled(false);
        if (currentShip == null) {
            map[checkY][checkX].setText("miss");
        } else {
            checkHit(currentShip,checkY,checkX);
        }
    }

    public static void main(String[] args) {
        new Main().createScreen();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkButton(e);
    }
}




