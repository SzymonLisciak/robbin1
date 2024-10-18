package org.example;

import org.example.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class App extends JPanel implements Runnable {

     final int originalTitleSize = 16;
    final int scale = 3;
    public final int titleSize = originalTitleSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = titleSize * maxScreenCol;
    public final int screenHeight = titleSize * maxScreenRow;
    KeyHandler keyH = new KeyHandler();
     Thread gameThread;

    int FPS = 60;
    TileManager tileM = new TileManager(this);

    Player player = new Player(this,keyH);

    public App(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);

    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    /*public void run() {
        double drawinterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawinterval;
     while(gameThread != null){





         update();
         repaint();

         try {
             double remainingTime = nextDrawTime - System.nanoTime();
             remainingTime = remainingTime /1000000;
             if(remainingTime < 0){
                 remainingTime = 0;

             }
             Thread.sleep((long) remainingTime);
             nextDrawTime += drawinterval;

         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
    }*/

    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int frames = 0;


        while(gameThread !=null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
                frames++;
            }
            if(timer >= 1000000000){
                System.out.println("FPS: " + frames);
                frames = 0;
                timer = 0;
            }

        }
    }

    public void update(){
    player.update();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);

        player.draw(g2);

        g2.dispose();
    }

}

