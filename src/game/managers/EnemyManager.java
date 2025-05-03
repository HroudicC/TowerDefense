package game.managers;

import game.map.MapLoader;
import game.entities.Enemy;
import game.entities.EnemyType;

import java.awt.*;
import java.util.ArrayList;

public class EnemyManager {

    private ArrayList<Enemy> enemies;
    private MapLoader mapLoader;

    public EnemyManager(MapLoader mapLoader) {
        this.mapLoader = mapLoader;
        enemies = new ArrayList<>();
    }


    public void spawnEnemy(EnemyType enemyType) {

        int width = mapLoader.getTILE_SIZE();
        int height = mapLoader.getTILE_SIZE();
        int speed = 8;
        int health = 20;
        Color enemyColor = Color.PINK;

        switch (enemyType) {
            case EnemyType.BASIC -> {
                speed = 5;
                health = 20;
                enemyColor = Color.PINK;
                break;
            }
            case EnemyType.SPEED -> {
                speed = 20;
                health = 15;
                enemyColor = Color.YELLOW;
                break;
            }
            case EnemyType.TANK -> {
                speed = 5;
                health = 25;
                enemyColor = Color.BLACK;
                break;
            }
            default -> {
                speed = 8;
                health = 20;
                enemyColor = Color.PINK;
            }
        }

        Enemy enemy = new Enemy(0, 0, width, height, speed, health, enemyColor,  mapLoader);
        enemies.add(enemy);
        System.out.println("Spawnnul se nepřítel typu " + enemyType + ". Celkový počet: " + enemies.size());

    }

    public void update() {
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemy.update();
            if (enemy.hasReachedEnd()) {
                System.out.println("Nepřítel dorazil do cíle a bude odstraněn.");
                enemiesToRemove.add(enemy);
            }
        }
        enemies.removeAll(enemiesToRemove);
    }

    public void draw(Graphics g) {
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public MapLoader getMapLoader() {
        return mapLoader;
    }

    public void setMapLoader(MapLoader mapLoader) {
        this.mapLoader = mapLoader;
    }
}
