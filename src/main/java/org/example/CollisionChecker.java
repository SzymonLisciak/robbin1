package org.example;

public class CollisionChecker {
    App app;
    public CollisionChecker(App app) {
        this.app = app;
    }
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol =  entityLeftWorldX/app.tileSize;
        int entityRightCol = entityRightWorldX/app.tileSize;
        int entityTopRow = entityTopWorldY/app.tileSize;
        int entityBottomRow = entityBottomWorldY/app.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                break;
                case "down":
                    break;

        }
    }
}
