package com.battleship.game.panels;

import com.battleship.game.Main;
import com.battleship.game.enums.GameState;
import com.battleship.game.logic.ShipData;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BotAttackPanel extends AttackPanel {
    private final ImageIcon boatMiddleHorizontal;
    private final ImageIcon boatMiddleVertical;
    private final ImageIcon boatEndLeft;
    private final ImageIcon boatEndRight;
    private final ImageIcon boatEndDown;
    private final ImageIcon boatEndUp;

    private ImageIcon boatMiddleHorizontalScaled;
    private ImageIcon boatMiddleVerticalScaled;
    private ImageIcon boatEndLeftScaled;
    private ImageIcon boatEndRightScaled;
    private ImageIcon boatEndDownScaled;
    private ImageIcon boatEndUpScaled;


    public BotAttackPanel(Main main) {
        super(main, GameState.PLAYER_2_ATTACK);
        // Loads the images for the different parts of the boat
        URL boatMiddleHorizontalURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_middle_horizontal.png");
        assert boatMiddleHorizontalURL != null;
        boatMiddleHorizontal = new ImageIcon(boatMiddleHorizontalURL);
        URL boatMiddleVerticalURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_middle_vertical.png");
        assert boatMiddleVerticalURL != null;
        boatMiddleVertical = new ImageIcon(boatMiddleVerticalURL);
        URL boatEndLeftURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_end_left.png");
        assert boatEndLeftURL != null;
        boatEndLeft = new ImageIcon(boatEndLeftURL);
        URL boatEndRightURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_end_right.png");
        assert boatEndRightURL != null;
        boatEndRight = new ImageIcon(boatEndRightURL);
        URL boatEndDownURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_end_down.png");
        assert boatEndDownURL != null;
        boatEndDown = new ImageIcon(boatEndDownURL);
        URL boatEndUpURL = getClass().getClassLoader()
                .getResource("com/battleship/game/assets/boat_end_up.png");
        assert boatEndUpURL != null;
        boatEndUp = new ImageIcon(boatEndUpURL);
    }

    @Override
    void updateGridImages() {

    }

    @Override
    void scaleImages() {
        super.scaleImages();
        Dimension dimensions = this.getSize();
        int w = (int) (dimensions.width * 1.2f);
        int h = (int) (dimensions.height * 1.2f);

        boatMiddleHorizontalScaled = new ImageIcon(boatMiddleHorizontal
                .getImage().getScaledInstance(w, h, 8));
        boatMiddleVerticalScaled = new ImageIcon(boatMiddleVertical
                .getImage().getScaledInstance(w, h, 8));
        boatEndLeftScaled = new ImageIcon(boatEndLeft
                .getImage().getScaledInstance(w, h, 8));
        boatEndRightScaled = new ImageIcon(boatEndRight
                .getImage().getScaledInstance(w, h, 8));
        boatEndDownScaled = new ImageIcon(boatEndDown
                .getImage().getScaledInstance(w, h, 8));
        boatEndUpScaled = new ImageIcon(boatEndUp
                .getImage().getScaledInstance(w, h, 8));

        updateGridImages();
    }
}
