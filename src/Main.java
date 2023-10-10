import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main {
    Mouse mouse;
    JFrame frame;
    JPanel panel;

    public void run() {
        frame = new JFrame("Test Frame");
        panel = new JPanel();
        mouse = new Mouse(frame, this);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 300, 300));

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.addMouseListener(mouse);
        frame.pack();
        frame.setVisible(true);
    }

    public void onMousePressed(MouseEvent e) {

    }

    public static void main(String[] args) {
        new Main().run();
    }

    class Mouse implements MouseListener {
        JFrame frame;
        Main main;

        public Mouse(JFrame frame, Main main) {
            this.frame = frame;
            this.main = main;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            Rectangle bounds = frame.getBounds();
            System.out.println("x: "+((float) e.getX())/bounds.width
                             +" y: "+((float) e.getY())/bounds.height);

            main.onMousePressed(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}

