package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.IOException;

public class Player extends Entity {
    App app;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(App app, KeyHandler keyH) {
        this.app = app;
        this.keyH = keyH;
        screenX = app.screenWidth/2 - (app.tileSize/2);
        screenY = app.screenHeight/2 - (app.tileSize/2);
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = app.tileSize * 24;
        worldY = app.tileSize * 18;
        speed = 4;
        direction = "left";

    }
    public void getPlayerImage(){
        try{
         left1 = ImageIO.read(getClass().getResourceAsStream("/zdj/nga_left.png"));
         right1 = ImageIO.read(getClass().getResourceAsStream("/zdj/nga_right.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }



    public void update(){
        if(keyH.upPressed == true){
            direction = "up";
            worldY -= speed;
        }
        else if(keyH.downPressed == true){
            direction = "down";
            worldY += speed;
        }
        else if(keyH.leftPressed == true){
            direction = "left";
            worldX -= speed;
        }
        else if(keyH.rightPressed == true){
            direction = "right";
            worldX += speed;
        }
    }
    public void draw(Graphics2D g2){
        //g2.setColor(Color.BLACK);

       // g2.fillRect(x, y, app.titleSize, app.titleSize);

        BufferedImage image = null;
        switch(direction){
           case "left":
               image = left1;
               break;
          case "right":
              image = right1;
              break;
            case "down":
                image = right1;
                break;
            case "up":

                image = left1;
                break;
        }

        g2.drawImage(image, screenX, screenY, app.tileSize, app.tileSize, null);


    }

}
