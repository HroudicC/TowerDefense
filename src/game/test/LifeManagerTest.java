package game.test;

import game.managers.LifeManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LifeManagerTest {

    @Test
    void testLifeManagerScenarios() {
        LifeManager managerInitial = new LifeManager(100);
        assertFalse(managerInitial.isGameOver());

        LifeManager managerWithLifeLeft = new LifeManager(100);
        managerWithLifeLeft.loseLife(99);
        assertFalse(managerWithLifeLeft.isGameOver());

        LifeManager managerGameOver = new LifeManager(100);
        managerGameOver.loseLife(101);
        assertTrue(managerGameOver.isGameOver());
    }

}