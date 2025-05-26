package game.managers;

import game.entities.Tower;
import game.entities.TowerType;
import game.ui.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TowerManager {

    private ArrayList<Tower> towers = new ArrayList<>();
    private MoneyManager moneyManager;

    public TowerManager(MoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }

    public boolean addTower(Tower tower) {
        int cost = tower.getMoneyCost();
        if (moneyManager.spendMoney(cost)) {
            System.out.println("Koupil si vez za: " + tower.getMoneyCost() + "$. Nyni ti zbyva: " + moneyManager.getMoney() + "$.");
            towers.add(tower);
            return true;
        }
        JOptionPane.showMessageDialog(null, "Nemas dostatek penez ke koupeni veze :(");
        return false;
    }

    public Tower createTower(TowerType type, int gridX, int gridY, int tileSize) {
        int width = 50, height = 50;
        int range = 150;
        int damage = 25;
        int moneyCost = 50;
        long cooldown = 1000;
        Color color = Color.GRAY;

        switch (type) {
            case BASIC:
                color = Color.BLUE;
                range = 85;
                width = 50;
                height = 50;
                damage = 13;
                cooldown = 1000;
                moneyCost = 100;
                break;
            case SNIPER:
                color = Color.RED;
                range = 250;
                width = 40;
                height = 40;
                damage = 38;
                cooldown = 3000;
                moneyCost = 250;
                break;
            case CANNON:
                color = Color.MAGENTA;
                range = 100;
                width = 60;
                height = 60;
                damage = 25;
                cooldown = 2000;
                moneyCost = 200;
                break;
            case LASER:
                color = Color.ORANGE;
                range = 180;
                width = 40;
                height = 40;
                damage = 10;
                cooldown = 300;
                moneyCost = 400;
                break;
            case ROCKET:
                color = Color.YELLOW;
                range = 200;
                width = 40;
                height = 40;
                damage = 50;
                cooldown = 1000;
                moneyCost = 500;
                break;
            case PLASMA:
                color = Color.GREEN;
                range = 220;
                width = 40;
                height = 40;
                damage = 50;
                cooldown = 800;
                moneyCost = 1000;
                break;
        }
        int x = gridX * tileSize + (tileSize - width) / 2;
        int y = gridY * tileSize + (tileSize - height) / 2;
        return new Tower(x,y, width, height,range, damage, color, cooldown, moneyCost);
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
