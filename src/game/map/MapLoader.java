package game.map;

import game.assets.AssetLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapLoader extends JPanel {

    private final int TILE_SIZE = 75;
    private ArrayList<TileType[]> mapByRows = new ArrayList<>();

    private BufferedImage grassTile, startPath, endPath, horizontalPath, verticalPath, leftToDownPath, leftToUpPath, rightToDownPath, rightToUpPath;


    public MapLoader() {
        loadMap();
        setOpaque(true);

        grassTile = AssetLoader.loadImage("src/game/assets/map/Grass.png");
        startPath = AssetLoader.loadImage("src/game/assets/map/PathStart.png");
        endPath = AssetLoader.loadImage("src/game/assets/map/PathEnd.png");

        horizontalPath = AssetLoader.loadImage("src/game/assets/map/PathHorizontal.png");
        verticalPath = AssetLoader.loadImage("src/game/assets/map/PathVertical.png");
        leftToDownPath = AssetLoader.loadImage("src/game/assets/map/PathLeftToDown.png");
        leftToUpPath = AssetLoader.loadImage("src/game/assets/map/PathLeftToUp.png");
        rightToDownPath = AssetLoader.loadImage("src/game/assets/map/PathRightToDown.png");
        rightToUpPath = AssetLoader.loadImage("src/game/assets/map/PathRightToUp.png");
    }

    public boolean loadMap() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/game/map/Map"))) {

            String line;
            int lineCounter = 0;
            while ((line = br.readLine()) != null) {
                lineCounter++;
                if (lineCounter == 1) { continue; }

                String[] data = line.split(" ");
                TileType[] row = new TileType[data.length];

                for (int i = 0; i < data.length; i++) {
                    switch (data[i]) {
                        case "0": row[i] = TileType.GRASS; break;
                        case "1": row[i] = TileType.START; break;
                        case "2": row[i] = TileType.END; break;
                        case "3": row[i] = TileType.HORIZONTAL_PATH; break;
                        case "4": row[i] = TileType.VERTICAL_PATH; break;
                        case "5": row[i] = TileType.LEFT_TO_DOWN_PATH; break;
                        case "6": row[i] = TileType.LEFT_TO_UP_PATH; break;
                        case "7": row[i] = TileType.RIGHT_TO_DOWN_PATH; break;
                        case "8": row[i] = TileType.RIGHT_TO_UP_PATH; break;
                        default:  row[i] = TileType.GRASS; break;
                    }
                }
                mapByRows.add(row);
            }
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < mapByRows.size(); row++) {
            TileType[] tileRow = mapByRows.get(row);
            for (int col = 0; col < tileRow.length; col++) {
                TileType type = tileRow[col];
                BufferedImage tileImage = null;
                switch (type) {
                    case TileType.GRASS:
                        tileImage = grassTile;
                        break;
                    case TileType.START:
                        tileImage = startPath;
                        break;
                    case TileType.END:
                        tileImage = endPath;
                        break;
                    case TileType.HORIZONTAL_PATH:
                        tileImage = horizontalPath;
                        break;
                    case TileType.VERTICAL_PATH:
                        tileImage = verticalPath;
                        break;
                    case TileType.LEFT_TO_DOWN_PATH:
                        tileImage = leftToDownPath;
                        break;
                    case TileType.LEFT_TO_UP_PATH:
                        tileImage = leftToUpPath;
                        break;
                    case TileType.RIGHT_TO_DOWN_PATH:
                        tileImage = rightToDownPath;
                        break;
                    case TileType.RIGHT_TO_UP_PATH:
                        tileImage = rightToUpPath;
                        break;
                }
                if(tileImage != null) {
                    g.drawImage(tileImage, col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                } else {
                    g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
                g.setColor(Color.BLACK);
                g.drawRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    public TileType getTileType(int gridX, int gridY) {
        if (gridY < 0 || gridY >= mapByRows.size()) {
            return null;
        }
        TileType[] row = mapByRows.get(gridY);
        if (gridX < 0 || gridX >= row.length) {
            return null;
        }
        return row[gridX];
    }


    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    public ArrayList<TileType[]> getMapByRows() {
        return mapByRows;
    }

}
