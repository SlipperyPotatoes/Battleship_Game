package com.battleship.game.panels;

import com.battleship.game.GameState;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MenuPanel extends BasePanel {

    public MenuPanel(JPanel mainPanel) {
        super(mainPanel, GameState.MAIN_MENU);
        this.setBackground(Color.BLUE);
        JLabel title = new JLabel();

        URL titleUrl = getClass().getClassLoader().getResource("com/battleship/game/assets/title_pixelart.png");
        assert titleUrl != null;
        ImageIcon icon = new ImageIcon(titleUrl);

        title.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        title.setIcon(icon);
        this.add(title);
    }
}
