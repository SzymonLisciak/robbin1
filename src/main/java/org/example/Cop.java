package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Cop extends Entity{
    App app;

    public Cop(App app) {
        solidArea = new Rectangle();
        solidArea.x =8;
        solidArea.y =16;
        solidArea.width =32;
        solidArea.height =32; worldX = app.tileSize * 24;
        worldX = app.tileSize * 24;
        worldY = app.tileSize * 18;
        speed = 7;
        direction = "up";
        getCopImage();
        setDeafultCopValues();
    }
    public void setDeafultCopValues() {

    }
    public void getCopImage(){
        try{
            left1 = ImageIO.read(getClass().getResourceAsStream("/zdj/cop_left.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/zdj/cop_right.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
