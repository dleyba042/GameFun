package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.util.Stack;

/**
 * Class to make managing updating and drawing a bunch of bullet objects easier
 */
public class BulletController
{
    public static final double RIGHT_BULLET_X_DIVISOR = 1.3;
    private Stack<Bullet> bullets;
    private GamePanel gamePanel;
    private KeyHandler keyHandler;

    private static final int BULLET_DELAY = 10;
    private int timePassed = 0;

    public BulletController(GamePanel panel, KeyHandler handler)
    {
        this.gamePanel = panel;
        this.keyHandler = handler;
        this.bullets = new Stack<>();
    }

    public void update(String direction, int playerX, int playerY)
    {
        if(keyHandler.mPressed && timePassed > BULLET_DELAY)
        {

            int directionAdjustX = (direction.equals("left")) ? playerX:
                    playerX + (int)(gamePanel.tileSize/ RIGHT_BULLET_X_DIVISOR);
            bullets.push(new Bullet(directionAdjustX, playerY, direction));
            timePassed = 0;
        }
        else
        {
            timePassed++;
        }

        if(!bullets.isEmpty())
        {
            for(Bullet bullet : bullets)
            {
                bullet.update();
            }                                                   //Check left and right
            while (!bullets.isEmpty() && (bullets.peek().getX() > gamePanel.screenWidth || bullets.peek().getX() < 0 ))
            {
                bullets.pop();
            }
        }
    }

    public void draw(Graphics2D g2)
    {
        if(!bullets.isEmpty())
        {
            for(Bullet bullet : bullets)
            {
                bullet.draw(g2);
            }
        }
    }


}
