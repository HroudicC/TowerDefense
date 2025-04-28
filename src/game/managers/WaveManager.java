package game.managers;

import game.entities.Wave;
import game.entities.EnemyType;

import java.util.ArrayList;

public class WaveManager {

    private ArrayList<Wave> waves;
    private int currentWaveIndex;
    private long lastSpawnTime;
    private EnemyManager enemyManager;


    public WaveManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
        waves = new ArrayList<>();
        currentWaveIndex = 0;

        initialize();
    }

    public void update() {

        if (currentWaveIndex >= waves.size()) {
            System.out.println("Vsechny vlny jsou dokoncene");
            return;
        }

        Wave currentWave = waves.get(getCurrentWaveIndex());
        long currentTime = System.currentTimeMillis();

        //Spawn nepratel podle intervalu v dane vlne
        if (currentWave.canSpawn() && currentTime - lastSpawnTime >= currentWave.getSpawnInterval()) {
            enemyManager.spawnEnemy(currentWave.getEnemyType());
            currentWave.enemySpawned();
            lastSpawnTime = currentTime;
        }

        if (currentWave.isFinished()){
           currentWaveIndex++;
            System.out.println("Vlna " + currentWaveIndex + " dokoncena");
        }

    }

    private void initialize(){
        waves.add(new Wave(5, EnemyType.BASIC, 2000));
        waves.add(new Wave(10,EnemyType.BASIC, 1800));
        waves.add(new Wave(7,EnemyType.SPEED, 2000));
        waves.add(new Wave(10, EnemyType.SPEED, 1800));
    }

    public int getCurrentWaveNumber() {
        return currentWaveIndex + 1;
    }

    public ArrayList<Wave> getWaves() {
        return waves;
    }

    public void setWaves(ArrayList<Wave> waves) {
        this.waves = waves;
    }

    public int getCurrentWaveIndex() {
        return currentWaveIndex;
    }

    public void setCurrentWaveIndex(int currentWaveIndex) {
        this.currentWaveIndex = currentWaveIndex;
    }

    public long getLastSpawnTime() {
        return lastSpawnTime;
    }

    public void setLastSpawnTime(long lastSpawnTime) {
        this.lastSpawnTime = lastSpawnTime;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void setEnemyManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }
}
