package game.entities;

/**
 * The TowerType enum defines the different types of towers available in the game.
 * Each tower type is connected with a specific image path that represents the tower.
 */
public enum TowerType {

    /**
     * Tower types with its associated image asset.
     */
    BASIC("/game/assets/entities/TowerBasic.png")
    , SNIPER("/game/assets/entities/TowerSniper.png")
    , CANNON("/game/assets/entities/TowerCannon.png")
    , LASER("/game/assets/entities/TowerLaser.png")
    , ROCKET("/game/assets/entities/TowerRocket.png")
    , MINIGUN("/game/assets/entities/TowerMinigun.png");

    /**
     * Constructs a TowerType with the specified image path.
     *
     * @param imagePath the asset path for the tower's image.
     */
    TowerType(String imagePath) {
        this.imagePath = imagePath;
    }

    private final String imagePath;

    public String getImagePath() {
        return imagePath;
    }

}
