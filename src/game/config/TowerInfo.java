package game.config;

public class TowerInfo {

    private String towerType;
    private int cost;
    private int range;
    private int damage;
    private int cooldown;
    private String description;

    public String getInfo(){
        return description + "\n"
                + "Cost: " + cost + "$" +"\n"
                + "Range: " + range + "\n"
                + "Damage: " + damage + "\n"
                + "Cooldown: " + cooldown + " ms";
    }

    public String getTowerType() {
        return towerType;
    }

    public void setTowerType(String towerType) {
        this.towerType = towerType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
