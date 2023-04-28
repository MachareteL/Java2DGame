package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //SCREEN SETTINGS
    final int originalTileSize = 16; //16zx16 TILE - default size of the player character, npc's and maps tiles
    final int scale = 3;

    final int tileSize = originalTileSize * scale; //48x48 tile size which is to compensate the width gap
    final int maxScreenCol = 16; //Maxwidth tiles in the screen0
    final int maxScreenRow = 12; //maxHeight tiles in the screen
    // the ratio is 4:3
    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    // 768 x 576
    int FPS = 60;
    //FPS


    KeyHandler keyH = new KeyHandler();
    Thread gameThread; // declare the gameThread var without initialize it - it makes the Thread not start yet


    //set PLayer default Position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 5;

    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        this.setFocusable(true);


    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start(); // automatically call void run method
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; //0.16666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){
            // here the game will be running this method
            // it must update the screen and the player position
            // update the information such as positions
            long currentTime = System.nanoTime();


            update();
            // draw the updated information
            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(){
        if (keyH.upPressed){
            playerY -= playerSpeed;
        }else if (keyH.downPressed){
            playerY += playerSpeed;
        }else if (keyH.leftPressed){
            playerX -= playerSpeed;
        }else if (keyH.rightPressed){
            playerX += playerSpeed;
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // SUPER makes the method affects the parent class - which is JPanel in this case
        // cause the gamePanel extends JPanel
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}