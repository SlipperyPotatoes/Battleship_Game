package com.battleship.game;

import com.battleship.game.enums.BotAlgorithmChoice;

public class BotData extends PlayerData{
    Bot bot;

    BotData(BotAlgorithmChoice algorithm) {
        super();
        bot = new Bot(algorithm);
    }


}
