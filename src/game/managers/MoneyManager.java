package game.managers;

public class MoneyManager {

    private int money;

    public MoneyManager() {
        this.money = 100;
    }

    public void addMoney(int amount) {
        this.money += amount;
    }

    public boolean spendMoney(int amount) {
       if (money == 0) {
           System.out.println("Nemas ani na jidlo, nemuzes utracet!.");
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

    public void setMoney(int money) {
        this.money = money;
    }
}
