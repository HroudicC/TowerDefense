import java.awt.*;
import java.util.ArrayList;

public class Enemy extends AbstractEntity {
    private int speed;
    private int health;
    private int pathIndex = 0;
    private ArrayList<Point> path = new ArrayList<>();

    private MapLoader mapLoader;
    private boolean pathCalculated = false;

    public Enemy(int x, int y, int width, int height, int speed, int health, MapLoader mapLoader) {
        super(x, y, width, height);
        this.speed = speed;
        this.health = health;
        this.mapLoader = mapLoader;
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

        if (tile != TileType.PATH && tile != TileType.START && tile != TileType.END) {
            return false;
        }
        visited[y][x] = true;
        path.add(new Point(x, y));

        if (new Point(x, y).equals(end)) {
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
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= speed) {
                x = targetX;
                y = targetY;
                pathIndex++; // Posun na další bod cesty
            } else {
                x += (int) ((dx / distance) * speed);
                y += (int) ((dy / distance) * speed);
            }

            if (pathIndex >= path.size()) {
                System.out.println("Nepřítel dorazil do cíle!");

            }
        }
        return path;
    }


    public boolean hasReachedEnd() {
        return pathCalculated && pathIndex >= path.size();
    }


    @Override
    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillOval(x, y, getWidth(), getHeight());
    }
}
