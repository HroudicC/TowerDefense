package game.managers;

import game.entities.Bullet;

import java.awt.*;
import java.util.ArrayList;

public class BulletManager {

    private ArrayList<Bullet> bullets = new ArrayList<>();

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void update(EnemyManager enemyManager) {
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            bullet.update();
            enemyManager.checkBulletCollision(bullet);
            if (!bullet.isActive()){
                bulletsToRemove.add(bullet);
            }
        }
        bullets.removeAll(bulletsToRemove);
    }

    public void draw(Graphics g) {
        for(Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }
}
