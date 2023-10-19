package com.battleship.game.panels;

import com.battleship.game.Main;
import com.battleship.game.enums.BotAlgorithmChoice;
import com.battleship.game.enums.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

//TODO: Fix button placement, maybe add an option to select a save file or load from save name
public class MenuPanel extends BasePanel implements ActionListener {
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton changeAlgorithmButton;
    private BotAlgorithmChoice botAlgorithm;

    public MenuPanel(Main main) {
        super(main, GameState.MAIN_MENU);
        this.setBackground(Color.BLUE);

        JLabel title = new JLabel();
        URL titleUrl = getClass().getClassLoader().getResource("com/battleship/game/assets/title_pixelart.png");
        assert titleUrl != null;
        ImageIcon icon = new ImageIcon(titleUrl);
        title.setIcon(icon);
        title.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));

        newGameButton = new JButton();
        newGameButton.setText("New Game");
        newGameButton.addActionListener(this);

        loadGameButton = new JButton();
        loadGameButton.setText("Load Game");
        loadGameButton.addActionListener(this);

        changeAlgorithmButton = new JButton();
        botAlgorithm = BotAlgorithmChoice.SIMPLE;
        changeAlgorithmButton.setText("Current Algorithm: \n" + botAlgorithm.toString());
        changeAlgorithmButton.addActionListener(this);

        this.add(title);
        this.add(newGameButton);
        this.add(loadGameButton);
        this.add(changeAlgorithmButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();
        if (changeAlgorithmButton == object) {
            botAlgorithm = botAlgorithm.next();
            changeAlgorithmButton.setText("Current Algorithm: \n" + botAlgorithm.toString());
        } else if (newGameButton == object) {
            main.startBotNewGame(botAlgorithm);
        } else if (loadGameButton == object) {
            main.loadPreviousBotGame(botAlgorithm);
        }
    }
}
