package com.battleship.game.logic;

import com.battleship.game.Main;
import com.battleship.game.enums.GameState;
import com.battleship.game.panels.HumanAttackPanel;
import com.battleship.game.panels.ShipPlacementPanel;


public class HumanVsHumanGame extends Game {


    public HumanVsHumanGame(Main main) {
        super(main);
    }

    @Override
    public void startNewGame() {
        ShipPlacementPanel shipPlacementPanel = new ShipPlacementPanel(main);
        main.getMainPanel().add(shipPlacementPanel,
                shipPlacementPanel.getPanelState().toString());

        HumanAttackPanel player1AttackPanel = new HumanAttackPanel(main, GameState.PLAYER_1_ATTACK);
        main.getMainPanel().add(player1AttackPanel,
                player1AttackPanel.getPanelState().toString());

        HumanAttackPanel player2AttackPanel = new HumanAttackPanel(main, GameState.PLAYER_2_ATTACK);
        main.getMainPanel().add(player2AttackPanel,
                player2AttackPanel.getPanelState().toString());

        main.changeGameState(shipPlacementPanel.getPanelState());
    }

    @Override
    public void nextPlacement() {
        if (player2.shipsSet()) {
            main.changeGameState(GameState.PLAYER_1_ATTACK);
            return;
        }

        ShipPlacementPanel shipPlacementPanel = new ShipPlacementPanel(main);
        main.getMainPanel().add(shipPlacementPanel,
                shipPlacementPanel.getPanelState().toString());
    }
}
