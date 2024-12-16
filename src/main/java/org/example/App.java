package org.example;

import org.example.tile.TileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;

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
    public List<Cop> cops = Collections.synchronizedList(new ArrayList<>());
    private int pursuitLevel = 1;
    private Timer pursuitTimer;

    public enum GameState {
        PLAYING,
        GAME_OVER,
        GAME_WON
    }
    public GameState gameState = GameState.PLAYING;

    private JButton restartButton;

    public App() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);
        this.setLayout(null);

        Cop initialCop = new Cop(this);
        cops.add(initialCop);

        restartButton = new JButton("Restart");
        restartButton.setBounds(screenWidth / 2 - 50, screenHeight / 2 + 50, 100, 50);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        restartButton.setVisible(false);
        this.add(restartButton);

        startPursuitTimer(); // Zostaw tylko to wywołanie
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();

    }

    private void startPursuitTimer() {
        pursuitTimer = new Timer();
        pursuitTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameState == GameState.PLAYING) {
                    increasePursuitLevel();

                    // Check for win condition at level 15
                    if (pursuitLevel >= 15) {
                        gameState = GameState.GAME_WON;
                        // Stop the timer
                        pursuitTimer.cancel();
                    }
                }
            }
        }, 15000, 15000); // Every 15 seconds
    }

    private synchronized void increasePursuitLevel() {
        if (gameState == GameState.PLAYING) {
            pursuitLevel++;
            Cop newCop = new Cop(this);
            synchronized (cops) {
                cops.add(newCop);
            }
            System.out.println("Pursuit Level: " + pursuitLevel);
        }
    }

    private void restartGame() {
        gameState = GameState.PLAYING;

        player = new Player(this, keyH);

        cops.clear();
        cops.add(new Cop(this));

        pursuitLevel = 1;

        restartButton.setVisible(false);

        startPursuitTimer();

        this.requestFocusInWindow();
    }
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int frames = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                frames++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer = 0;
            }
        }
    }


    public void update() {
        if (gameState == GameState.PLAYING) {
            player.update();

            // Synchronizowana kopia listy
            List<Cop> copsCopy;
            synchronized (cops) {
                copsCopy = new ArrayList<>(cops);
            }

            for (Cop cop : copsCopy) {
                cop.updateCop();

                // kolizja z policjantem
                Rectangle playerBounds = new Rectangle(
                        player.worldX + player.solidArea.x,
                        player.worldY + player.solidArea.y,
                        player.solidArea.width,
                        player.solidArea.height
                );

                Rectangle copBounds = new Rectangle(
                        cop.worldX + cop.solidArea.x,
                        cop.worldY + cop.solidArea.y,
                        cop.solidArea.width,
                        cop.solidArea.height
                );

                if (playerBounds.intersects(copBounds)) {
                    gameState = GameState.GAME_OVER;
                    // zarzymanie timera przy komncu gry
                    if (pursuitTimer != null) {
                        pursuitTimer.cancel();
                    }
                    break;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        player.draw(g2);

        for (Cop cop : cops) {
            cop.drawCop(g2);
        }

        drawPursuitLevelStars(g2);

        if (gameState == GameState.GAME_OVER) {
            drawGameOver(g2);
            SwingUtilities.invokeLater(() -> {
                restartButton.setVisible(true);
                restartButton.setBounds(
                        screenWidth / 2 - 50,
                        screenHeight / 2 + 50,
                        100,
                        50
                );
            });
        }

        if (gameState == GameState.GAME_WON) {
            drawGameWon(g2);
            SwingUtilities.invokeLater(() -> {
                restartButton.setVisible(true);
                restartButton.setBounds(
                        screenWidth / 2 - 50,
                        screenHeight / 2 + 50,
                        100,
                        50
                );
            });
        }

        g2.dispose();
    }
    private void drawPursuitLevelStars(Graphics2D g2) {
        BufferedImage starImage = null;
        try {
            starImage = ImageIO.read(getClass().getResourceAsStream("/zdj/star.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int starSize = 16; // Rozmiar gwiazdki
        int padding = 10;  // Odległość między gwiazdkami

        for (int i = 0; i < pursuitLevel; i++) {
            int x = padding + (starSize + padding) * i;
            int y = padding;
            g2.drawImage(starImage, x, y, starSize, starSize, null);
        }
    }
    private void drawGameWon(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, screenWidth, screenHeight);

        g2.setColor(Color.GREEN);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        String text = "YOU WON!";
        int textWidth = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, screenWidth/2 - textWidth/2, screenHeight/2);
    }


    private void drawGameOver(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, screenWidth, screenHeight);

        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        String text = "GAME OVER";
        int textWidth = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, screenWidth/2 - textWidth/2, screenHeight/2);
    }

    public void cleanup() {
        if (pursuitTimer != null) {
            pursuitTimer.cancel();
        }
    }
}



