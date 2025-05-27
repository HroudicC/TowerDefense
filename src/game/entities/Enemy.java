package game.entities;

import game.assets.AssetLoader;
import game.map.MapLoader;
import game.map.TileType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enemy extends AbstractEntity {

    private int speed, health, moneyReward;
    private int pathIndex = 0;
    private ArrayList<Point> path = new ArrayList<>();
    private Color enemyColor;

    private MapLoader mapLoader;
    private boolean pathCalculated = false;
    private Direction movementDirection;

    private BufferedImage enemyImage;
    private EnemyType enemyType;

    public Enemy(int x, int y, int width, int height, int speed, int health, EnemyType enemyType, Color enemyColor, int moneyReward, MapLoader mapLoader) {
        super(x, y, width, height);
        this.speed = speed;
        this.health = health;
        this.enemyType = enemyType;
        this.enemyColor = enemyColor;
        this.moneyReward = moneyReward;
        this.mapLoader = mapLoader;

        enemyImage = AssetLoader.loadImage(enemyType.getImagePath());
    }

    /**
     * DFS metoda pro nalezení cesty od startu k cíli.
     * Prochází čtyři směry a při nalezení správné cesty vrací true.
     * Made by ChatGPT
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

        // Pokud cesta nevede k cíli, odebereme aktuální bod a vracíme false
        path.remove(path.size() - 1);
        return false;
    }

    /**
     * Metoda pro výpočet cesty. Najde bod START a END v mapě a zavolá DFS.
     * Po úspěšném výpočtu nastaví počáteční pozici nepřítele.
     * Made by ChatGPT
     */
    private void calculatePath() {
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
                // Nastavení počáteční pozice nepřítele v pixelech
                this.x = start.x * mapLoader.getTILE_SIZE();
                this.y = start.y * mapLoader.getTILE_SIZE();
            } else {
                System.out.println("Cesta nebyla nalezena!");
            }
        } else {
            System.out.println("Start nebo End bod nebyl nalezen!");
        }
    }


    @Override
    public ArrayList<Point> update() {

        if (!pathCalculated) {
            calculatePath();
        }

        if (pathCalculated && pathIndex < path.size()) {
            Point targetTile = path.get(pathIndex);
            int targetX = targetTile.x * mapLoader.getTILE_SIZE();
            int targetY = targetTile.y * mapLoader.getTILE_SIZE();
            int dx = targetX - x;
            int dy = targetY - y;
            double distance = Math.sqrt(dx * dx + dy * dy); //Vypocitani vzdalenosti mezi enemy a vezi

            if (distance <= speed) {
                x = targetX;
                y = targetY;
                pathIndex++; // posune na dalsi bod cesty
            } else {
                x += (int) ((dx / distance) * speed);
                y += (int) ((dy / distance) * speed);
            }

            if (pathIndex < path.size() - 1) {
                Point current = path.get(pathIndex);
                Point next = path.get(pathIndex + 1);
                int diffX = next.x - current.x;
                int diffY = next.y - current.y;

                //Porovnani rozdilu X a Y kvuli pohybu
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (diffX > 0) {
                        movementDirection = Direction.RIGHT;
                    } else {
                        movementDirection = Direction.LEFT;
                    }
                } else {
                    if (diffY > 0) {
                        movementDirection = Direction.DOWN;
                    } else {
                        movementDirection = Direction.UP;
                    }
                }

            }
        }
        return path;
    }

    public void takeDamage(int damage) {
        this.health -= damage;

        System.out.println("Enemy dostal " + damage + " damage. Zbyvajici zivoty: " + health);

        if(this.health <= 0) {
            this.health = 0;
            System.out.println("Enemy je mrtvi. Odmena: " + moneyReward + "$.");
        }
    }


    public boolean hasReachedEnd() {
        return pathCalculated && pathIndex >= path.size();
    }

    public boolean isDead(){
       return health <= 0;
    }

    public int getMoneyReward() {
        return moneyReward;
    }

    @Override
    public void draw(Graphics g) {
        if (enemyImage != null) {
            g.drawImage(enemyImage,x,y,null);
        }else{
            g.setColor(enemyColor);
            g.fillOval(x, y, getWidth(), getHeight());
        }
    }
}
