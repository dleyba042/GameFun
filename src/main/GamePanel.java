package main;


import entity.Bullet;
import entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * Our game screen
 */
public class GamePanel extends JPanel implements Runnable
{
    public static final double RIGHT_BULLET_X_DIVISOR = 1.3;
    //Screen settings
    final int originalTileSize = 16; //default size of tiles -> will be scaled
    final int scale = 4;
    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //FPS
    final int FPS = 60;
    private KeyHandler keyHandler = new KeyHandler();
    //OUR GAME CLOCK
    Thread gameThread;
    Player player = new Player(this,keyHandler);

    Stack<Bullet> bullets = new Stack<>();

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(SystemColor.CYAN);
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
        player.update();
        //Deal with our bullets
        if(keyHandler.mPressed)
        {
            int directionAdjustX = (player.getDirection().equals("left")) ? player.x :
                    player.x + (int)(tileSize/ RIGHT_BULLET_X_DIVISOR);
            bullets.push(new Bullet(this,keyHandler,directionAdjustX, player.y, player.getDirection()));
        }
        if(!bullets.isEmpty())
        {
            for(Bullet bullet : bullets)
            {
                bullet.update();
            }
            while (!bullets.isEmpty() && bullets.peek().getX() > screenWidth)
            {
                bullets.pop();
            }
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        player.draw(g2);

        if(!bullets.isEmpty())
        {
            for(Bullet bullet : bullets)
            {
                bullet.draw(g2);
            }
        }

        g2.dispose();

    }

}
