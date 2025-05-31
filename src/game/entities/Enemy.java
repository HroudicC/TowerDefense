package game.entities;

import game.assets.AssetLoader;
import game.map.MapLoader;
import game.map.TileType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The Enemy class represents an enemy that moves on the map along a calculated path.
 * The enemy uses a depth-first search (DFS) algorithm to determine its path from a START tile to an END tile
 * and moves along that path. It contains properties like speed, health, and a money reward that is given upon its defeat.
 * ChatGPT helped me with this class.
 */
public class Enemy {

    private int x, y, width, height;

    private int speed, health, moneyReward;
    private int pathIndex = 0;
    private ArrayList<Point> path = new ArrayList<>();
    private Color enemyColor;

    private MapLoader mapLoader;
    private boolean pathCalculated = false;

    private BufferedImage enemyImage;
    private EnemyType enemyType;

    /**
     * Constructs a new Enemy with the specified parameters.
     *
     * @param x the initial x-coordinate.
     * @param y the initial y-coordinate.
     * @param width the width of the enemy.
     * @param height the height of the enemy.
     * @param speed the movement speed of the enemy.
     * @param health the initial health of the enemy.
     * @param enemyType the type of enemy (used to load the corresponding image asset).
     * @param enemyColor the fallback color of the enemy if the image is unavailable.
     * @param moneyReward the reward amount given when the enemy is defeated.
     * @param mapLoader the MapLoader instance used to retrieve map data for path calculation.
     */
    public Enemy(int x, int y, int width, int height, int speed, int health, EnemyType enemyType, Color enemyColor, int moneyReward, MapLoader mapLoader) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.health = health;
        this.enemyType = enemyType;
        this.enemyColor = enemyColor;
        this.moneyReward = moneyReward;
        this.mapLoader = mapLoader;

        enemyImage = AssetLoader.loadImage(enemyType.getImagePath());
    }

    /**
     * Recursively performs a depth-first search (DFS) to find a valid path from the current position to the target endpoint.
     * It searches in four directions (right, left, down, up) until it finds a path.
     *
     * @param x the current x-coordinate.
     * @param y the current y-coordinate.
     * @param end the target endpoint.
     * @param path the current path taken as a list of Points.
     * @param visited a 2D array marking visited grid positions.
     * @return true if a valid path to the endpoint is found, false otherwise.
     */
    private boolean dfs(int x, int y, Point end, ArrayList<Point> path, boolean[][] visited) {

        if (x < 0 || y < 0 || y >= mapLoader.getMapByRows().size() || x >= mapLoader.getMapByRows().get(0).length) {
            return false;
        }
        if (visited[y][x]) {
            return false;
        }

        TileType tile = mapLoader.getMapByRows().get(y)[x];

        if (tile != TileType.HORIZONTAL_PATH && tile != TileType.VERTICAL_PATH
                && tile != TileType.LEFT_TO_DOWN_PATH && tile != TileType.LEFT_TO_UP_PATH
                && tile != TileType.RIGHT_TO_DOWN_PATH && tile != TileType.RIGHT_TO_UP_PATH
                && tile != TileType.START && tile != TileType.END) {
            return false;
        }
        visited[y][x] = true;
        path.add(new Point(x, y));

        if (x == end.x && y == end.y) {
            return true;
        }

        if (dfs(x + 1, y, end, path, visited)) return true;
        if (dfs(x - 1, y, end, path, visited)) return true;
        if (dfs(x, y + 1, end, path, visited)) return true;
        if (dfs(x, y - 1, end, path, visited)) return true;

        // If the current path does not lead to the endpoint, backtrack.
        path.remove(path.size() - 1);
        return false;
    }

    /**
     * Calculates the enemy's path by finding the START and END tiles in the map and calling DFS.
     * Once a valid path is found, the enemy's starting position (in pixels) is set based on the START tile.
     */
    public void calculatePath() {
        Point start = null;
        Point end = null;

        ArrayList<TileType[]> rows = mapLoader.getMapByRows();
        for (int y = 0; y < rows.size(); y++) {
            TileType[] row = rows.get(y);
            for (int x = 0; x < row.length; x++) {
                if (row[x] == TileType.START) {
                    start = new Point(x, y);
                } else if (row[x] == TileType.END) {
                    end = new Point(x, y);
                }
            }
        }

        if (start != null && end != null) {
            boolean[][] visited = new boolean[rows.size()][rows.get(0).length];
            if (dfs(start.x, start.y, end, path, visited)) {
                pathCalculated = true;
                // Assign initial pixel position based on the START tile.
                this.x = start.x * mapLoader.getTILE_SIZE();
                this.y = start.y * mapLoader.getTILE_SIZE();
            } else {
                System.out.println("Path was not found!");
            }
        } else {
            System.out.println("Start or End point was not found!");
        }
    }

    /**
     * Updates the enemy's position along its calculated path. If the enemy is close enough to the next target tile
     * (based on its speed), it snaps to that tile and proceeds to the next target tile.
     */
    public void update() {

        if (!pathCalculated) {
            calculatePath();
        }

        if (pathCalculated && pathIndex < path.size()) {
            Point targetTile = path.get(pathIndex);
            int targetX = targetTile.x * mapLoader.getTILE_SIZE();
            int targetY = targetTile.y * mapLoader.getTILE_SIZE();
            int dx = targetX - x;
            int dy = targetY - y;
            // Calculate the distance to the target tile.
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= speed) {
                x = targetX;
                y = targetY;
                pathIndex++; // Proceed to the next point in the path.
            } else {
                x += (int) ((dx / distance) * speed);
                y += (int) ((dy / distance) * speed);
            }
        }
    }

    /**
     * Deal damage to an enemy by deducting that damage value from their health.
     *
     * @param damage the amount of damage to apply.
     */
    public void takeDamage(int damage) {
        this.health -= damage;

        if(this.health <= 0) {
            this.health = 0;
            System.out.println("Enemy is dead. Reward: " + moneyReward + "$.");
        }
    }

    /**
     * Checks if the enemy has reached the end of its path.
     *
     * @return true if the enemy has reached its final destination.
     */
    public boolean hasReachedEnd() {
        return pathCalculated && pathIndex >= path.size();
    }

    public boolean isDead(){
       return health <= 0;
    }

    public int getMoneyReward() {
        return moneyReward;
    }

    /**
     * Calculates and returns the x-coordinate of the enemy's center.
     *
     * @return the center x-coordinate (in pixels).
     */
    public int getCenterX() {
        return x + width / 2;
    }

    /**
     * Calculates and returns the y-coordinate of the enemy's center.
     *
     * @return the center y-coordinate (in pixels).
     */
    public int getCenterY() {
        return y + height / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Renders the enemy on the given graphics context.
     * If an image is available, it is rendered, but otherwise a colored oval is used.
     *
     * @param g the Graphics context used for drawing.
     */
    public void draw(Graphics g) {
        if (enemyImage != null) {
            g.drawImage(enemyImage,x,y,null);
        }else{
            g.setColor(enemyColor);
            g.fillOval(x, y, width, height);
        }
    }
}
