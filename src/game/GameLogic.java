package game;

import game.managers.BulletManager;
import game.managers.EnemyManager;
import game.managers.TowerManager;
import game.managers.WaveManager;
import game.map.MapLoader;

import java.awt.*;

public class GameLogic {

    private MapLoader mapLoader;

    private WaveManager waveManager;
    private EnemyManager enemyManager;
    private BulletManager bulletManager;
    private TowerManager towerManager;

    public GameLogic(MapLoader mapLoader) {
        this.mapLoader = mapLoader;
        enemyManager = new EnemyManager(mapLoader);
        waveManager = new WaveManager(enemyManager);
        bulletManager = new BulletManager();
        towerManager = new TowerManager();

    }

    public void update() {
       waveManager.update();
       enemyManager.update();
       towerManager.update(System.currentTimeMillis(), enemyManager, bulletManager);
       bulletManager.update(enemyManager);
    }

    public void startNextWave(){
            waveManager.startNextWave();
            System.out.println("Vlna" + waveManager.getCurrentWaveNumber());
    }

    public void draw(Graphics g) {
        mapLoader.paintComponent(g);
        enemyManager.draw(g);
        towerManager.draw(g);
        bulletManager.draw(g);
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }
}
