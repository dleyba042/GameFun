package main;

import entity.Bullet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

    Stack<Bullet> bullets = new Stack<>();

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

        if(keyHandler.spacePressed)
        {
            bullets.push(new Bullet(playerX,playerY));
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.fillRect(playerX,playerY,tileSize,tileSize);

        if(!bullets.isEmpty())
        {
            for(Bullet bullet : bullets)
            {
                bullet.setY(bullet.getY() - bullet.getBulletSpeed());
                g2.setColor(Color.BLUE);
                g2.fillOval(bullet.getX(), bullet.getY(), bullet.getBulletSize(), bullet.getBulletSize());
            }

            while (!bullets.isEmpty() && bullets.peek().getY() < 0)
            {
                bullets.pop();
            }

        }



        g2.dispose();

    }
}
