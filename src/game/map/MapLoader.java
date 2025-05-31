package game.map;

import game.assets.AssetLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The MapLoader class is responsible for loading and rendering the game map.
 */
public class MapLoader extends JPanel {

    private final int TILE_SIZE = 75;
    private ArrayList<TileType[]> mapByRows = new ArrayList<>();

    private BufferedImage grassTile, startPath, endPath, horizontalPath, verticalPath, leftToDownPath, leftToUpPath, rightToDownPath, rightToUpPath;

    /**
     * Constructs a new MapLoader instance.
     * It loads the map from a file and initializes tile images for rendering.
     */
    public MapLoader() {
        loadMap();
        setOpaque(true);

        grassTile = AssetLoader.loadImage("/game/assets/map/Grass.png");
        startPath = AssetLoader.loadImage("/game/assets/map/PathStart.png");
        endPath = AssetLoader.loadImage("/game/assets/map/EndPath.png");

        horizontalPath = AssetLoader.loadImage("/game/assets/map/PathHorizontal.png");
        verticalPath = AssetLoader.loadImage("/game/assets/map/PathVertical.png");
        leftToDownPath = AssetLoader.loadImage("/game/assets/map/PathLeftToDown.png");
        leftToUpPath = AssetLoader.loadImage("/game/assets/map/PathLeftToUp.png");
        rightToDownPath = AssetLoader.loadImage("/game/assets/map/PathRightToDown.png");
        rightToUpPath = AssetLoader.loadImage("/game/assets/map/PathRightToUp.png");
    }

    /**
     * Loads the map from a file and converts it to an array of tile types.
     *
     * @return true if the map was successfully loaded, false otherwise.
     */
    public boolean loadMap() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/game/map/Map"))))  {

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

    /**
     * Renders the map on the panel.
     *
     * @param g the Graphics context used to drawing.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < mapByRows.size(); row++) {
            TileType[] tileRow = mapByRows.get(row);
            for (int col = 0; col < tileRow.length; col++) {
                TileType type = tileRow[col];
                BufferedImage tileImage = switch (type) {
                    case TileType.GRASS -> grassTile;
                    case TileType.START -> startPath;
                    case TileType.END -> endPath;
                    case TileType.HORIZONTAL_PATH -> horizontalPath;
                    case TileType.VERTICAL_PATH -> verticalPath;
                    case TileType.LEFT_TO_DOWN_PATH -> leftToDownPath;
                    case TileType.LEFT_TO_UP_PATH -> leftToUpPath;
                    case TileType.RIGHT_TO_DOWN_PATH -> rightToDownPath;
                    case TileType.RIGHT_TO_UP_PATH -> rightToUpPath;
                };
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

    /**
     * Gets the tile type at the specified grid coordinates.
     *
     * @param gridX the x-coordinate in the grid.
     * @param gridY the y-coordinate in the grid.
     * @return the tile type at the specified location or null if it out of bounds.
     */
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
