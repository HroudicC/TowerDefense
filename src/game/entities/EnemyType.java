package game.entities;

public enum EnemyType {

    BASIC("src/game/assets/entities/EnemyBasic.png")
    , SPEED("src/game/assets/entities/EnemySpeed.png")
    , TANK("src/game/assets/entities/EnemyTank.png")
    , PHANTOM("src/game/assets/entities/EnemyPhantom.png")
    , ELITE("src/game/assets/entities/EnemyElite.png");

    private final String imagePath;

    EnemyType(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
