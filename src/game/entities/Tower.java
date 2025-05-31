package game.entities;

import game.assets.AssetLoader;
import game.managers.BulletManager;
import game.managers.EnemyManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Tower class represents a tower in the game.
 * A tower is positioned on the game grid and is responsible for targeting enemies within its range and firing bullets at them.
 * Each tower has specific attributes such as range, damage, cost, and firing cooldown.
 * The tower uses an image asset for its appearance if available, otherwise, it renders a colored rectangle.
 */
public class Tower  {

    private int x, y;
    private int width, height;
    private int range;
    private int damage;
    private int moneyCost;
    private Color color;

    private double bulletSpeed = 27;
    private long lastShotTime;
    private long cooldown;

    private Enemy currentEnemy;
    private TowerType towerType;
    private BufferedImage towerImage;


    /**
     * Constructs a new Tower with the specified parameters.
     *
     * @param x  the x-coordinate of the tower's position.
     * @param y  the y-coordinate of the tower's position.
     * @param width the width of the tower.
     * @param height the height of the tower.
     * @param range the range of the tower in pixels.
     * @param damage the damage caused by the tower's bullets.
     * @param towerType the type of the tower.
     * @param color the fallback color of the tower if the tower image asset is unavailable.
     * @param cooldown the cooldown time in milliseconds between sequence of shots.
     * @param moneyCost the cost to buy the tower.
     */
    public Tower(int x, int y, int width, int height, int range, int damage, TowerType towerType, Color color,long cooldown, int moneyCost) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.range = range;
        this.damage = damage;
        this.towerType = towerType;
        this.color = color;
        this.cooldown = cooldown;
        this.moneyCost = moneyCost;
        this.lastShotTime = 0;

        towerImage = AssetLoader.loadImage(towerType.getImagePath());
    }

    /**
     * Returns the x-coordinate of the tower's center.
     *
     * @return the center x-coordinate of the tower.
     */
    public int getCenterX(){
        return x+width/2;
    }

    /**
     * Returns the y-coordinate of the tower's center.
     *
     * @return the center y-coordinate of the tower
     */
    public int getCenterY(){
        return y+height/2;
    }

    /**
     * Updates the tower's targeting and firing mechanism.
     * The tower first checks if the current target is still valid (in range and not dead).
     * If the current target is invalid, it searches for a new enemy within range.
     * If a valid target is found and the shooting cooldown period has passed, the tower calculates the firing angle,
     * creates a new bullet, and adds it to the BulletManager.
     * Source: ChatGPT and this link helped me with this method: https://www.baeldung.com/java-distance-between-two-points
     *
     * @param currentTime the current time in milliseconds.
     * @param enemyManager the EnemyManager used to locate targets within range.
     * @param bulletManager the BulletManager used to handle the fired bullets.
     */
    public void update(long currentTime, EnemyManager enemyManager, BulletManager bulletManager) {
        int centerX = getCenterX();
        int centerY = getCenterY();

        if (currentEnemy != null) {
            int enemyCenterX = currentEnemy.getCenterX();
            int enemyCenterY = currentEnemy.getCenterY();
            // Calculate the horizontal and vertical difference between enemy and tower.
            double dx = enemyCenterX - centerX;
            double dy = enemyCenterY - centerY;
            // Calculate the distance using the Pythagorean theorem.
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance > range || currentEnemy.isDead()) {
                currentEnemy = null;
            }
        }

        if (currentEnemy == null) {
            currentEnemy = enemyManager.findTargetInRange(centerX, centerY, range);
        }

        // If a target exists and the cooldown period has passed, fire a bullet.
        if (currentEnemy != null && currentTime - lastShotTime >= cooldown) {
            int enemyCenterX = currentEnemy.getCenterX();
            int enemyCenterY = currentEnemy.getCenterY();
            // Compute horizontal and vertical distance between tower and enemy.
            double diffX = enemyCenterX - centerX;
            double diffY = enemyCenterY - centerY;
            // Calculate the firing angle (in radians) using arctan2.
            double angle = Math.atan2(diffY, diffX);

            Bullet newBullet = new Bullet(centerX, centerY, damage, bulletSpeed, angle);
            bulletManager.addBullet(newBullet);
            lastShotTime = currentTime;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMoneyCost() {
        return moneyCost;
    }

    /**
     * Renders the tower on the given graphics context.
     * If a tower image is available, it is drawn at the tower's position.
     * Otherwise, a colored rectangle is drawn.
     *
     * @param g the Graphics context used for rendering the tower.
     */
    public void draw(Graphics g) {
       if (towerImage != null) {
           g.drawImage(towerImage, x, y, null);
       }else {
           g.setColor(color);
           g.fillRect(x, y, width, height);
       }
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));
        g.drawOval(x - range, y - range, width + 2 * range, height + 2 * range);
    }
}
