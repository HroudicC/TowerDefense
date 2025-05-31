package game.config;

import java.io.*;
import java.util.HashMap;

/**
 * The TowerConfigLoader class is responsible for loading tower configuration data from a file.
 * It reads a semicolon ';' separated file where each row (except the first) represents configuration settings for a tower.
 * The loaded configurations are stored in a HashMap, keyed by the tower type.
 */
public class TowerConfigLoader {

    /**
     * Loads tower configurations from the specified resource path.
     * The file is expected to have at least 6 columns separated by semicolons ';': towerType;cost;range;damage;cooldown;description.
     *  The first line of the file is skipped (for file information).
     *
     * @param resourcePath the path to the file.
     * @return a HashMap where the key is the tower type and the value is the corresponding TowerInfo.
     */
    public static HashMap<String, TowerInfo> loadTowerConfigs (String resourcePath){

        HashMap<String, TowerInfo> towerConfigMap = new HashMap<>();
        try (InputStream is = TowerConfigLoader.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                return towerConfigMap;
            }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                String line;
                int lineCounter = 0;
                while ((line = reader.readLine()) != null) {
                    lineCounter++;
                    if (lineCounter == 1) {
                        continue;
                    }

                    String[] parts = line.split(";");
                    if (parts.length >= 6) {
                        TowerInfo info = new TowerInfo();
                        info.setTowerType(parts[0].trim());
                        info.setCost(Integer.parseInt(parts[1].trim()));
                        info.setRange(Integer.parseInt(parts[2].trim()));
                        info.setDamage(Integer.parseInt(parts[3].trim()));
                        info.setCooldown(Integer.parseInt(parts[4].trim()));
                        info.setDescription(parts[5].trim());

                        towerConfigMap.put(info.getTowerType(), info);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return towerConfigMap;
    }
}
