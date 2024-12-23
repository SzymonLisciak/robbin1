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
    public Tiles[] tiles;
    public int[][] mapTileNum;


    public TileManager(App app) {

        this.app = app;
        tiles = new Tiles[12];
        mapTileNum = new int[app.maxWorldRow][app.maxWorldCol];
        getTileImage();
        loadMap();
    }
    public void getTileImage() {
        try {
            tiles[0] = new Tiles();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass1.png"));
            tiles[1] = new Tiles();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tiles[1].collision = true;
            tiles[2] = new Tiles();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road_down.png"));
            tiles[2].fast = true;
            tiles[3] = new Tiles();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road_up.png"));
            tiles[3].fast = true;
            tiles[4] = new Tiles();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tiles[4].collision = true;
            tiles[5] = new Tiles();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rock.png"));

            tiles[5].slowness = true;
            tiles[6] = new Tiles();
            tiles[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/glass.png"));
            tiles[6].collision = true;
            tiles[7] = new Tiles();
            tiles[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/door_ld.png"));
            tiles[7].collision = true;
            tiles[8] = new Tiles();
            tiles[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/door_rd.png"));
            tiles[8].collision = true;
            tiles[9] = new Tiles();
            tiles[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/door_lu.png"));
            tiles[9].collision = true;
            tiles[10] = new Tiles();
            tiles[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/door_ru.png"));
            tiles[10].collision = true;
            tiles[11] = new Tiles();
            tiles[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tiles[11].collision = true;


        }catch (IOException e){
            e.printStackTrace();
        }


    }


    public void loadMap() {
        try (InputStream is = getClass().getResourceAsStream("/maps/60x66.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            int col = 0;
            int row = 0;

            while (row < app.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break;

                String[] numbers = line.split(" ");
                for (col = 0; col < app.maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[row][col] = num;
                }
                row++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load map: " + e.getMessage(), e);
        }
    }
    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < app.maxWorldCol && worldRow < app.maxWorldRow){
            int tileNum = mapTileNum[worldRow][worldCol];
            int worldX = worldCol * app.tileSize;
            int worldY = worldRow * app.tileSize;
            int screenX = worldX - app.player.worldX + app.player.screenX;
            int screenY = worldY - app.player.worldY + app.player.screenY;
            if (worldX + app.tileSize > app.player.worldX - app.player.screenX && worldX - app.tileSize < app.player.worldX + app.player.screenX && worldY + app.tileSize > app.player.worldY - app.player.screenY && worldY - app.tileSize < app.player.worldY + app.player.screenY) {
                g2.drawImage(tiles[tileNum].image, screenX, screenY, app.tileSize, app.tileSize,null);
            }

            worldCol++;


            if(worldCol == app.maxWorldCol){
                worldCol = 0;
                worldRow++;


            }

        }
    }

}
