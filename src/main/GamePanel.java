package main;

import javax.swing.*;
import java.awt.*;

/**
 * Our game screen
 */
public class GamePanel extends JPanel implements Runnable
{
    //Screen settings
    final int originalTileSize = 16; //default size of tiles -> will be scaled
    final int scale = 3;
    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    //FPS
    final int FPS = 60;
    private KeyHandler keyHandler = new KeyHandler();
    //OUR GAME CLOCK
    Thread gameThread;

    //Set player defaults
    int playerX = 100;
    int playerY = 100;
    int playerSpeed =4;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /*
    //THIS IS THE PRINCIPLE RUNTIME
    //one method
    @Override
    public void run()
    {
        //One second or 1billion nano seconds
        //math to set draw interval to happen once a second
        double drawInterval = 1000000000/fps;
        double nextDrawTime = System.nanoTime() + drawInterval;


        while (gameThread != null)
        {

            //UPDATE: info such as positions
            update();
            //DRAW: based on new info
            repaint(); //Calls paintComponent method

            try {
                //see how long left before next draw and then sleep
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime/=1000000; //convert to milliseconds
                if(remainingTime < 0)
                {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime); //takes milliseconds

                nextDrawTime+= drawInterval;

            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

     */

    @Override
    public void run()
    {
        //One second or 1billion nano seconds
        //math to set draw interval to happen once a second
        double drawInterval = 1000000000/ FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;


        while (gameThread != null)
        {
            currentTime = System.nanoTime();

            delta+= (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1)
            {
                update();
                repaint();
                delta--;
            }

        }
    }

    public void update()
    {
        if(keyHandler.upPressed)
        {
            playerY-= playerSpeed;
        } else if (keyHandler.downPressed)
        {
            playerY+= playerSpeed;
        }
        else if(keyHandler.rightPressed)
        {
            playerX+= playerSpeed;
        }else if(keyHandler.leftPressed)
        {
            playerX-=playerSpeed;
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.fillRect(playerX,playerY,tileSize,tileSize);
        g2.dispose();

    }
}
