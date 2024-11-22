package org.example;

public class EffectChecker  {
    private final App app;

    public EffectChecker(App app) {
        this.app = app;
    }
            public void checkTile(Entity entity) {
                int entityLeftWorldX = entity.worldX + entity.solidArea.x;
                int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
                int entityTopWorldY = entity.worldY + entity.solidArea.y;
                int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

                int entityLeftCol = entityLeftWorldX / app.tileSize;
                int entityRightCol = entityRightWorldX / app.tileSize;
                int entityTopRow = entityTopWorldY / app.tileSize;
                int entityBottomRow = entityBottomWorldY / app.tileSize;

                int tileNum1, tileNum2;

                switch (entity.direction) {
                    case "up":
                        entityTopRow = (entityTopWorldY - entity.speed)/app.tileSize;
                        tileNum1 = app.tileM.mapTileNum[entityTopRow][entityLeftCol];
                        tileNum2 = app.tileM.mapTileNum[entityTopRow][entityRightCol];
                        if (app.tileM.tiles[tileNum1].slowness || app.tileM.tiles[tileNum2].slowness) {
                            entity.slownessOn = true;
                        }
                        else if (app.tileM.tiles[tileNum1].fast || app.tileM.tiles[tileNum2].fast) {
                            entity.fastOn = true;
                        }

                        break;
                    case "down":
                        entityBottomRow = (entityBottomWorldY + entity.speed)/app.tileSize;
                        tileNum1 = app.tileM.mapTileNum[entityBottomRow][entityLeftCol];
                        tileNum2 = app.tileM.mapTileNum[entityBottomRow][entityRightCol];
                        if (app.tileM.tiles[tileNum1].slowness || app.tileM.tiles[tileNum2].slowness) {
                            entity.slownessOn = true;
                        }
                        else if (app.tileM.tiles[tileNum1].fast || app.tileM.tiles[tileNum2].fast) {
                            entity.fastOn = true;
                        }
                        break;
                    case "left":
                        entityLeftCol = (entityLeftWorldX - entity.speed)/app.tileSize;
                        tileNum1 = app.tileM.mapTileNum[entityTopRow][entityLeftCol];
                        tileNum2 = app.tileM.mapTileNum[entityBottomRow][entityLeftCol];
                        if (app.tileM.tiles[tileNum1].slowness || app.tileM.tiles[tileNum2].slowness) {
                            entity.slownessOn = true;
                        }
                        else if (app.tileM.tiles[tileNum1].fast || app.tileM.tiles[tileNum2].fast) {
                            entity.fastOn = true;
                        }
                        break;
                    case "right":
                        entityRightCol = (entityRightWorldX + entity.speed)/app.tileSize;
                        tileNum1 = app.tileM.mapTileNum[entityTopRow][entityRightCol];
                        tileNum2 = app.tileM.mapTileNum[entityBottomRow][entityRightCol];
                        if (app.tileM.tiles[tileNum1].slowness || app.tileM.tiles[tileNum2].slowness) {
                            entity.slownessOn = true;
                        }
                        else if (app.tileM.tiles[tileNum1].fast || app.tileM.tiles[tileNum2].fast) {
                            entity.fastOn = true;
                        }
                        break;}

    }
}


