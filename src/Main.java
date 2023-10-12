import java.awt.*; 
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;



public class Main implements ActionListener {


    JFrame mainFrame;

        JPanel mainPanel;

        JPanel mainPanel2;

        JButton startGame;

        JButton exitGame;

        // Label to display text
        JLabel l;

        JButton[][] map = new JButton[10][10];



    public boolean[][] enemyHit = new boolean[10][10];

    public void copyArray(){

        int[][] enemyAttacks = new int[10][10];

        int[][] enemyShips = new int[10][10];

        

    }

    private boolean checkHit(int x,int y, int[][] enemyAttacks){

        if (enemyAttacks[x][y] == 1) {

            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

         for (int i = 0; i < 10; i++) {

            for (int p = 0; p < 10; p++) {

                //String s=Integer.toString(i+p);
                if (("button"+ i + p).equals(e.getActionCommand())) {

                    System.out.println("button"+ i + p);

                    enemyHit[i][p] = true;

                    map[i][p].setText("p");
                    map[i][p].setEnabled(false);

                }
            }
        }
    }


    public void createScreen(){

        // JFrame
        

        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();

        mainFrame = new JFrame("panel");

         // Creating a label to display text
        //l = new JLabel("panel label");

         // Creating a new buttons
         

         // Creating a panel to add buttons
        mainPanel = new JPanel();
        mainPanel2 = new JPanel();

    
         // Adding buttons and textfield to panel
         // using add() method
         
        //mainPanel.add(l);
        

        mainPanel2.setBackground(Color.blue);

        GridLayout layout = new GridLayout(10,10);


        layout.setHgap(5); 
        layout.setVgap(5); 

        

        mainPanel2.setPreferredSize(new Dimension(600, 600));
        mainPanel2.setLayout(layout);

         // setbackground of panel
        mainPanel.setBackground(Color.red);


        // Adding panel to frame

        //mainFrame.add(mainPanel);

        mainPanel.add(mainPanel2);
        mainFrame.add(mainPanel);
        

         // Setting the size of frame

        mainFrame.setVisible(true);

        mainFrame.setResizable(false);

        device.setFullScreenWindow(mainFrame);


        for (int i = 0; i < 10; i++) {

            for (int p = 0; p < 10; p++) {

                map[i][p] = new JButton("x");
                map[i][p].setActionCommand("button" + i + p);
                map[i][p].addActionListener(this); // Or some other ActionListener

                mainPanel2.add(map[i][p]);

                
                
            }
        }
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }

    public static void main(String[] args) {
        new Main().createScreen();

     }

   


 
}




