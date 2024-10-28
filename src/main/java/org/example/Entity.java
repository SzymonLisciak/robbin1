package org.example;

import java.awt.image.BufferedImage;

public abstract class Entity {

    public int worldX ,worldY;
    public int speed;
    public BufferedImage left1,right1;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

}
