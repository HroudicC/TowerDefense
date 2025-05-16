package game.managers;

import game.entities.Tower;
import game.entities.TowerType;

import java.awt.*;
import java.util.ArrayList;

public class TowerManager {

    private ArrayList<Tower> towers = new ArrayList<>();

    public void addTower(Tower tower) {
        towers.add(tower);
    }

    public Tower createTower(TowerType type, int gridX, int gridY, int tileSize) {
        int width = 50, height = 50;
        int range = 150;
        int damage = 25;
        Color color = Color.GRAY;

        switch (type) {
            case BASIC:
                color = Color.BLUE;
                range = 150;
                width = 50;
                height = 50;
                damage = 35;
                break;
            case SNIPER:
                color = Color.RED;
                range = 220;
                width = 40;
                height = 40;
                damage = 25;
                break;
            case BOXER:
                color = Color.MAGENTA;
                range = 85;
                width = 60;
                height = 60;
                damage = 40;
                break;
        }
        int x = gridX * tileSize + (tileSize - width) / 2;
        int y = gridY * tileSize + (tileSize - height) / 2;
        return new Tower(x,y, width, height,range, damage, color);
    }

    public boolean isTowerAt(int gridX, int gridY, int tileSize) {
        for (Tower tower : towers) {

            int towerGridX = tower.getX() / tileSize;
            int towerGridY = tower.getY() / tileSize;

            if (towerGridX == gridX && towerGridY == gridY) {
                return true;
            }
        }
        return false;
    }

    public void update(long currentTime, EnemyManager enemyManager, BulletManager bulletManager) {
        for (Tower tower : towers) {
            tower.update(currentTime, enemyManager, bulletManager);
        }
    }


    public void draw(Graphics g) {
        for (Tower tower : towers) {
            tower.draw(g);
        }
    }

}
