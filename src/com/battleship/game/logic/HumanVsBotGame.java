package com.battleship.game.logic;

import com.battleship.game.Main;
import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.enums.GameState;
import com.battleship.game.panels.BotAttackPanel;
import com.battleship.game.panels.HumanAttackPanel;
import com.battleship.game.panels.ShipPlacementPanel;
import com.battleship.game.utils.Vector;

import javax.swing.*;

// The human will always be player 1 and the bot always player 2
public class HumanVsBotGame extends Game {
    Bot bot;
    BotAlgorithm algorithm;
    BotAttackPanel botAttackPanel;

    public HumanVsBotGame(Main main, BotAlgorithm algorithm) {
        super(main);
        this.algorithm = algorithm;
    }

    @Override
    public void startNewGame() {
        ShipPlacementPanel shipPlacementPanel = new ShipPlacementPanel(main);
        main.getMainPanel().add(shipPlacementPanel,
                shipPlacementPanel.getPanelState().toString());

        main.changeGameState(shipPlacementPanel.getPanelState());
    }

    @Override
    public void startSavedGame(String saveName) {
        super.startSavedGame(saveName);
        if (player1 == null || player2 == null) {
            return;
        }

        bot = new Bot(algorithm, player1);

        HumanAttackPanel humanAttackPanel = new HumanAttackPanel(main, GameState.PLAYER_1_ATTACK, player2);
        main.getMainPanel().add(humanAttackPanel,
                humanAttackPanel.getPanelState().toString());

        botAttackPanel = new BotAttackPanel(main, player1);
        main.getMainPanel().add(botAttackPanel,
                botAttackPanel.getPanelState().toString());

        main.changeGameState(GameState.PLAYER_1_ATTACK);
    }

    // Called after the user finishes placing their ships
    @Override
    public void nextPlacement() {
        bot = new Bot(algorithm, player1.getShipGrid());

        setNextPlayerShips(bot.generateShipPositions());

        HumanAttackPanel humanAttackPanel = new HumanAttackPanel(main, GameState.PLAYER_1_ATTACK, player2);
        main.getMainPanel().add(humanAttackPanel,
                humanAttackPanel.getPanelState().toString());

        botAttackPanel = new BotAttackPanel(main, player1);
        main.getMainPanel().add(botAttackPanel,
                botAttackPanel.getPanelState().toString());

        main.changeGameState(GameState.PLAYER_1_ATTACK);
    }

    @Override
    public void nextAttack() {
        super.nextAttack();
        if (main.getGameState() == GameState.PLAYER_1_ATTACK) {
            return;
        }

        // Adds delay between screen switching to bots attack panel and bot attacking
        Timer timer = new Timer(500, e -> doBotAttack());
        timer.setRepeats(false);
        timer.start();
    }

    private void doBotAttack() {
        Vector vec = bot.findNextAttackPos();
        player1.botAttackAt(vec);
        botAttackPanel.updateGridImages();
        botAttackPanel.updateLabels();


        if (player1.allShipsDead()) {
            main.finishGame("BOT");
            return;
        }

        Timer timer = new Timer(1500, e -> nextAttack());
        timer.setRepeats(false);
        timer.start();
    }
}
