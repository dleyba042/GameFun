package entity;

public class Bullet
{
    private int x;
    private int y;

    private int bulletSize = 16;

    private int bulletSpeed = 12;

    public Bullet(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getBulletSpeed()
    {
        return bulletSpeed;
    }

    public int getBulletSize() {
        return bulletSize;
    }
}
