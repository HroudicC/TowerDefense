package game.test;

import game.GameLogic;
import game.managers.WaveManager;
import game.map.MapLoader;
import game.ui.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WaveManagerTest {

    private WaveManager waveManager;
    private GameLogic gameLogic;
    private MapLoader mapLoader;
    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        mapLoader = mock(MapLoader.class);
        gamePanel = mock(GamePanel.class);

        gameLogic = new GameLogic(mapLoader, gamePanel);
        waveManager = gameLogic.getWaveManager();
    }

    @Test
    void testInitialWaveState() {
        assertEquals(1, waveManager.getCurrentWaveNumber());
        assertEquals(0, waveManager.getCurrentWaveIndex());
        assertEquals(10, waveManager.getTotalWaves());
    }

    @Test
    void testFinishWave() {
        waveManager.setTotalWaves(10);
        waveManager.setCurrentWaveIndex(9);
        waveManager.startNextWave();

        assertTrue(gameLogic.isVictory());
    }

}
