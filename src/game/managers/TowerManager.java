package game.managers;

import game.entities.Tower;
import game.entities.TowerType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The TowerManager class manages towers in the game.
 * It handles creating new towers, adding them to the game (if enough money is available),
 * checking the placement of towers on the grid (game map) and updating tower status and drawing towers.
 */
public class TowerManager {

    private ArrayList<Tower> towers = new ArrayList<>();
    private MoneyManager moneyManager;

    /**
     * Constructs a new TowerManager.
     *
     * @param moneyManager the MoneyManager used to manage game currency.
     */
    public TowerManager(MoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }

    /**
     * Tries to add a tower to the game.
     * It first checks if the player has enough money to buy the tower and if the purchase is successful,
     * the tower is added and the money is deducted.
     * Otherwise, a pop-up window message is displayed.
     *
     * @param tower the tower to be added.
     * @return true if the tower was successfully added.
     */
    public boolean addTower(Tower tower) {
        int cost = tower.getMoneyCost();
        if (moneyManager.spendMoney(cost)) {
            System.out.println("You bought a tower for: " + tower.getMoneyCost() + "$. Now you have: " + moneyManager.getMoney() + "$.");
            towers.add(tower);
            return true;
        }
        JOptionPane.showMessageDialog(null, "You don't have enough money to buy a tower :(");
        return false;
    }

    /**
     * Creates a new tower based on the specified tower type and grid position.
     * The method sets default parameters for each tower type, such as range, damage, response time, and cost,
     * and calculates the tower's position in the grid.
     *
     * @param towerType the type of tower to be created.
     * @param gridX the x-coordinate in the grid.
     * @param gridY the y-coordinate in the grid.
     * @param tileSize the size of a tile, used for calculating the tower's position.
     * @return a new Tower.
     */
    public Tower createTower(TowerType towerType, int gridX, int gridY, int tileSize) {
        int width = 75, height = 75;
        int range = 150;
        int damage = 25;
        int moneyCost = 50;
        long cooldown = 1000;
        Color color = Color.GRAY;

        switch (towerType) {
            case BASIC:
                color = Color.BLUE;
                range = 85;
                damage = 13;
                cooldown = 1000;
                moneyCost = 100;
                break;
            case SNIPER:
                color = Color.RED;
                range = 250;
                damage = 38;
                cooldown = 2500;
                moneyCost = 250;
                break;
            case CANNON:
                color = Color.MAGENTA;
                range = 100;
                damage = 25;
                cooldown = 2000;
                moneyCost = 200;
                break;
            case LASER:
                color = Color.ORANGE;
                range = 180;
                damage = 5;
                cooldown = 100;
                moneyCost = 400;
                break;
            case ROCKET:
                color = Color.YELLOW;
                range = 200;
                damage = 50;
                cooldown = 1000;
                moneyCost = 500;
                break;
            case MINIGUN:
                color = Color.GREEN;
                range = 220;
                damage = 10;
                cooldown = 150;
                moneyCost = 1000;
                break;
        }
        // Calculate the coordinates of the tower so that it is placed in the middle of the tile.
        int x = gridX * tileSize + (tileSize - width) / 2;
        int y = gridY * tileSize + (tileSize - height) / 2;
        return new Tower(x,y, width, height,range, damage, towerType ,color, cooldown, moneyCost);
    }

    /**
     * Checks if the tower is already placed at the specified grid position.
     *
     * @param gridX the x-coordinate in the grid.
     * @param gridY the y-coordinate in the grid.
     * @param tileSize the size of a tile, used for calculation.
     * @return true if there is already a tower at the specified position.
     */
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

    /**
     * Updates all towers by calling their updates methods.
     * The update process uses the current time, an enemy manager for targeting,
     * and a bullet manager to handle bullets fired.
     *
     * @param currentTime the current time in milliseconds.
     * @param enemyManager the EnemyManager used to manage enemies.
     * @param bulletManager the BulletManager used to manage bullets.
     */
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
