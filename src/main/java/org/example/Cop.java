package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cop extends Entity {
        public final int screenX;
        public final int screenY;

    public Cop(App app) {
        super(app);
        solidArea = new Rectangle();
        screenX = app.screenWidth/2 - (app.tileSize/2);
        screenY = app.screenHeight/2 - (app.tileSize/2);
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        worldX = app.tileSize * 24;
        worldY = app.tileSize * 18;
        direction = "left";
        getCopImage();
    }

    public void setDeafultCopValues() {

    }

    public void getCopImage() {
        try {
            this.left1 = ImageIO.read(getClass().getResourceAsStream("/zdj/cop_left.png"));
            this.right1 = ImageIO.read(getClass().getResourceAsStream("/zdj/cop_right.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawCop(Graphics2D g2) {
        //g2.setColor(Color.BLACK);

        // g2.fillRect(x, y, app.titleSize, app.titleSize);

        BufferedImage image = null;
        switch (direction) {
            case "left", "up":
                image = left1;
                break;
            case "right", "down":
                image = right1;
                break;
        }

        g2.drawImage(image, worldY, worldY, app.tileSize, app.tileSize, null);
    }

    public void updateCop() {
        boolean g = false;
        if (!g) {
                direction = "up";
        }
        collisionOn = false;
        app.cChecker.checkTile(this);
    }
}