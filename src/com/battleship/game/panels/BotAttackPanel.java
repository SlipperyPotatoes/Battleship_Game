package com.battleship.game.panels;

import com.battleship.game.Main;
import com.battleship.game.enums.GameState;

public class BotAttackPanel extends AttackPanel {

    public BotAttackPanel(Main main) {
        super(main, GameState.PLAYER_2_ATTACK);
    }
}
