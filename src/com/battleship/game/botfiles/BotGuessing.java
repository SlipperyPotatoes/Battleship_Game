package com.battleship.game.botfiles;

import com.battleship.game.utils.Vector;

/**
 * Abstract call both BotGuessing classes inherit from in order for the bot object to only need
 * to hold one generic BotGuessing object.
 */
public abstract class BotGuessing {
    public abstract Vector findNextAttack();

    public abstract BotSaveData toSaveData();
}
