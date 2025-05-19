package game;

import game.managers.*;
import game.map.MapLoader;

import java.awt.*;

public class GameLogic {

    private MapLoader mapLoader;

    private WaveManager waveManager;
    private EnemyManager enemyManager;
    private BulletManager bulletManager;
    private TowerManager towerManager;
    private MoneyManager moneyManager;

    public GameLogic(MapLoader mapLoader) {
        this.mapLoader = mapLoader;
        moneyManager = new MoneyManager();
        enemyManager = new EnemyManager(mapLoader, moneyManager);
        waveManager = new WaveManager(enemyManager);
        bulletManager = new BulletManager();
        towerManager = new TowerManager(moneyManager);

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

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public MoneyManager getMoneyManager() {
        return moneyManager;
    }

    public void setMoneyManager(MoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }
}
