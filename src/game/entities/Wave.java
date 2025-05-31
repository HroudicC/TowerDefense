package game.entities;

/**
 * The Wave class represents single enemy wave in the game.
 * It manages the state of enemy spawning by keeping track of the total number of enemies to spawn,
 * the number of enemies already spawned, the type of enemy for the wave, and the time interval between spawns.
 */
public class Wave {

    private int totalEnemies;
    private int spawnedEnemies;
    private EnemyType enemyType;
    private int spawnInterval;

    /**
     * Constructs a new Wave with the specified total number of enemies, enemy type, and spawn interval.
     *
     * @param totalEnemies the total number of enemies to spawn in this wave.
     * @param enemyType the type of enemy to be spawn in this wave.
     * @param spawnInterval the time interval in milliseconds between spawning enemies.
     */
    public Wave(int totalEnemies, EnemyType enemyType, int spawnInterval) {
        this.totalEnemies = totalEnemies;
        this.spawnedEnemies = 0;
        this.enemyType = enemyType;
        this.spawnInterval = spawnInterval;
    }

    /**
     * Check if any more enemies can spawn in the current wave.
     *
     * @return true if the number of spawned enemies is less than the total required, false otherwise.
     */
    public boolean canSpawn() {
        return spawnedEnemies < totalEnemies;
    }

    /**
     * Increase the number of enemies spawned.
     */
    public void enemySpawned() {
        spawnedEnemies++;
    }

    /**
     * Determines if the current wave is finished.
     *
     * @return true if the total number of enemies spawned is more or equal to the total number requested, false otherwise.
     */
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
