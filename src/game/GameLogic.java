package game;

import game.entities.Enemy;
import game.managers.EnemyManager;
import game.managers.WaveManager;
import game.map.MapLoader;

import java.awt.*;
import java.util.ArrayList;

public class GameLogic {

    private MapLoader mapLoader;
    private ArrayList<Enemy> enemies;

    private WaveManager waveManager;
    private EnemyManager enemyManager;

    public GameLogic(MapLoader mapLoader) {
        this.mapLoader = mapLoader;
        enemyManager = new EnemyManager(mapLoader);
        waveManager = new WaveManager(enemyManager);

        enemies = new ArrayList<>();
    }

    public void update() {
       waveManager.update();
       enemyManager.update();
    }

    public void startNextWave(){
        if (waveManager.isWaitingForNextWave()){
            waveManager.startNextWave();
            System.out.println("Vlna" + waveManager.getCurrentWaveNumber());
        }
    }

    public void draw(Graphics g) {
        enemyManager.draw(g);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
