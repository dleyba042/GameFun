package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;


public class TileManager
{
    GamePanel gamePanel;
    Tile[] tiles;
    int[][] mapTileNum;

    public TileManager(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        tiles = new Tile[10];
        mapTileNum = new int[gamePanel.maxScreenRow][gamePanel.maxScreenCol];
        getTileImage();
        loadMap();
    }

    public void loadMap()
    {
        try
        {
            InputStream inputStream = getClass().getResourceAsStream("/resource/maps/firstMap.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow)
            {
                String line = reader.readLine();

                while (col < gamePanel.maxScreenCol)
                {
                    String[] numberArr = line.split(" ");
                    mapTileNum[row][col] = Integer.parseInt(numberArr[col]);
                    col++;
                }
                if(col == gamePanel.maxScreenCol)
                {
                    col = 0;
                    row++;
                }
            }
            reader.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void getTileImage()
    {
        try
        {
            //catci
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("../resource/tiles/cacti..png"));
            //ground
            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("../resource/tiles/ground..png"));
            //night
            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("../resource/tiles/night..png"));
            //sky
            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("../resource/tiles/sky..png"));
            //trans
            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("../resource/tiles/trans..png"));

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2)
    {
            int col = 0;
            int row = 0;
            int x = 0;
            int y = 0;

            while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow)
            {
                g2.drawImage(tiles[mapTileNum[row][col]].image,x,y,gamePanel.tileSize, gamePanel.tileSize,null);
                col++;

                x+= gamePanel.tileSize;

                if(col == gamePanel.maxScreenCol)
                {
                        col = 0;
                        x = 0;
                        row++;
                        y += gamePanel.tileSize;
                }
            }
    }

}
