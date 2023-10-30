import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

public class gamePanel extends JPanel  implements ActionListener  , KeyListener{

    private class Tile {
        int x ;
        int y ;
        Tile (int x , int y){
            this.x = x ;
            this.y = y ;
        }
    }


    int width;
    int height;
    int TileSize = 25 ;

    Tile SnakeHead ;
    ArrayList <Tile> Snakebody ;
    Tile Apple ;
    Random random ;

    int velocityX ;
    int velocityY ;

    Timer gameloop ;
    int delay;

    boolean gameover = false ;


    gamePanel (int width , int height) {
        this.width = width ;
        this.height = height ;
        setPreferredSize( new Dimension(this.width , this.height));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true) ;


        SnakeHead = new Tile(5, 5);
        Snakebody = new ArrayList<Tile>();
        Apple = new Tile(10, 10);
        random = new Random();
        newApple();

        

        velocityX =   1 ;
        velocityY =   0 ;
        delay = 180 ;

        

        gameloop = new Timer(delay, this);
        gameloop.start();


        delay -= (Snakebody.size() *  2.5) ;
       

    }
    
    public void paintComponent(Graphics g ){
        super.paintComponent(g);
        draw(g);
    }

    public void draw (Graphics g){

        //grid
        for (int i = 0; i < width/TileSize; i++) {
            g.drawLine(i*TileSize, 0, i * TileSize, height);
            g.drawLine( 0 , i * TileSize, width  , i*TileSize);
        }

        //snake head
        g.setColor(Color.yellow);
       // g.fillRect(SnakeHead.x * TileSize, SnakeHead.y * TileSize,TileSize , TileSize);
        g.fill3DRect(SnakeHead.x * TileSize, SnakeHead.y * TileSize,TileSize , TileSize , true);
        g.setColor(Color.RED);
        g.setFont (new Font( "Arial" , Font.BOLD , 25 ));
        g.drawString(":" ,SnakeHead.x * TileSize + 15 , SnakeHead.y * TileSize  + 15) ;

        //apple
        g.setColor(Color.red);
        g.fillOval(Apple.x * TileSize, Apple.y *TileSize ,TileSize , TileSize);
        

        //body
        g.setColor(Color.green);
        for (int i = 0; i < Snakebody.size(); i++) {
            g.setColor(Color.blue);
            Tile snakePart = Snakebody.get(i);
           
            g.fill3DRect(snakePart.x  * TileSize , snakePart.y * TileSize , TileSize , TileSize , true);
            g.setColor(Color.orange);
           g.setFont (new Font( "Arial" , Font.PLAIN , 10 ));
            g.drawString("@" ,snakePart.x *TileSize + 15 , snakePart.y *TileSize + 15) ;
            //g.fillRect(snakePart.x  * TileSize , snakePart.y * TileSize , TileSize , TileSize);

        }

        // score 
        g.setFont (new Font( "Arial" , Font.PLAIN , 16 ));
        
        if(gameover == true) {

            g.setColor(Color.red);
            g.setFont (new Font( "Arial" , Font.BOLD , 60 ));
            g.drawString( "Game Over  : " + String.valueOf(Snakebody.size() * 10) , 100 , 300 ) ;
            g.setFont (new Font( "Arial" , Font.ITALIC , 20 ));
            // g.drawString(" Enter '0' to restart the game  ", 180, 350);
            //  g.drawString( "Game Over  :" + "4"+ String.valueOf(Snakebody.size()) , 100 , 300 ) ;

        }

        else {
            g.setColor(Color.green);
            g.drawString( " Score :- " + String.valueOf(Snakebody.size() *10) , TileSize - 16 , TileSize) ;

        }
    }


    // random Apple 
    public void newApple(){
        Apple.x = random.nextInt((int) (width / TileSize)) ;
        Apple.y = random.nextInt((int) (height / TileSize)) ;

    }

    public void move (){

        if(collision(SnakeHead, Apple)){
            Snakebody.add(new Tile(Apple.x, Apple.y));
            newApple();
        }

        //body move 

        for (int i = Snakebody.size() -1; i >= 0; i--) {
            Tile snakePart = Snakebody.get(i);
            if(i == 0){
                snakePart.x = SnakeHead.x ;
                snakePart.y = SnakeHead.y ;
            }
            else {
                Tile prevPart = Snakebody.get(i-1) ;
                snakePart.x = prevPart.x ;
                snakePart.y = prevPart.y ;
            }   
        }



        SnakeHead.x  = SnakeHead.x +  velocityX  ;
        SnakeHead.y = SnakeHead.y + velocityY  ;


        
        //collision with body ;

        for (int i = 0; i < Snakebody.size(); i++) {
            Tile snakePart = Snakebody.get(i);
            if (collision(SnakeHead, snakePart)) {
                gameover = true ;
            }
            
        }

            // wall collied 
        if (SnakeHead.x *TileSize < 0  || SnakeHead.x  * TileSize > width || SnakeHead.y  * TileSize >height  || SnakeHead.y *TileSize < 0 ){
            gameover = true ;
        }
    }


    public Boolean collision (Tile tile1 , Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y ;
    }


  
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        move() ;
        repaint();

        if (gameover){
            gameloop.stop();
            
        }

 
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP   && velocityY != 1){
            velocityX = 0 ;
            velocityY = -1 ;

        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN   && velocityY != -1){
            velocityX = 0 ;
            velocityY = 1 ;

        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT   && velocityX != 1){
            velocityX =  -1 ;
            velocityY = 0  ;

        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT   && velocityX != -1) {
            velocityX = 1 ;
            velocityY = 0 ;

        }   
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    
  
   


    
}
