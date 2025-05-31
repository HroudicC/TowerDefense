package game.entities;

import java.awt.*;

/**
 * The Bullet class represents a bullet fired by a tower in the game.
 * Each bullet has a position (x, y), speed (dx, dy), damage and a fixed size.
 * The bullets move along a straight line determined by the initial angle and become inactive
 * if they leave the borders of the game map.
 */
public class Bullet {

    private double x, y;
    private double dx, dy;
    private int width = 15, height = 15;
    private int damage;
    private double speed;
    private Color color = Color.RED;
    private boolean isActive = true;

    //Size of the map
    private static int MAP_WIDTH = 1200;
    private static int MAP_HEIGHT = 750;

    /**
     * Constructs a new Bullet with the specified starting position, damage, speed, and travel angle.
     *
     * @param startX the starting x-coordinate of the bullet.
     * @param startY the starting y-coordinate of the bullet.
     * @param damage the damage caused by the bullet.
     * @param speed the speed of the bullet.
     * @param angle the angle of the shot (in radians), that determines the bullet's trajectory.
     */
    public Bullet(double startX, double startY, int damage, double speed, double angle) {
        this.x = startX;
        this.y = startY;
        this.damage = damage;
        this.speed = speed;
        // Calculate horizontal and vertical speed components based on the firing angle.
        this.dx = Math.cos(angle) * speed;
        this.dy = Math.sin(angle) * speed;
    }

    /**
     * Updates the bullet's position based on its speed.
     * The bullet is deactivated if it moves outside the bounds of the game map.
     */
    public void update() {
        if (!isActive) return;
        x += dx;
        y += dy;

        if (x < 0|| x > MAP_WIDTH || y < 0|| y > MAP_HEIGHT)
            deactivate();
    }

    public void deactivate() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * Draws the bullet on the provided Graphics context.
     *
     * @param g the Graphics context used for drawing the bullet.
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) x, (int) y, width, height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public int getDamage() {
        return damage;
    }

}
