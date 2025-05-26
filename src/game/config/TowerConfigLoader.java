package game.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class TowerConfigLoader {

    public static HashMap<String, TowerInfo> loadTowerConfigs (String filePath) {

        HashMap<String, TowerInfo> towerConfigMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineCounter = 0;
            while ((line = reader.readLine()) != null) {
                lineCounter++;
                if (lineCounter == 1) { continue; }

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return towerConfigMap;
    }
}
