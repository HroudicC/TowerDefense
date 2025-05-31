package game.test;

import game.managers.MoneyManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyManagerTest {

    private MoneyManager moneyManager;

    @BeforeEach
    void setUp() {
        moneyManager = new MoneyManager(0);
    }

    @Test
    void testMoneyOperations() {
        assertTrue(moneyManager.addMoney(100));
        assertEquals(100, moneyManager.getMoney());

        assertTrue(moneyManager.spendMoney(50));
        assertEquals(50, moneyManager.getMoney());

        assertFalse(moneyManager.spendMoney(60));
        assertEquals(50, moneyManager.getMoney());
    }
}