package com.battleship.game.panels;

import com.battleship.game.Main;
import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.enums.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

//TODO: Fix button placement, maybe add an option to select a save file or load from save name
public class MenuPanel extends BasePanel implements ActionListener {
    private final JButton newBotGameButton;
    private final JButton loadBotGameButton;
    private final JButton changeAlgorithmButton;
    private BotAlgorithm botAlgorithm;

    public MenuPanel(Main main) {
        super(main, GameState.MAIN_MENU);
        this.setBackground(Color.BLUE);

        JLabel title = new JLabel();
        URL titleUrl = getClass().getClassLoader().getResource("com/battleship/game/assets/title_pixelart.png");
        assert titleUrl != null;
        ImageIcon icon = new ImageIcon(titleUrl);
        title.setIcon(icon);
        title.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));

        newBotGameButton = new JButton();
        newBotGameButton.setText("New Human vs. Bot Game");
        newBotGameButton.addActionListener(this);

        loadBotGameButton = new JButton();
        loadBotGameButton.setText("Load Human vs. Bot Game");
        loadBotGameButton.addActionListener(this);

        changeAlgorithmButton = new JButton();
        botAlgorithm = BotAlgorithm.SIMPLE;
        changeAlgorithmButton.setText("Current Algorithm: \n" + botAlgorithm);
        changeAlgorithmButton.addActionListener(this);

        this.add(title);
        this.add(newBotGameButton);
        this.add(loadBotGameButton);
        this.add(changeAlgorithmButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();
        if (changeAlgorithmButton == object) {
            botAlgorithm = botAlgorithm.next();
            changeAlgorithmButton.setText("Current Algorithm: \n" + botAlgorithm.toString());
        } else if (newBotGameButton == object) {
            main.startNewBotGame(botAlgorithm);
        } else if (loadBotGameButton == object) {
            main.loadPreviousBotGame(botAlgorithm);
        }
    }
}
