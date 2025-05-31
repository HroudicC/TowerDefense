package game;

import game.managers.*;
import game.map.MapLoader;
import game.ui.GamePanel;

import javax.swing.*;
import java.awt.*;

/**
 * The GameLogic class handles all game operations together.
 * Updates enemy waves, enemy movements, tower actions and shot trajectories,
 * and controls the end of the game.
 * It also triggers the next wave and performs all drawing procedures.
 */
public class GameLogic {

    private MapLoader mapLoader;

    private WaveManager waveManager;
    private EnemyManager enemyManager;
    private BulletManager bulletManager;
    private TowerManager towerManager;
    private MoneyManager moneyManager;
    private LifeManager lifeManager;
    private GamePanel gamePanel;

    /**
     * Construct a new GameLogic instance.
     *
     * @param mapLoader the MapLoader used to load and display the game map.
     * @param gamePanel the GamePanel where the game is rendered.
     */
    public GameLogic(MapLoader mapLoader, GamePanel gamePanel) {
        this.mapLoader = mapLoader;
        this.gamePanel = gamePanel;

        moneyManager = new MoneyManager(500);
        lifeManager = new LifeManager(100);
        enemyManager = new EnemyManager(mapLoader, moneyManager, lifeManager, gamePanel);
        waveManager = new WaveManager(enemyManager, 10);
        bulletManager = new BulletManager();
        towerManager = new TowerManager(moneyManager);

    }

    /**
     * Updates the game state.
     * This method updates waves, enemies, towers, and bullets.
     * It checks for game over and victory conditions.
     * When one of these conditions is met, a pop-up window is shown and the application exits.
     */
    public void update() {
       waveManager.update();
       enemyManager.update();
       towerManager.update(System.currentTimeMillis(), enemyManager, bulletManager);
       bulletManager.update(enemyManager);

       if (lifeManager.isGameOver()){
           System.out.println("GAME OVER");
           JOptionPane.showMessageDialog(
                   gamePanel,
                   "You lost :(",
                   "GAME OVER",
                   JOptionPane.INFORMATION_MESSAGE);
           System.exit(0);
       }

       if (isVictory()){
           System.out.println("VICTORY");
           JOptionPane.showMessageDialog(
                   gamePanel,
                   "You won :)",
                   "VICTORY",
                   JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
       }
    }

    /**
     * Starts the next wave of enemies.
     */
    public void startNextWave(){
            waveManager.startNextWave();
    }

    /**
     * Checks whether the victory condition has been achieved.
     * Victory is achieved when the current wave count is more or equal to the total wave count.
     *
     * @return true if the victory condition is achieved, false otherwise.
     */
    public boolean isVictory(){
        int totalWaves = waveManager.getTotalWaves();
        int currentWave = waveManager.getCurrentWaveNumber();

        return currentWave >= totalWaves;
    }

    /**
     * Draws all the game components on the screen.
     *
     * @param g the Graphics context used for drawing the game components
     */
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
