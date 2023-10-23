package com.battleship.game.logic;

import com.battleship.game.Main;
import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.enums.GameState;
import com.battleship.game.panels.BotAttackPanel;
import com.battleship.game.panels.HumanAttackPanel;
import com.battleship.game.panels.ShipPlacementPanel;

// The human will always be player 1 and the bot always player 2
public class HumanVsBotGame extends Game {
    Bot bot;


    public HumanVsBotGame(Main main, BotAlgorithm algorithm) {
        super(main);
        this.bot = new Bot(algorithm);
    }

    @Override
    public void startNewGame() {
        ShipPlacementPanel shipPlacementPanel = new ShipPlacementPanel(main);
        main.getMainPanel().add(shipPlacementPanel,
                shipPlacementPanel.getPanelState().toString());

        HumanAttackPanel humanAttackPanel = new HumanAttackPanel(main, GameState.PLAYER_1_ATTACK);
        main.getMainPanel().add(humanAttackPanel,
                humanAttackPanel.getPanelState().toString());

        BotAttackPanel botAttackPanel = new BotAttackPanel(main);
        main.getMainPanel().add(botAttackPanel,
                botAttackPanel.getPanelState().toString());

        main.changeGameState(shipPlacementPanel.getPanelState());
    }

    @Override
    public void nextPlacement() {
        player2.setBoolShips(bot.generateShipPositions());

        main.changeGameState(GameState.PLAYER_1_ATTACK);
    }
}