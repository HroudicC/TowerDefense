package game.managers;

/**
 * The MoneyManager class manages the in-game currency.
 * It provides methods to add money, spend money, and check the current balance.
 */
public class MoneyManager {

    private int money;

    /**
     * Constructs a new MoneyManager.
     *
     * @param initialMoney initial amount of money.
     */
    public MoneyManager(int initialMoney) {
        this.money = initialMoney;
    }

    /**
     * Adds the specified amount to the current money balance.
     *
     * @param amount the amount of money to add.
     */
    public boolean addMoney(int amount) {
        if (amount > 0) {
            this.money += amount;
            return true;
        }
        return false;
    }

    /**
     * This method tries to spend the specified amount of money.
     * If the current balance is enough, the amount is deducted and the method returns true.
     * If not enough money is available, returns false.
     *
     * @param amount the amount od money to spend.
     * @return true if the money was successfully spent, false otherwise.
     */
    public boolean spendMoney(int amount) {
        if (amount <= 0 || money < amount) {
            return false;
        }
        money -= amount;
        return true;
    }


    public int getMoney() {
        return money;
    }
}
