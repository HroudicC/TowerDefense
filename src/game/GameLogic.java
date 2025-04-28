package game;

import game.entities.Enemy;
import game.map.MapLoader;

import java.awt.*;
import java.util.ArrayList;

public class GameLogic {
    private MapLoader mapLoader;
    private ArrayList<Enemy> enemies;
    private long lastSpawnTime;
    private int spawnInterval = 2000;

    public GameLogic(MapLoader mapLoader) {
        this.mapLoader = mapLoader;

        enemies = new ArrayList<>();
        lastSpawnTime = System.currentTimeMillis();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSpawnTime >= spawnInterval) {
            spawnEnemy();
            lastSpawnTime = currentTime;
        }

        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemy.update();
            if (enemy.hasReachedEnd()) {
                System.out.println("Nepřítel dorazil do cíle!");
                enemiesToRemove.add(enemy);
            }
        }
        enemies.removeAll(enemiesToRemove);
    }

    //Metoda pro vyzkouseni funkcnosti
    private void spawnEnemy() {
        int width = mapLoader.getTILE_SIZE();
        int height = mapLoader.getTILE_SIZE();
        int speed = 2;
        int health = 10;

        Enemy newEnemy = new Enemy(0, 0, width, height, speed, health, mapLoader);
        enemies.add(newEnemy);
        System.out.println("Spawnnul se nový nepřítel, celkový počet: " + enemies.size());
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
