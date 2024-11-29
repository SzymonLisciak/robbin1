package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.IOException;

public class Player extends Entity implements Transform{
    App app;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(App app, KeyHandler keyH) {
        super(app);
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
         this.left1 = ImageIO.read(getClass().getResourceAsStream("/zdj/nga_left.png"));
         this.right1 = ImageIO.read(getClass().getResourceAsStream("/zdj/nga_right.png"));
         this.leftCar = ImageIO.read(getClass().getResourceAsStream("/zdj/nga_leftcar.png"));
         this.rightCar = ImageIO.read(getClass().getResourceAsStream("/zdj/nga_rightcar.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }



    public void update(){
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
        if(keyH.upPressed){
            direction = "up";

        }
        else if(keyH.downPressed){
            direction = "down";

        }
        else if(keyH.leftPressed){
            direction = "left";

        }
        else if(keyH.rightPressed){
            direction = "right";

        }
        slownessOn = false;
        collisionOn = false;
        fastOn = false;
        app.cChecker.checkTile(this);
        app.effectChecker.checkTile(this);

        if (!collisionOn) {
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
            if (slownessOn) {
               speed = 2;
            }else if(fastOn) {
                speed = 11;
            }else {
                speed = 7;
            }


        }
    }
    public void draw(Graphics2D g2){
        //g2.setColor(Color.BLACK);

       // g2.fillRect(x, y, app.titleSize, app.titleSize);

        BufferedImage image = null;
        switch(direction){
           case "left", "up":
               image = left1;
               break;
          case "right", "down":
              image = right1;
              break;
        }
        if (fastOn){
            EntityTransformation(g2);
            return;
        }

        g2.drawImage(image, screenX, screenY, app.tileSize, app.tileSize, null);


    }


    @Override
    public void EntityTransformation(Graphics2D g2) {
        BufferedImage image = null;
        switch(direction){
            case "left", "up":
                image = leftCar;
                break;
            case "right", "down":
                image = rightCar;
                break;
        }

        g2.drawImage(image, screenX, screenY, app.tileSize, app.tileSize, null);
    }
}
