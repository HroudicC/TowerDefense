package game.managers;

import game.entities.Wave;
import game.entities.EnemyType;

import java.util.ArrayList;

/**
 * The WaveManager class manages enemy waves in the game.
 * It upholds the wave list, updates the current wave based on timing, and spawns enemies using the EnemyManager.
 * It also controls when a new wave is started.
 */
public class WaveManager {

    private ArrayList<Wave> waves;
    private int currentWaveIndex, totalWaves;
    private long lastSpawnTime;
    private EnemyManager enemyManager;

    private boolean waitingForNextWave = false;
    private boolean waveActive = false;

    /**
     * Constructs a new WaveManager.
     *
     * @param enemyManager the enemyManager that handles enemy actions.
     * @param totalWaves the total number of waves in the game.
     */
    public WaveManager(EnemyManager enemyManager, int totalWaves) {
        this.enemyManager = enemyManager;
        waves = new ArrayList<>();
        currentWaveIndex = 0;
        lastSpawnTime = System.currentTimeMillis();

        initialize();
        this.totalWaves = totalWaves;
    }

    /**
     * Updates the current wave.
     * This method checks if the current wave is active, and if so, spawns enemies based on the spawn interval.
     * When all enemies are spawned and cleared, it will end the current wave and prepare for the next one.
     */
    public void update() {
        if (!waveActive) return;

        if (currentWaveIndex >= waves.size()) {
            System.out.println("All waves are finished.");
            return;
        }

        if (waitingForNextWave) return;

        Wave currentWave = waves.get(getCurrentWaveIndex());
        long currentTime = System.currentTimeMillis();

        if (currentWave.canSpawn() && currentTime - lastSpawnTime >= currentWave.getSpawnInterval()) {
            enemyManager.spawnEnemy(currentWave.getEnemyType());
            currentWave.enemySpawned();
            lastSpawnTime = currentTime;
        }

        if (currentWave.isFinished() && enemyManager.getEnemies().isEmpty()) {
            waveActive = false;
            waitingForNextWave = true;
            System.out.println("Wave " + getCurrentWaveNumber() + " finished.");
            currentWaveIndex++;
        }

    }

    /**
     * Initializes the list of waves with predefined enemy configurations.
     * Each Wave is created with a specific enemy type, count, and spawn interval.
     */
    private void initialize(){
        waves.add(new Wave(5, EnemyType.BASIC, 1200));
        waves.add(new Wave(8,EnemyType.BASIC, 700));
        waves.add(new Wave(8,EnemyType.SPEED, 800));
        waves.add(new Wave(10,EnemyType.SPEED, 500));
        waves.add(new Wave(5, EnemyType.TANK, 1500));
        waves.add(new Wave(8, EnemyType.TANK, 800));
        waves.add(new Wave(5, EnemyType.PHANTOM, 1500));
        waves.add(new Wave(10, EnemyType.PHANTOM, 1000));
        waves.add(new Wave(15,EnemyType.ELITE, 700));
    }

    /**
     * Starts the next wave if the current wave is inactive and there are remaining waves.
     * Resets the spawn timer and sets the wave to active.
     */
    public void startNextWave() {
        if (!waveActive && currentWaveIndex < waves.size()) {
            waveActive = true;
            waitingForNextWave = false;
            lastSpawnTime = System.currentTimeMillis();
            System.out.println("Starting wave: " + getCurrentWaveNumber());
        }
    }

    public int getCurrentWaveNumber() {
        return currentWaveIndex + 1;
    }

    public int getCurrentWaveIndex() {
        return currentWaveIndex;
    }

    public int getTotalWaves() {
        return totalWaves;
    }

    public void setTotalWaves(int totalWaves) {
        this.totalWaves = totalWaves;
    }

    public void setCurrentWaveIndex(int currentWaveIndex) {
        this.currentWaveIndex = currentWaveIndex;
    }
}
