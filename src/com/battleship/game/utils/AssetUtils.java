package com.battleship.game.utils;

import com.battleship.game.botfiles.Ship;
import com.battleship.game.logic.Game;
import com.battleship.game.logic.PlayerData;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

/**
 * TODO ADD COMMENT.
 */
public class AssetUtils {

    /**
    * TODO ADD COMMENT.
    */
    public static ImageIcon loadIcon(String fileName) {
        URL url = AssetUtils.class.getClassLoader()
                .getResource("com/battleship/game/assets/" + fileName);
        assert url != null;
        return new ImageIcon(url);
    }

    public static ImageIcon scaleImage(ImageIcon image, int w, int h) {
        return new ImageIcon(image.getImage().getScaledInstance(w, h, 8));
    }

    /**
    * TODO ADD COMMENT.
    */
    public static String playerDataToSaveString(PlayerData data, String playerName) {
        Ship[][] shipGrid = data.getShipGrid();

        boolean[][] placesAttacked = data.getPlacesBeenAttacked();
        ArrayList<Ship> shipArray = data.getShipArray();
        shipArray.add(0, null);
        int[][] intShips = new int[Game.SIZE_Y][Game.SIZE_X];

        for (int y = 0; y < Game.SIZE_Y; y++) {
            for (int x = 0; x < Game.SIZE_X; x++) {
                Ship currentShip = shipGrid[y][x];
                if (currentShip == null) {
                    intShips[y][x] = 0;
                    continue;
                }

                for (int i = 0; i < shipArray.size(); i++) {
                    Ship arrayShip = shipArray.get(i);
                    if (arrayShip == null) {
                        continue;
                    }

                    if (currentShip.getName().equals(arrayShip.getName())) {
                        intShips[y][x] = i;
                        break;
                    }
                }
            }
        }

        StringBuilder builder = new StringBuilder(playerName + " data:");
        builder.append("\n\t");
        builder.append("places been attacked:");
        builder.append("\n\t");
        for (int y = 0; y < Game.SIZE_Y; y++) {
            for (int x = 0; x < Game.SIZE_X; x++) {
                builder.append(placesAttacked[y][x] ? 1 : 0);
            }
            builder.append("\n\t");
        }
        builder.append("ship data:");
        builder.append("\n\t");
        for (int i = 0; i < shipArray.size(); i++) {
            Ship ship = shipArray.get(i);
            builder.append(i).append(" - ");
            if (ship == null) {
                builder.append("Empty");
            } else {
                builder.append(ship);
            }
            builder.append("\n\t");
        }
        builder.append("ship grid:");
        builder.append("\n\t");
        for (int y = 0; y < Game.SIZE_Y; y++) {
            for (int x = 0; x < Game.SIZE_X; x++) {
                builder.append(intShips[y][x]);
            }
            builder.append("\n\t");
        }

        return builder.toString();
    }

    /**
    * TODO ADD COMMENT.
    */
    public static PlayerData saveStringToPlayerData(String saveString) {
        // TODO DONT KNOW WHEN TO CLOSE THIS?!?
        Scanner reader = new Scanner(saveString);
        reader.nextLine();

        boolean[][] placesAttacked = new boolean[Game.SIZE_Y][Game.SIZE_X];
        for (int y = 0; y < Game.SIZE_Y; y++) {
            String data = reader.nextLine().substring(1);
            for (int x = 0; x < Game.SIZE_X; x++) {
                placesAttacked[y][x] = Character.getNumericValue(data.charAt(x)) == 1;
            }
        }
        reader.nextLine();
        reader.nextLine();

        ArrayList<Ship> shipArray = new ArrayList<>();
        shipArray.add(null);
        for (int i = 0; i < Game.SHIP_SIZES.length; i++) {
            String shipStr = reader.nextLine().substring(5);
            shipArray.add(new Ship(shipStr));
        }

        reader.nextLine();

        Ship[][] shipGrid = new Ship[Game.SIZE_Y][Game.SIZE_X];
        for (int y = 0; y < Game.SIZE_Y; y++) {
            String data = reader.nextLine().substring(1);
            for (int x = 0; x < Game.SIZE_X; x++) {
                shipGrid[y][x] = shipArray.get(Character.getNumericValue(data.charAt(x)));
            }
        }

        return new PlayerData(shipGrid, placesAttacked);
    }
}
