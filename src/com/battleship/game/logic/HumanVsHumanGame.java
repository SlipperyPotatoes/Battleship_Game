package com.battleship.game.logic;

import com.battleship.game.Main;
import com.battleship.game.enums.GameState;
import com.battleship.game.panels.HumanAttackPanel;
import com.battleship.game.panels.ShipPlacementPanel;

/**
 * Unused implementation of the Game class for a version of BattleShip
 * with 2 human players playing against each other
 */
public class HumanVsHumanGame extends Game {


    public HumanVsHumanGame(Main main) {
        super(main);
    }

    @Override
    public void startNewGame() {
        ShipPlacementPanel shipPlacementPanel = new ShipPlacementPanel(main);
        main.getMainPanel().add(shipPlacementPanel,
                shipPlacementPanel.getPanelState().toString());



        main.changeGameState(shipPlacementPanel.getPanelState());
    }

    @Override
    public void nextPlacement() {
        if (player2.shipsNotSet()) {
            ShipPlacementPanel shipPlacementPanel = new ShipPlacementPanel(main);
            main.getMainPanel().add(shipPlacementPanel,
                    shipPlacementPanel.getPanelState().toString());
            return;
        }

        HumanAttackPanel player1AttackPanel = new HumanAttackPanel(main, GameState.PLAYER_1_ATTACK, player2);
        main.getMainPanel().add(player1AttackPanel,
                player1AttackPanel.getPanelState().toString());

        HumanAttackPanel player2AttackPanel = new HumanAttackPanel(main, GameState.PLAYER_2_ATTACK, player1);
        main.getMainPanel().add(player2AttackPanel,
                player2AttackPanel.getPanelState().toString());

        main.changeGameState(GameState.PLAYER_1_ATTACK);
    }
}
