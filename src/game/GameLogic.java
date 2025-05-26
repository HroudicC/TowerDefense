package game;

import game.managers.*;
import game.map.MapLoader;
import game.ui.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameLogic {

    private MapLoader mapLoader;

    private WaveManager waveManager;
    private EnemyManager enemyManager;
    private BulletManager bulletManager;
    private TowerManager towerManager;
    private MoneyManager moneyManager;
    private LifeManager lifeManager;
    private GamePanel gamePanel;

    public GameLogic(MapLoader mapLoader, GamePanel gamePanel) {
        this.mapLoader = mapLoader;
        this.gamePanel = gamePanel;

        moneyManager = new MoneyManager();
        lifeManager = new LifeManager(100);
        enemyManager = new EnemyManager(mapLoader, moneyManager, lifeManager, gamePanel);
        waveManager = new WaveManager(enemyManager, 7);
        bulletManager = new BulletManager();
        towerManager = new TowerManager(moneyManager);

    }

    public void update() {
       waveManager.update();
       enemyManager.update();
       towerManager.update(System.currentTimeMillis(), enemyManager, bulletManager);
       bulletManager.update(enemyManager);

       if (lifeManager.isGameOver()){
           System.out.println("KONEC HRY");
           JOptionPane.showMessageDialog(
                   gamePanel,
                   "Prohrál jsi :(",
                   "Prohra",
                   JOptionPane.INFORMATION_MESSAGE);
           System.exit(0);
       }

       if (isVictory()){
           System.out.println("VYHRA");
           JOptionPane.showMessageDialog(
                   gamePanel,
                   "Vyhrál jsi! :)",
                   "Výhra",
                   JOptionPane.INFORMATION_MESSAGE);
           System.exit(0);
       }
    }

    public void startNextWave(){
            waveManager.startNextWave();
            System.out.println("Vlna" + waveManager.getCurrentWaveNumber());
    }

    public boolean isVictory(){
        int totalWaves = waveManager.getTotalWaves();
        int currentWave = waveManager.getCurrentWaveNumber();

        return currentWave >= totalWaves;
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

    public LifeManager getLifeManager() {
        return lifeManager;
    }
}
