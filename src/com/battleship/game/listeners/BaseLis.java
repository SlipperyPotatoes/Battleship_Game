package com.battleship.game.listeners;

import com.battleship.game.Game;

// A template for other listeners to extend while implementing their intended listener
public abstract class BaseLis {
    protected Game game;

    public BaseLis(Game game) {
        this.game = game;
    }
}
