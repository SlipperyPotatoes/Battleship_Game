package com.battleship.game.botfiles;

import java.util.Scanner;

public class BotSaveData {
    private final boolean firstAttack;
    private final boolean firstShipAttack;
    private final boolean attackingShip;

    public BotSaveData(boolean firstAttack, boolean firstShipAttack, boolean attackingShip) {
        this.firstAttack = firstAttack;
        this.firstShipAttack = firstShipAttack;
        this.attackingShip = attackingShip;
    }

    public BotSaveData(String str) {
        Scanner reader = new Scanner(str);
        this.firstAttack = Boolean.parseBoolean(reader.nextLine().substring(14));
        this.firstShipAttack = Boolean.parseBoolean(reader.nextLine().substring(20));
        this.attackingShip = Boolean.parseBoolean(reader.nextLine().substring(17));
        reader.close();
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

    @Override
    public String toString() {
        return "First attack: " + firstAttack +
                "\n\tFirst ship attack: " + firstShipAttack +
                "\n\tAttacking ship: " + attackingShip;
    }
}
