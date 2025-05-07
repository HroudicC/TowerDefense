package game.entities;

import java.awt.*;

public class Tower  {

    private int x, y;
    private int width, height;
    private int range;
    private Color color;


    public Tower(int x, int y, int width, int height, int range, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.range = range;
        this.color = color;
    }

    public void update() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
