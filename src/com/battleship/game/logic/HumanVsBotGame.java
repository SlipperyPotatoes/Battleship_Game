package com.battleship.game.logic;

import com.battleship.game.Main;
import com.battleship.game.botfiles.BotSaveData;
import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.enums.GameState;
import com.battleship.game.panels.BotAttackPanel;
import com.battleship.game.panels.HumanAttackPanel;
import com.battleship.game.panels.ShipPlacementPanel;
import com.battleship.game.utils.Vector;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.battleship.game.utils.AssetUtils.playerDataToSaveString;

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

        BotSaveData botSaveData;
        try {
            String fileData = new String(Files.readAllBytes(Path.of(saveName + ".txt")));
            String botDataStr = fileData.substring(fileData.indexOf("bot data:") + 11);
            botSaveData = new BotSaveData(botDataStr);

        } catch (Exception e) {
            throw new RuntimeException("Could not load botSaveData");
        }

        bot = new Bot(algorithm, player1, botSaveData);

        HumanAttackPanel humanAttackPanel = new HumanAttackPanel(main, GameState.PLAYER_1_ATTACK, player2);
        main.getMainPanel().add(humanAttackPanel,
                humanAttackPanel.getPanelState().toString());

        botAttackPanel = new BotAttackPanel(main, player1);
        main.getMainPanel().add(botAttackPanel,
                botAttackPanel.getPanelState().toString());

        main.changeGameState(GameState.PLAYER_1_ATTACK);
    }

    @Override
    public void saveGame() {
        String p1DataString = playerDataToSaveString(player1, "player 1");
        String p2DataString = playerDataToSaveString(player2, "player 2");

        File saveFile = new File("saveGame.txt");
        try {
            saveFile.createNewFile();
            FileWriter fileWriter = new FileWriter(saveFile);
            fileWriter.append(p1DataString);
            fileWriter.append("\n");
            fileWriter.append(p2DataString);
            fileWriter.append("\n");
            fileWriter.append("bot data:\n\t");
            fileWriter.append(bot.getBotSaveData().toString());
            fileWriter.append("\n");
            fileWriter.close();
        } catch (Exception ignored) {}

        main.endGame();
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
