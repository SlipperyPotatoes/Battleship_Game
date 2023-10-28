package com.battleship.game.botfiles;

import com.battleship.game.utils.ShipUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BotSaveData {
    private final boolean firstAttack;
    private final boolean firstShipAttack;
    private final boolean attackingShip;
    private final boolean attackDirectionFound;
    private final String currentDirection;
    private final boolean unevenSquares;
    private final Point currentAttack;
    private final Point firstHit;
    private final List<String> nextDirection;

    public BotSaveData(boolean firstAttack, boolean firstShipAttack, boolean attackingShip,
                       boolean attackDirectionFound, String currentDirection, boolean unevenSquares,
                       Point currentAttack, Point firstHit, List<String> nextDirection) {
        this.firstAttack = firstAttack;
        this.firstShipAttack = firstShipAttack;
        this.attackingShip = attackingShip;
        this.attackDirectionFound = attackDirectionFound;
        this.currentDirection = currentDirection;
        this.unevenSquares = unevenSquares;
        this.currentAttack = currentAttack;
        this.firstHit = firstHit;
        this.nextDirection = nextDirection;
    }

    public BotSaveData(String str) {
        Scanner reader = new Scanner(str);
        firstAttack = Boolean.parseBoolean(reader.nextLine().substring(14));
        firstShipAttack = Boolean.parseBoolean(reader.nextLine().substring(20));
        attackingShip = Boolean.parseBoolean(reader.nextLine().substring(17));
        attackDirectionFound = Boolean.parseBoolean(reader.nextLine().substring(25));
        currentDirection = reader.nextLine().substring(20);
        unevenSquares = Boolean.parseBoolean(reader.nextLine().substring(17));
        currentAttack = ShipUtils.strToPoint(reader.nextLine().substring(17));
        firstHit = ShipUtils.strToPoint(reader.nextLine().substring(12));
        String nextDirectionStr = reader.nextLine().substring(20);
        if (nextDirectionStr.equals("EMPTY")) {
            nextDirection = new ArrayList<>();
        } else {
            nextDirection = Arrays.asList(nextDirectionStr.split(", *"));
        }
        reader.close();
    }

    public int[][] getCurrentSquares() {
        if (unevenSquares) {
            return new int[][]{
                    {1, 3, 5, 7, 9},
                    {0, 2, 4, 6, 8},
                    {1, 3, 5, 7, 9},
                    {0, 2, 4, 6, 8},
                    {1, 3, 5, 7, 9},
                    {0, 2, 4, 6, 8},
                    {1, 3, 5, 7, 9},
                    {0, 2, 4, 6, 8},
                    {1, 3, 5, 7, 9},
                    {0, 2, 4, 6, 8},
            };

        } else {
            return new int[][]{
                    {0, 2, 4, 6, 8},
                    {1, 3, 5, 7, 9},
                    {0, 2, 4, 6, 8},
                    {1, 3, 5, 7, 9},
                    {0, 2, 4, 6, 8},
                    {1, 3, 5, 7, 9},
                    {0, 2, 4, 6, 8},
                    {1, 3, 5, 7, 9},
                    {0, 2, 4, 6, 8},
                    {1, 3, 5, 7, 9},
            };
        }
    }

    public boolean getFirstAttack() {
        return firstAttack;
    }

    public boolean getFirstShipAttack() {
        return firstShipAttack;
    }

    public boolean getAttackingShip() {
        return attackingShip;
    }

    public boolean getAttackDirectionFound() {
        return attackDirectionFound;
    }

    public String getCurrentDirection() {
        return currentDirection;
    }

    public Point getCurrentAttack() {
        return currentAttack;
    }

    public Point getFirstHit() {
        return firstHit;
    }

    public List<String> getNextDirection() {
        return nextDirection;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("First attack: ").append(firstAttack);
        builder.append("\n\t");
        builder.append("First ship attack: ").append(firstShipAttack);
        builder.append("\n\t");
        builder.append("Attacking ship: ").append(attackingShip);
        builder.append("\n\t");
        builder.append("Attack direction found: ").append(attackDirectionFound);
        builder.append("\n\t");
        builder.append("Current direction: ").append(currentDirection);
        builder.append("\n\t");
        builder.append("Uneven squares: ").append(unevenSquares);
        builder.append("\n\t");
        builder.append("Current Attack: ").append(ShipUtils.pointToStr(currentAttack));
        builder.append("\n\t");
        builder.append("First hit: ").append(ShipUtils.pointToStr(firstHit));
        builder.append("\n\t");
        builder.append("Next direction(s): ");

        if (nextDirection.isEmpty()) {
            builder.append("EMPTY");
        } else {
            for (String direction : nextDirection) {
                builder.append(direction).append(", ");
            }
            builder.delete(builder.length() - 2, builder.length());
        }

        return builder.toString();
    }
}
