package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Player extends Entity
{
    public static final int ONE_SECOND_NANO = 200000000;
    GamePanel gamePanel;
    KeyHandler keyHandler;
    String direction;
    boolean jumping;
    boolean falling;
    final int jumpSpeed = 8;
    long jumpStart;


    public Player(GamePanel gamePanel, KeyHandler keyHandler)
    {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.x = 0;
        this.speed = 4;
        this.y = gamePanel.screenHeight - gamePanel.tileSize;
        getPlayerImage();
        direction = "left";
    }

    public void getPlayerImage()
    {

        try
        {
            right1 = ImageIO.read(getClass().getResourceAsStream("../resource/player/right1..png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("../resource/player/right2..png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("../resource/player/left1..png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("../resource/player/left2..png"));
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void update()
    {

        if(keyHandler.spacePressed && !jumping && !falling)
        {
            jumping = true;
            jumpStart = System.nanoTime();
        }

        if(jumping)
        {
            if(System.nanoTime() - jumpStart < ONE_SECOND_NANO)
            {
                y -= jumpSpeed;
            }
            else
            {
                jumpStart = 0;
                jumping = false;
                falling = true;
            }

        }

        if(falling)
        {
            if(y + speed > gamePanel.screenHeight - gamePanel.tileSize)
            {
                y = gamePanel.screenHeight - gamePanel.tileSize;
                falling = false;
            }
            else
            {
                y += jumpSpeed;
            }
        }


        if(keyHandler.rightPressed)
        {
            x+= speed;
            direction = "right";
            spriteCounter++;
        }else if(keyHandler.leftPressed)
        {
            x-=speed;
            direction = "left";
            spriteCounter++;
        }


        if(spriteCounter > 10 && (keyHandler.rightPressed || keyHandler.leftPressed))
        {
            spriteNum = (spriteNum == 1) ? 2  : 1;
            spriteCounter = 0;
        }

        getPlayerImage();
    }

    public void draw(Graphics2D g)
    {
        BufferedImage image = switch (direction) {
            case "left" -> (spriteNum == 1) ? left1 : left2;
            case "right" -> (spriteNum == 1)? right1 : right2;
            default -> null;
        };

        g.drawImage(image,x,y, gamePanel.tileSize, gamePanel.tileSize,null);
    }

    public String getDirection()
    {
        return direction;
    }
}
