package game.managers;

import game.entities.Bullet;
import game.map.MapLoader;
import game.entities.Enemy;
import game.entities.EnemyType;
import game.ui.GamePanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * The EnemyManager class manages all operations related to enemies in the game.
 * It is responsible for spawning enemies, updating them, checking for bullet collisions, and drawing them on the screen.
 */
public class EnemyManager {

    private ArrayList<Enemy> enemies;
    private MapLoader mapLoader;
    private MoneyManager moneyManager;
    private LifeManager lifeManager;
    private GamePanel gamePanel;

    /**
     * Constructs a new EnemyManager.
     *
     * @param mapLoader the MapLoader instance used for map properties.
     * @param moneyManager the MoneyManager instance used for handling rewards.
     * @param lifeManager the LifeManager instance used for handling player's lives.
     * @param gamePanel the GamePanel instance for UI updates.
     */
    public EnemyManager(MapLoader mapLoader, MoneyManager moneyManager, LifeManager lifeManager, GamePanel gamePanel) {
        this.mapLoader = mapLoader;
        this.moneyManager = moneyManager;
        this.lifeManager = lifeManager;
        this.gamePanel = gamePanel;
        enemies = new ArrayList<>();
    }


    /**
     * Spawns a new enemy of the specified type.
     * Enemy attributes (speed, health, reward and colour) are determined by enemy type.
     * The enemy is then instanced at the initial position (0, 0) with dimensions defined by the tile size.
     * The new enemy is added to the enemy list.
     *
     * @param enemyType the type of enemy to spawn.
     */
    public void spawnEnemy(EnemyType enemyType) {

        int width = mapLoader.getTILE_SIZE();
        int height = mapLoader.getTILE_SIZE();
        int speed, health, moneyReward;
        Color enemyColor;

        switch (enemyType) {
            case BASIC -> {
                speed = 10;
                health = 50;
                enemyColor = Color.PINK;
                moneyReward = 50;
            }
            case SPEED -> {
                speed = 16;
                health = 40;
                enemyColor = Color.YELLOW;
                moneyReward = 60;
            }
            case TANK -> {
                speed = 6;
                health = 200;
                enemyColor = Color.BLACK;
                moneyReward = 80;
            }
            case PHANTOM -> {
                speed = 13;
                health = 140;
                enemyColor = Color.GRAY;
                moneyReward = 100;
            }
            case ELITE -> {
                speed = 7;
                health = 320;
                enemyColor = Color.RED;
                moneyReward = 125;
            }
            default -> {
                speed = 8;
                health = 20;
                enemyColor = Color.PINK;
                moneyReward = 50;
            }
        }

        Enemy enemy = new Enemy(0, 0, width, height, speed, health, enemyType, enemyColor, moneyReward, mapLoader);
        enemies.add(enemy);
        System.out.println("A " + enemyType + " enemy has spawned. Total: " + enemies.size());
    }

    /**
     * Updates all enemies.
     * For each enemy, this method will call the update function, which will  move the enemy.
     * Then it checks if the enemy has reached the end of the path or died.
     * If the enemy has reached the end, the player's life will be reduced.
     * If the enemy is dead, the player is rewarded with money.
     * Enemies that have reached the end or died are removed from the enemy list.
     */
    public void update() {
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemy.update();
            if (enemy.hasReachedEnd()) {
                System.out.println("The enemy has reached the end.");
                lifeManager.loseLife(15);
                gamePanel.updateHealthLabel();
                enemiesToRemove.add(enemy);
            } else if (enemy.isDead()){
                moneyManager.addMoney(enemy.getMoneyReward());
                enemiesToRemove.add(enemy);
                gamePanel.updateMoneyLabel();
            }
        }
        enemies.removeAll(enemiesToRemove);
    }

    /**
     * Finds the closest enemy that is located in the specified range of the specified tower coordinates.
     * The method calculates the distance between the center of each enemy and the specified tower position.
     * If the enemy is in the range and closer than any previously marked enemy, it is saved as a target.
     *
     * @param towerX the x-coordinate of the tower's position.
     * @param towerY the y-coordinate of the tower's position.
     * @param range the maximum distance from the tower.
     * @return the closest enemy in the range, or null if none is found.
     */
    public Enemy findTargetInRange(int towerX, int towerY, int range) {
        Enemy closestEnemy = null;
        double closestDistance = Double.MAX_VALUE;

        for (Enemy enemy: enemies) {
            int enemyCenterX = enemy.getX() + enemy.getWidth() / 2;
            int enemyCenterY = enemy.getY() + enemy.getHeight() / 2;
            double distance = Math.sqrt(Math.pow(towerX - enemyCenterX, 2) + Math.pow(towerY - enemyCenterY, 2));
            if (distance <= range && distance < closestDistance) {
               closestEnemy = enemy;
               closestDistance = distance;
            }
        }
        return closestEnemy;
    }


    /**
     * The method calculates the distance between the bullet and the enemy.
     * The method calculates the distance between the bullet and the center of each enemy.
     * If the distance is less than the specified collision threshold, the bullet is deactivated and the enemy takes damage.
     * Source: This link and chatGPT helped me with this method: https://www.baeldung.com/java-distance-between-two-points
     *
     * @param bullet the bullet to check for collision with enemies.
     */

    public void checkBulletCollision(Bullet bullet) {
        double collisionThreshold = 40;
        for (Enemy enemy : enemies) {

            int enemyCenterX = enemy.getX() + enemy.getWidth() / 2;
            int enemyCenterY = enemy.getY() + enemy.getHeight() / 2;

            // Calculate the horizontal and vertical difference between the bullet's position and the enemy's center.
            double diffX = bullet.getX() - enemyCenterX;
            double diffY = bullet.getY() - enemyCenterY;
            // Calculate the distance from the bullet to the enemy's center using the Pythagorean theorem.
            double distance = Math.sqrt(diffX * diffX + diffY * diffY);

            if (distance < collisionThreshold) {
                bullet.deactivate();
                enemy.takeDamage(bullet.getDamage());
                break;
            }
        }
    }

    /**
     * Draws all enemies on the screen.
     *
     * @param g the Graphics context used for drawing the enemies.
     */
    public void draw(Graphics g) {
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
