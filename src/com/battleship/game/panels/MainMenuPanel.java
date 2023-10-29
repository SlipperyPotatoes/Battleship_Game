package com.battleship.game.panels;

import static com.battleship.game.utils.AssetUtils.loadIcon;

import com.battleship.game.Main;
import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.enums.GameState;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 * Panel for showing the game's main menu.
 */
public class MainMenuPanel extends BasePanel implements ActionListener {
    private final JButton newBotGameButton;
    private final JButton loadBotGameButton;
    private final JButton changeAlgorithmButton;
    private BotAlgorithm botAlgorithm;

    /**
    * Creates a new main menu panel with all the buttons and labels added.
    *
    * @param main Reference to main
    */
    public MainMenuPanel(Main main) {
        super(main, GameState.MAIN_MENU);
        this.setBackground(Color.BLUE);

        botAlgorithm = BotAlgorithm.SIMPLE;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel();
        ImageIcon icon = loadIcon("title_pixel_art.png");
        title.setIcon(icon);
        title.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        newBotGameButton = new JButton();
        newBotGameButton.setText("New Human vs. Bot Game");
        newBotGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newBotGameButton.addActionListener(this);

        loadBotGameButton = new JButton();
        loadBotGameButton.setText("Load Human vs. Bot Game");
        loadBotGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadBotGameButton.addActionListener(this);

        changeAlgorithmButton = new JButton();
        changeAlgorithmButton.setText("Current Algorithm: \n" + botAlgorithm);
        changeAlgorithmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        changeAlgorithmButton.addActionListener(this);

        JButton exitButton = new JButton();
        exitButton.setText("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener((e) -> System.exit(0));

        this.add(title);
        this.add(newBotGameButton, BorderLayout.CENTER);
        this.add(loadBotGameButton, BorderLayout.CENTER);
        this.add(changeAlgorithmButton, BorderLayout.CENTER);
        this.add(exitButton, BorderLayout.CENTER);
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
