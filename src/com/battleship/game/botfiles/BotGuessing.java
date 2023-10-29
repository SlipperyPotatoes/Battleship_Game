package com.battleship.game.botfiles;

import com.battleship.game.utils.Vector;

/**
 * TODO ADD COMMENT.
 */
public abstract class BotGuessing {
    public abstract Vector findNextAttack();

    public abstract BotSaveData toSaveData();
}
