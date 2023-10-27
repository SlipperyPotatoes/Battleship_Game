package com.battleship.game.logic;

import com.battleship.game.Main;
import com.battleship.game.botfiles.Ship;
import com.battleship.game.enums.GameState;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static com.battleship.game.utils.assetsUtils.playerDataToSaveString;
import static com.battleship.game.utils.assetsUtils.saveStringToPlayerData;

public abstract class Game {
    public final static int SIZE_X = 10;
    public final static int SIZE_Y = 10;
    public final static int[] SHIP_SIZES = {5, 4, 3, 3, 2};
    public final static String[] SHIP_NAMES = {"Aircraft Carrier", "Battleship",
            "Submarine", "Cruiser", "Destroyer"};

    PlayerData player1;
    PlayerData player2;

    Main main;

    public Game(Main main) {
        this.main = main;
        this.player1 = new PlayerData();
        this.player2 = new PlayerData();
    }

    public abstract void startNewGame();

    public abstract void nextPlacement();

    public void startSavedGame(String saveName) {
        //TODO: Load game data using url, storing PlayerData for players 1 and 2 in their variables
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
        //TODO: Save game data to text file with .save extension,
        // the only thing that needs to be saved is the both player's PlayerData
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
        } catch (Exception ignored) {}

        main.endGame();
    }



    public void nextAttack() {
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
}
