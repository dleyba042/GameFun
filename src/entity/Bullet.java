package entity;


import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Bullet
{
    GamePanel gamePanel;
    KeyHandler keyHandler;

    private int x;
    private int y;

    private int bulletSize = 8;
    private int bulletSpeed = 12;

    private String direction;

    public Bullet(int x, int y,String direction)
    {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void draw(Graphics2D g2)
    {
        g2.setColor(Color.BLUE);
        g2.fillOval(x,y,bulletSize, bulletSize);
    }

    public void update()
    {

        if (direction.equals("left"))
        {
            x-= bulletSpeed;
        }
        else
        {
            x+= bulletSpeed;
        }
    }

    public int getX()
    {
        return x;
    }

}
