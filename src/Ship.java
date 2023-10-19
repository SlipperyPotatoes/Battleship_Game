import java.util.*;
import java.awt.*;
import java.util.Random;
//true value means vertical


public class Ship {
    int length;
    boolean rotation;
    Point locationStart = new Point();
    int hp;
    String name;

    Ship(int length, String name) {
        this.length = length;
        this.hp = length;
        this.name = name;
        this.rotation = rotateShips();
    }

    private boolean rotateShips() {
        Random random = new Random();
        boolean rotate = random.nextBoolean();

        if (rotate) {
            if (BotPlacing.vertical == 3) {
                return false;
            }
            BotPlacing.vertical++;
            return true;    

        } else {
            if (BotPlacing.horizontal == 3) {
                return true;
            }
            BotPlacing.horizontal++;
            return rotate;
            
        }    
    }
}
