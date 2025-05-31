package game.test;

import game.GameLogic;
import game.map.MapLoader;
import game.ui.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameLogicTest {

    private MapLoader mapLoader;
    private GamePanel gamePanel;
    private GameLogic gameLogic;


    @BeforeEach
    void setUp() {
        mapLoader = mock(MapLoader.class);
        gamePanel = mock(GamePanel.class);
        gameLogic = new GameLogic(mapLoader, gamePanel);
    }

    @Test
    void testNormalUpdateDoesNotFinishGame() {
        gameLogic.update();
        assertFalse(gameLogic.isVictory());
        assertFalse(gameLogic.getLifeManager().isGameOver());
    }


}