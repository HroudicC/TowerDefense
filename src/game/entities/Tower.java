package game.entities;

import game.managers.BulletManager;
import game.managers.EnemyManager;

import java.awt.*;

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


    public Tower(int x, int y, int width, int height, int range, int damage, Color color,long cooldown, int moneyCost) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.range = range;
        this.damage = damage;
        this.color = color;
        this.cooldown = cooldown;
        this.moneyCost = moneyCost;
        this.lastShotTime = 0;
    }

    public int getCenterX(){
        return x+width/2;
    }

    public int getCenterY(){
        return y+height/2;
    }

    public void update(long currentTime, EnemyManager enemyManager, BulletManager bulletManager) {
        int centerX = getCenterX();
        int centerY = getCenterY();


        if (currentEnemy != null) {
            int enemyCenterX = currentEnemy.getCenterX();
            int enemyCenterY = currentEnemy.getCenterY();
            double dx = enemyCenterX - centerX;
            double dy = enemyCenterY - centerY;
            double distance = Math.sqrt(dx * dx + dy * dy); //Vypocitani vzdalenosti mezi enemy a vezi
            if (distance > range || currentEnemy.isDead()) {
                currentEnemy = null;
            }
        }

        if (currentEnemy == null) {
            currentEnemy = enemyManager.findTargetInRange(centerX, centerY, range);
        }

        if (currentEnemy != null && currentTime - lastShotTime >= cooldown) {
            int enemyCenterX = currentEnemy.getCenterX();
            int enemyCenterY = currentEnemy.getCenterY();
            double diffX = enemyCenterX - centerX;
            double diffY = enemyCenterY - centerY;
            double angle = Math.atan2(diffY, diffX); // Vypocet uhlu mezi enemy a vezi

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

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));
        g.drawOval(x - range, y - range, width + 2 * range, height + 2 * range);
    }
}
