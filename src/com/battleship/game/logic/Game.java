package com.battleship.game.logic;

import com.battleship.game.Main;
import com.battleship.game.botfiles.Ship;
import com.battleship.game.enums.GameState;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.battleship.game.utils.AssetUtils.playerDataToSaveString;
import static com.battleship.game.utils.AssetUtils.saveStringToPlayerData;

public abstract class Game {
    public final static int SIZE_X = 10;
    public final static int SIZE_Y = 10;
    public final static int[] SHIP_SIZES = {5, 4, 3, 3, 2};
    public final static String[] SHIP_NAMES = {"Aircraft Carrier", "Battleship",
            "Submarine", "Cruiser", "Destroyer"};

    private boolean canDoAction;

    PlayerData player1;
    PlayerData player2;

    Main main;

    public Game(Main main) {
        this.main = main;
        this.player1 = new PlayerData();
        this.player2 = new PlayerData();
        this.canDoAction = true;
    }

    public abstract void startNewGame();

    public abstract void nextPlacement();

    public void startSavedGame(String saveName) {
        try {
            String fileData = new String(Files.readAllBytes(Path.of(saveName + ".txt")));

            player1 = saveStringToPlayerData(
                    fileData.substring(15, fileData.indexOf("player 2")));
            player2 = saveStringToPlayerData(
                    fileData.substring(fileData.indexOf("player 2") + 15));


        } catch (Exception ignored) {
            JOptionPane.showMessageDialog(main.getFrame(),
                    "No existing save found",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

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
        }

        main.endGame();
    }


    public void nextAttack() {
        canDoAction = true;

        switch (main.getGameState()) {
            case PLAYER_1_ATTACK -> main.changeGameState(GameState.PLAYER_2_ATTACK);
            case PLAYER_2_ATTACK -> main.changeGameState(GameState.PLAYER_1_ATTACK);
            default -> throw new IllegalStateException
                    ("Illegal state reached, nextAttack called on: " + main.getGameState().toString());
        }
    }

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
