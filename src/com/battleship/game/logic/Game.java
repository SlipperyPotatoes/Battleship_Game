package com.battleship.game.logic;

import static com.battleship.game.utils.AssetUtils.playerDataToSaveString;
import static com.battleship.game.utils.AssetUtils.saveStringToPlayerData;

import com.battleship.game.Main;
import com.battleship.game.botfiles.Ship;
import com.battleship.game.enums.GameState;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.*;


/**
 * An abstract class the stores some of the data and methods associated with managing a 
 * Battleship game between 2 unspecified players.
 */
public abstract class Game {

    public static final int SIZE_X = 10;
    public static final int SIZE_Y = 10;
    public static final int[] SHIP_SIZES = {5, 4, 3, 3, 2};
    public static final String[] SHIP_NAMES = {"Aircraft Carrier", "Battleship",
        "Submarine", "Cruiser", "Destroyer"};

    private boolean canDoAction; // Indicates whether the game can take user input

    PlayerData player1;
    PlayerData player2;

    Main main;

    /**
     * Creates a new game object with empty playerData objects as a game hasn't been started yet.
     * 
     * @param main A reference to the main class which can be used to call methods for things 
     *        such as switching panels
     */
    public Game(Main main) {
        this.main = main;
        this.player1 = new PlayerData();
        this.player2 = new PlayerData();
        this.canDoAction = true;
    }

    public abstract void startNewGame();

    public abstract void nextPlacement();

    /**
     * Loads the contents of the file with the name (saveName).txt to a string and converts the
     * contents of that string into a playerData object
     * 
     * @param saveName Name of the text file with the game's save-data
     */
    public void startSavedGame(String saveName) {
        try {
            String fileData = new String(Files.readAllBytes(Path.of(saveName + ".txt")));

            player1 = saveStringToPlayerData(
                    fileData.substring(15, fileData.indexOf("player 2")));
            player2 = saveStringToPlayerData(
                    fileData.substring(fileData.indexOf("player 2") + 15));


        // there is no file saved so this gives an error
        } catch (Exception ignored) {
            JOptionPane.showMessageDialog(main.getFrame(),
                    "No existing save found",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Converts both playerData objects into strings to be saved. Then creates a file with the name
     * saveGame.txt if one doesn't already exist and loads the file into a fileWriter which adds 
     * the playerData strings and closes the file.
     * <p>
     * The data is saved into a text file in human-readable plain text.
     * This was done on purpose so that the status of a game can be understood without the
     * need for this program.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
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


    /**
     * Called when a player has finished their attack so that the game can switch screens to
     * the other player's attack screen.
     */
    public void nextAttack() {
        canDoAction = true;

        switch (main.getGameState()) {
            case PLAYER_1_ATTACK -> main.changeGameState(GameState.PLAYER_2_ATTACK);
            case PLAYER_2_ATTACK -> main.changeGameState(GameState.PLAYER_1_ATTACK);
            default -> throw new IllegalStateException(
                "Illegal state reached, nextAttack called on: "
                + main.getGameState().toString());
        }
    }

    /**
     * Stores a shipGrid in the first player if their ships aren't set and in the second player
     * if the first player's ships have already been set.
     *  
     * @param ships A 2d grid of ships
     */
    public void setNextPlayerShips(Ship[][] ships) {
        if (player1.shipsNotSet()) {
            player1.setShips(ships);
            return;
        }
        player2.setShips(ships);
    }

    public boolean canDoAction() {
        return canDoAction;
    }

    public void setCanDoAction(boolean canDoAction) {
        this.canDoAction = canDoAction;
    }
}
