import java.awt.Frame;
import javax.swing.*;

public class SnakeApp {
    public static void main(String[] args) throws Exception {

       

        int width = 600 ;
        int height = 600;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        
        gamePanel panel = new gamePanel(width, height);
        frame.add(panel);
        frame.pack();
        panel.requestFocus();

        
    }
}
