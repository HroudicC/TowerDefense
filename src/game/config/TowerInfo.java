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

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
