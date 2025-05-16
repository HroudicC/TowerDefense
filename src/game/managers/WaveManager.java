package game.managers;

import game.entities.Wave;
import game.entities.EnemyType;

import java.util.ArrayList;

public class WaveManager {

    private ArrayList<Wave> waves;
    private int currentWaveIndex;
    private long lastSpawnTime;
    private EnemyManager enemyManager;

    private boolean waitingForNextWave = false;
    private boolean waveActive = false;


    public WaveManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
        waves = new ArrayList<>();
        currentWaveIndex = 0;
        lastSpawnTime = System.currentTimeMillis();

        initialize();
    }

    public void update() {
        if (!waveActive) return;

        if (currentWaveIndex >= waves.size()) {
            System.out.println("Vsechny vlny jsou dokoncene");
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
            System.out.println("Vlna " + (currentWaveIndex + 1) + " dokoncena");
            currentWaveIndex++;
        }

    }

    private void initialize(){
        waves.add(new Wave(5, EnemyType.BASIC, 2000));
        waves.add(new Wave(10,EnemyType.SPEED, 1800));
        waves.add(new Wave(7,EnemyType.TANK, 2000));
        waves.add(new Wave(10, EnemyType.SPEED, 1800));
    }

    public void startNextWave() {
        if (!waveActive && currentWaveIndex < waves.size()) {
            waveActive = true;
            waitingForNextWave = false;
            lastSpawnTime = System.currentTimeMillis();
            System.out.println("Spouštím vlnu " + (currentWaveIndex + 1));        }
    }

    public int getCurrentWaveNumber() {
        return currentWaveIndex + 1;
    }

    public int getCurrentWaveIndex() {
        return currentWaveIndex;
    }
}
