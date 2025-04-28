package game.entities;

public class Wave {

    private int totalEnemies;
    private int spawnedEnemies;
    private EnemyType enemyType;
    private int spawnInterval;

    public Wave(int totalEnemies, EnemyType enemyType, int spawnInterval) {
        this.totalEnemies = totalEnemies;
        this.spawnedEnemies = spawnedEnemies;
        this.enemyType = enemyType;
        this.spawnInterval = spawnInterval;
    }

    public boolean canSpawn() {
        return spawnedEnemies < totalEnemies;
    }

    public void enemySpawned() {
        spawnedEnemies++;
    }

    public boolean isFinished() {
        return spawnedEnemies >= totalEnemies;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public int getSpawnInterval() {
        return spawnInterval;
    }
}
