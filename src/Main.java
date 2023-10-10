import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.awt.Color;



public class Main{

     // JFrame 
     static JFrame mainFrame; 
  
     static JButton startGame;

     static JButton exitGame;

    static JButton b2;

     // Label to display text
     static JLabel l;

     void run() {

         GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
         GraphicsDevice device = graphics.getDefaultScreenDevice();

         mainFrame = new JFrame("panel");

         // Creating a label to display text
         l = new JLabel("panel label");

         // Creating a new buttons
         startGame = new JButton("Start Game");
         exitGame = new JButton("Exit Game");

         startGame.setPreferredSize(new Dimension(200, 100));
         exitGame.setPreferredSize(new Dimension(200, 100));

         // Creating a panel to add buttons
         JPanel mainPanel = new JPanel();

         // Adding buttons and textfield to panel
         // using add() method
         mainPanel.add(startGame);
         mainPanel.add(exitGame);
         mainPanel.add(l);


         // setbackground of panel
         mainPanel.setBackground(Color.red);

         // Adding panel to frame
         mainFrame.add(mainPanel);

         // Setting the size of frame

         mainFrame.setVisible(true);

         mainFrame.setResizable(false);

         device.setFullScreenWindow(mainFrame);

     }

     public static void main(String[] args) {
         new Main().run();

         startGame.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 mainFrame.setVisible(false);
                 new Gamestart().run();

                 
             }
         });

         exitGame.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 System.exit(0);
             }
         });

     }

}



 class Gamestart extends JPanel {

    // JFrame
    static JFrame mainFrame;

    static JPanel mainPanel;

    static JPanel mainPanel2;

    static JButton startGame;

    static JButton exitGame;

    static JButton b2;

    // Label to display text
    static JLabel l;

    static JButton[][] map = new JButton[10][10];

    void run(){

        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();

        mainFrame = new JFrame("panel");

         // Creating a label to display text
        l = new JLabel("panel label");

         // Creating a new buttons
         

         // Creating a panel to add buttons
        mainPanel = new JPanel();
        mainPanel2 = new JPanel();

        

         // Adding buttons and textfield to panel
         // using add() method
         
        mainPanel.add(l);
        

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

        createMap();


    }

    public void createMap() {

        for (int i = 0; i < 10; i++) {
            for (int p = 0; p < 10; p++) {

                String s=Integer.toString(i+p);

                map[i][p] = new JButton(s);

                //ap[i][p].setPreferredSize(new Dimension(45, 45));
                //map[i][p].setLocation(i*50, p*50);

                mainPanel2.add(map[i][p]);
                

            }
        }
    }

    
 }


