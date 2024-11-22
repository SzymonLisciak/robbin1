package org.example;

import org.example.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class App extends JPanel implements Runnable {

     final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    //USTAWIENIA MAPY
    public final int maxWorldCol = 60;
    public final int maxWorldRow = 66;
    public final int WorldWidth = tileSize * maxWorldCol;
    public final int WorldHeight = tileSize * maxWorldRow;


    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    EffectChecker effectChecker = new EffectChecker(this);
    CollisionChecker cChecker = new CollisionChecker(this);
    int FPS = 60;
    TileManager tileM = new TileManager(this);

    public Player player = new Player(this,keyH);
    public Cop cop1 = new Cop(this);

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
    cop1.updateCop();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        player.draw(g2);
        cop1.drawCop(g2);

        g2.dispose();
    }

}

