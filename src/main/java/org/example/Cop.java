package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;

public class Cop extends Entity implements Transform {
    private int screenX;
    private int screenY;
    private List<Point> path;
    private Point currentTarget;
    private double preciseX;
    private double preciseY;

    public Cop(App app) {
        super(app);
        setRandomDefaultValues();
        getCopImage();
        solidArea = new Rectangle(8, 16, 32, 32);
    }

    private void setRandomDefaultValues() {
        Random random = new Random();
        int worldCol, worldRow, tileNum;
        do {
            worldCol = random.nextInt(app.maxWorldCol);
            worldRow = random.nextInt(app.maxWorldRow);
            tileNum = app.tileM.mapTileNum[worldRow][worldCol];
        } while (app.tileM.tiles[tileNum].collision);

        worldX = worldCol * app.tileSize;
        worldY = worldRow * app.tileSize;
        preciseX = worldX;
        preciseY = worldY;
        speed = 3;
        direction = "right";
    }

    private void getCopImage() {
        try {
            this.left1 = ImageIO.read(getClass().getResourceAsStream("/zdj/cop_left.png"));
            this.right1 = ImageIO.read(getClass().getResourceAsStream("/zdj/cop_right.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Node implements Comparable<Node> {
        int x, y;
        int gCost, hCost;
        Node parent;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getFCost() {
            return gCost + hCost;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.getFCost(), other.getFCost());
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Node) {
                Node other = (Node) obj;
                return this.x == other.x && this.y == other.y;
            }
            return false;
        }
    }

    private List<Point> findPath(int startX, int startY, int targetX, int targetY) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();

        Node startNode = new Node(startX, startY);
        Node targetNode = new Node(targetX, targetY);

        startNode.gCost = 0;
        startNode.hCost = Math.abs(startX - targetX) + Math.abs(startY - targetY);

        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();

            if (currentNode.equals(targetNode)) {
                return reconstructPath(currentNode);
            }

            closedSet.add(currentNode);

            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int[] dir : directions) {
                int newX = currentNode.x + dir[0];
                int newY = currentNode.y + dir[1];

                if (newX < 0 || newX >= app.maxWorldCol || newY < 0 || newY >= app.maxWorldRow) {
                    continue;
                }

                int tileNum = app.tileM.mapTileNum[newY][newX];
                if (app.tileM.tiles[tileNum].collision) {
                    continue;
                }

                Node neighborNode = new Node(newX, newY);

                if (closedSet.contains(neighborNode)) {
                    continue;
                }

                int newMovementCostToNeighbor = currentNode.gCost + 1;

                if (!openSet.contains(neighborNode) || newMovementCostToNeighbor < neighborNode.gCost) {
                    neighborNode.gCost = newMovementCostToNeighbor;
                    neighborNode.hCost = Math.abs(newX - targetX) + Math.abs(newY - targetY);
                    neighborNode.parent = currentNode;

                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                    }
                }
            }
        }

        return null;
    }

    private List<Point> reconstructPath(Node targetNode) {
        List<Point> path = new ArrayList<>();
        Node current = targetNode;

        while (current != null) {
            path.add(new Point(current.x, current.y));
            current = current.parent;
        }

        Collections.reverse(path);
        return path;
    }

    public void updateCop() {
        int playerTileX = app.player.worldX / app.tileSize;
        int playerTileY = app.player.worldY / app.tileSize;
        int copTileX = (int) preciseX / app.tileSize;
        int copTileY = (int) preciseY / app.tileSize;

        if (path == null || path.isEmpty()) {
            path = findPath(copTileX, copTileY, playerTileX, playerTileY);
            if (path != null && path.size() > 1) {
                currentTarget = new Point(path.get(1).x * app.tileSize, path.get(1).y * app.tileSize);
            }
        }

        if (currentTarget != null) {
            double dx = currentTarget.x - preciseX;
            double dy = currentTarget.y - preciseY;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance > speed) {
                double moveX = (dx / distance) * speed;
                double moveY = (dy / distance) * speed;

                preciseX += moveX;
                preciseY += moveY;

                worldX = (int) preciseX;
                worldY = (int) preciseY;

                if (Math.abs(moveX) > Math.abs(moveY)) {
                    direction = moveX > 0 ? "right" : "left";
                } else {
                    direction = moveY > 0 ? "down" : "up";
                }
            } else {
                worldX = currentTarget.x;
                worldY = currentTarget.y;
                preciseX = worldX;
                preciseY = worldY;

                if (path != null) {
                    path.remove(0);
                    if (!path.isEmpty()) {
                        currentTarget = new Point(path.get(0).x * app.tileSize, path.get(0).y * app.tileSize);
                    } else {
                        currentTarget = null;
                    }
                }
            }
        }

        //pozycja
        screenX = worldX - app.player.worldX + app.player.screenX;
        screenY = worldY - app.player.worldY + app.player.screenY;
    }

    public void drawCop(Graphics2D g2) {
        BufferedImage image = null;
        switch(direction) {
            case "left", "up":
                image = left1;
                break;
            case "right", "down":
                image = right1;
                break;
        }

        g2.drawImage(image, screenX, screenY, app.tileSize, app.tileSize, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(
                screenX + solidArea.x,
                screenY + solidArea.y,
                solidArea.width,
                solidArea.height
        );
    }

    @Override
    public void EntityTransformation(Graphics2D g2) {
        // Opcjonalna transformacja
    }
}