package com.battleship.game.utils;

import javax.swing.*;
import java.net.URL;

public class assetsUtils {
    public static ImageIcon loadIcon(String path) {
        URL url = assetsUtils.class.getClassLoader()
                .getResource("com/battleship/game/assets/" + path);
        assert url != null;
        return new ImageIcon(url);
    }

    public static ImageIcon scaleImage(ImageIcon image, int w, int h) {
        return new ImageIcon(image.getImage().getScaledInstance(w, h, 8));
    }
}
