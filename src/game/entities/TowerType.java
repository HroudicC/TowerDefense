package game.entities;

public enum TowerType {

    BASIC("src/game/assets/entities/TowerBasic.png")
    , SNIPER("src/game/assets/entities/TowerSniper.png")
    , CANNON("src/game/assets/entities/TowerCannon.png")
    , LASER("src/game/assets/entities/TowerLaser.png")
    , ROCKET("src/game/assets/entities/TowerRocket.png")
    , MINIGUN("src/game/assets/entities/TowerMinigun.png");

    private final String imagePath;

    TowerType(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

}
