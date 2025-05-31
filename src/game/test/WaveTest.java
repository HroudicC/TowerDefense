package game.test;

import game.entities.EnemyType;
import game.entities.Wave;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaveTest {

    private Wave wave;

    @BeforeEach
    void setUp() {
        wave = new Wave(5, EnemyType.BASIC, 500);
    }

    @Test
    void testInitialState() {
        assertTrue(wave.canSpawn());
        assertFalse(wave.isFinished());
    }

    @Test
    void testSpawning() {
        wave.enemySpawned();
        wave.enemySpawned();
        wave.enemySpawned();

        assertTrue(wave.canSpawn());
        assertFalse(wave.isFinished());

        wave.enemySpawned();
        wave.enemySpawned();

        assertFalse(wave.canSpawn());
        assertTrue(wave.isFinished());
    }

    @Test
    void testWaveSpawningWithDelay() throws InterruptedException {
        int totalEnemies = 5;
        int spawnCount = 0;

        while (wave.canSpawn()) {
            Thread.sleep(wave.getSpawnInterval());
            wave.enemySpawned();
            spawnCount++;
        }

        assertEquals(totalEnemies, spawnCount);
        assertTrue(wave.isFinished());
    }


    @Test
    void testEmptyWave() {
        Wave emptyWave = new Wave(0, EnemyType.BASIC, 500);

        assertFalse(emptyWave.canSpawn());
        assertTrue(emptyWave.isFinished());
    }
}