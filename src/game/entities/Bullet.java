package game.entities;

import java.awt.*;

public class Bullet {

    private double x, y;
    private double dx, dy;
    private int width = 15, height = 15;
    private int damage;
    private double speed;
    private Color color = Color.RED;
    private boolean isActive = true;

    private static int MAP_WIDTH = 1200;
    private static int MAP_HEIGHT = 750;

    public Bullet(double startX, double startY, int damage, double speed, double angle) {
        this.x = startX;
        this.y = startY;
        this.damage = damage;
        this.speed = speed;
        this.dx = Math.cos(angle) * speed;
        this.dy = Math.sin(angle) * speed;
    }



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
