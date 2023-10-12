package com.battleship.game.listeners;

import com.battleship.game.Main;

// A template for other listeners to extend while implementing their intended listener
public abstract class BaseLis {
    protected Main main;

    public BaseLis(Main main) {
        this.main = main;
    }
}
