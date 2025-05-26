package game.managers;

public class MoneyManager {

    private int money;

    public MoneyManager() {
        this.money = 500;
    }

    public void addMoney(int amount) {
        this.money += amount;
    }

    public boolean spendMoney(int amount) {
       if (money == 0) {
           System.out.println("Nemas penize na utraceni!");
           return false;
       } else if (money >= amount) {
           money -= amount;
           return true;
       }
       return false;
    }


    public int getMoney() {
        return money;
    }
}
