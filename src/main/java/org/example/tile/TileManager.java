package org.example.tile;

import org.example.App;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    App app;
    Tiles[] tiles;
    int mapTileNum[][];


    public TileManager(App app) {

        this.app = app;
        tiles = new Tiles[10];
        mapTileNum = new int[app.maxScreenCol][app.maxScreenRow];
        getTileImage();
        loadMap();
    }
    public void getTileImage() {
        try {
            tiles[0] = new Tiles();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            tiles[1] = new Tiles();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tiles[2] = new Tiles();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road_down.png"));
            tiles[3] = new Tiles();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road_up.png"));


        }catch (IOException e){
            e.printStackTrace();
        }


    }


    public void loadMap(){
        try{
            InputStream is = getClass().getResourceAsStream("/maps/map01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0 ;
            int row = 0;
            while(col < app.maxScreenCol && row < app.maxScreenRow){
                String line = br.readLine();

                while(col < app.maxScreenCol){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[row][col] = num;
                    col++;
                }
                if (col == app.maxScreenCol){
                    col = 0;
                    row++;
                }
                br.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void draw(Graphics2D g2){
       int col = 0;
       int row = 0;
       int y = 0;
       int x = 0;
       while(col < app.maxScreenCol && row < app.maxScreenRow){
           int tileNum = mapTileNum[row][col];
           g2.drawImage(tiles[tileNum].image, x, y, app.titleSize, app.titleSize,null);
           col++;
           x+=app.titleSize;

           if(col == app.maxScreenCol){

               col = 0;
               x= 0;
               row++;
               y +=app.titleSize;

           }

       }
    }

}
