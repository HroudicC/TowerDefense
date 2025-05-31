package game.managers;

import game.entities.Bullet;

import java.awt.*;
import java.util.ArrayList;

/**
 * The BulletManager class manages all bullets in the game.
 * Keeps a list of active bullets, updates their statuses (including collision checking), and renders them on the screen.
 */
public class BulletManager {

    private ArrayList<Bullet> bullets = new ArrayList<>();

    /**
     * Adds a new bullet to the bullet Arraylist.
     *
     * @param bullet the bullet to be added.
     */
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    /**
     * Updates all bullets by starting their own update method and checking for collisions with enemies using the provided EnemyManager.
     * Any bullet that becomes inactive during the update is removed from the list.
     *
     * @param enemyManager the EnemyManager instance used to check for bullet collisions.
     */
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

    /**
     * Draws all active bullets on the screen.
     *
     * @param g the Graphics context used for drawing the bullets
     */
    public void draw(Graphics g) {
        for(Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }
}
