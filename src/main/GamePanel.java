package main;

import entity.BulletController;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

/**
 * Our game screen
 */
public class GamePanel extends JPanel implements Runnable
{
    //Screen settings
    final int originalTileSize = 16; //default size of tiles -> will be scaled
    final int scale = 4;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //FPS
    final int FPS = 60;
    private TileManager tileManager = new TileManager(this);
    private KeyHandler keyHandler = new KeyHandler();

    //OUR GAME CLOCK
    Thread gameThread;
    Player player = new Player(this,keyHandler);

    private BulletController bulletController = new BulletController(this,keyHandler);

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
        bulletController.update(player.getDirection(), player.x, player.y);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2); //draw tiles before player
        player.draw(g2);
        bulletController.draw(g2);


        g2.dispose();

    }

}
