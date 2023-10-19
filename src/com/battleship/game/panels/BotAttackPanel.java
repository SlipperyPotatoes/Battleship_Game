package com.battleship.game.panels;

import com.battleship.game.enums.GameState;
import com.battleship.game.Main;

public class BotAttackPanel extends AttackPanel {

    public BotAttackPanel(Main main) {
        super(main, GameState.BOT_ATTACK);
    }
}
