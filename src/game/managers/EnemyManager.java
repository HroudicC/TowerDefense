package game.managers;

import game.entities.Bullet;
import game.map.MapLoader;
import game.entities.Enemy;
import game.entities.EnemyType;

import java.awt.*;
import java.util.ArrayList;

public class EnemyManager {

    private ArrayList<Enemy> enemies;
    private MapLoader mapLoader;
    private MoneyManager moneyManager;

    public EnemyManager(MapLoader mapLoader, MoneyManager moneyManager) {
        this.mapLoader = mapLoader;
        this.moneyManager = moneyManager;
        enemies = new ArrayList<>();
    }


    public void spawnEnemy(EnemyType enemyType) {

        int width = mapLoader.getTILE_SIZE();
        int height = mapLoader.getTILE_SIZE();
        int speed, health, moneyReward;
        Color enemyColor;

        switch (enemyType) {
            case EnemyType.BASIC -> {
                speed = 10;
                health = 50;
                enemyColor = Color.PINK;
                moneyReward = 50;
            }
            case EnemyType.SPEED -> {
                speed = 13;
                health = 25;
                enemyColor = Color.YELLOW;
                moneyReward = 50;
            }
            case EnemyType.TANK -> {
                speed = 6;
                health = 100;
                enemyColor = Color.BLACK;
                moneyReward = 50;
            }
            default -> {
                speed = 8;
                health = 20;
                enemyColor = Color.PINK;
                moneyReward = 50;
            }
        }

        Enemy enemy = new Enemy(0, 0, width, height, speed, health, enemyColor, moneyReward, mapLoader);
        enemies.add(enemy);
        System.out.println("Spawnnul se nepřítel typu " + enemyType + ". Celkový počet: " + enemies.size());

    }

    public void update() {
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemy.update();
            if (enemy.hasReachedEnd()) {
                System.out.println("Nepritel dojel do cile");
                enemiesToRemove.add(enemy);
            } else if (enemy.isDead()){
                moneyManager.addMoney(enemy.getMoneyReward());
                enemiesToRemove.add(enemy);
            }
        }
        enemies.removeAll(enemiesToRemove);
    }

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

    public void checkBulletCollision(Bullet bullet) {
        double collisionThreshold = 35;
        for (Enemy enemy : enemies) {

            int enemyCenterX = enemy.getX() + enemy.getWidth() / 2;
            int enemyCenterY = enemy.getY() + enemy.getHeight() / 2;

            //Vzdalenost strely od stredu nepritele
            double diffX = bullet.getX() - enemyCenterX;
            double diffY = bullet.getY() - enemyCenterY;
            double distance = Math.sqrt(diffX * diffX + diffY * diffY);

            if (distance < collisionThreshold) {
                bullet.deactivate();
                enemy.takeDamage(bullet.getDamage());
                break;
            }
        }
    }


    public void draw(Graphics g) {
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
