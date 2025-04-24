import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class MapLoader extends JPanel {

    private final int TILE_SIZE = 75;
    ArrayList<TileType[]> mapByRows = new ArrayList<>();


    public MapLoader() {
        loadMap();
        setBounds(0,125,1200,750);
        setOpaque(true);
    }


    public boolean loadMap() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/Map"))) {

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
                        case "1": row[i] = TileType.PATH; break;
                        case "2": row[i] = TileType.START; break;
                        case "3": row[i] = TileType.END; break;
                        default:  row[i] = TileType.GRASS; break;
                    }
                }
                mapByRows.add(row);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < mapByRows.size(); row++) {
            TileType[] tileRow = mapByRows.get(row);
            for (int col = 0; col < tileRow.length; col++) {
                TileType type = tileRow[col];
                switch (type) {
                    case GRASS:
                        g.setColor(Color.GREEN);
                        break;
                    case PATH:
                        g.setColor(new Color(84, 89, 90));
                        break;
                    case START:
                        g.setColor(Color.BLUE);
                        break;
                    case END:
                        g.setColor(Color.RED);
                        break;
                }
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        System.out.println("Map is loaded");
    }
}
