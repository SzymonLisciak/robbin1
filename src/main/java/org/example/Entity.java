package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    App app;
    public int worldX ,worldY;
    public int speed;
    public BufferedImage left1,right1;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean slownessOn = false;
    public boolean fastOn = false;

    public Entity(App app) {
        this.app = app;
    }

}
