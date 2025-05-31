package game.entities;

/**
 * The EnemyType enum defines the different types of enemies available in the game.
 * Each enemy type is connected with a specific image path that represents the enemy.
 */
public enum EnemyType {

    /**
     * Enemy types with its associated image asset.
     */
    BASIC("/game/assets/entities/EnemyBasic.png")
    , SPEED("/game/assets/entities/EnemySpeed.png")
    , TANK("/game/assets/entities/EnemyTank.png")
    , PHANTOM("/game/assets/entities/EnemyPhantom.png")
    , ELITE("/game/assets/entities/EnemyElite.png");

    private final String imagePath;

    /**
     * Constructs an EnemyType with the specified image path.
     *
     * @param imagePath the asset path for the enemy's image.
     */
    EnemyType(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
