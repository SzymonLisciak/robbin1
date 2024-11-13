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
        solidArea = new Rectangle();
        solidArea.x =8;
        solidArea.y =16;
        solidArea.width =32;
        solidArea.height =32;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = app.tileSize * 24;
        worldY = app.tileSize * 18;
        speed = 7;
        direction = "up";

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
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true){
        if(keyH.upPressed == true){
            direction = "up";

        }
        else if(keyH.downPressed == true){
            direction = "down";

        }
        else if(keyH.leftPressed == true){
            direction = "left";

        }
        else if(keyH.rightPressed == true){
            direction = "right";

        }
        collisionOn = false;
        app.cChecker.checkTile(this);

        if (collisionOn == false) {
            switch (direction) {
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
            }
            }
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
