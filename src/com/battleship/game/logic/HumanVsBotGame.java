package com.battleship.game.logic;

import static com.battleship.game.utils.AssetUtils.playerDataToSaveString;

import com.battleship.game.Main;
import com.battleship.game.botfiles.BotSaveData;
import com.battleship.game.enums.BotAlgorithm;
import com.battleship.game.enums.GameState;
import com.battleship.game.panels.BotAttackPanel;
import com.battleship.game.panels.HumanAttackPanel;
import com.battleship.game.panels.ShipPlacementPanel;
import com.battleship.game.utils.Vector;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.*;



/**
 * Class for storing and handling all the data associated with a Battleship game between a
 * human player and a bot player.
 * <p>
 * The human will always be player 1 and the bot always player 2.
 */
public class HumanVsBotGame extends Game {
    Bot bot;
    BotAlgorithm algorithm;
    BotAttackPanel botAttackPanel;

    /**
     * See superclass constructor. 
     * 
     * @param algorithm An enum that indicates which bot difficulty the user chose to play against
     */
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

    /**
     * Calls the superclass method which loads the playerData.
     * <p>
     * If there was an error the playerData would be null so exits this method as the load has
     * already failed and the user was already notified by the superclass method.
     * <p>
     * If there was no error loading the playerData then the file is loaded again in order to
     * load the extra botSaveData at the end of the file which is then used to create a new bot.
     * 
     * @param saveName Name of the text file with the save
     */
    @Override
    public void startSavedGame(String saveName) {
        super.startSavedGame(saveName);
        if (player1 == null || player2 == null) {
            return;
        }

        if (player1.shipsNotSet() || player2.shipsNotSet()) {
            return;
        }

        BotSaveData botSaveData;
        try {
            String fileData = new String(Files.readAllBytes(Path.of(saveName + ".txt")));
            String botDataStr = fileData.substring(fileData.indexOf("bot data:") + 11);
            botSaveData = new BotSaveData(botDataStr);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(main.getFrame(),
                    "No existing human vs bot save found",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        bot = new Bot(algorithm, player1, botSaveData);

        HumanAttackPanel humanAttackPanel =
            new HumanAttackPanel(main, GameState.PLAYER_1_ATTACK, player2);

        main.getMainPanel().add(humanAttackPanel,
                humanAttackPanel.getPanelState().toString());

        botAttackPanel = new BotAttackPanel(main, player1);
        main.getMainPanel().add(botAttackPanel,
                botAttackPanel.getPanelState().toString());

        main.changeGameState(GameState.PLAYER_1_ATTACK);
    }

    /**
     * Converts both playerData objects and the botSaveData object into strings to be saved.
     * Then creates a file with the name saveGame.txt if one doesn't already exist and loads
     * the file into a fileWriter which adds the strings and closes the file.
     * <p>
     * The data is saved into a text file in human-readable plain text.
     * This was done on purpose so that the status of a game can be understood without the
     * need for this program.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
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
        } catch (Exception ignored) {
            JOptionPane.showMessageDialog(main.getFrame(),
                    "Failed to save game",
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        main.endGame();
    }

    // Called after the user finishes placing their ships
    @Override
    public void nextPlacement() {
        bot = new Bot(algorithm, player1.getShipGrid());

        setNextPlayerShips(bot.generateShipPositions());

        HumanAttackPanel humanAttackPanel = 
            new HumanAttackPanel(main, GameState.PLAYER_1_ATTACK, player2);

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


        // if all the players ships are destroyed the bot wins
        if (player1.allShipsDead()) {
            main.finishGame("BOT");
            return;
        }

        // this delays the attack so that the player can see what the bot attacked.
        Timer timer = new Timer(1500, e -> nextAttack());
        timer.setRepeats(false);
        timer.start();
    }
}
