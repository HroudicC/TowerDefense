package game.managers;

public class LifeManager {

    private int lives;

    public LifeManager(int initialLives) {
        this.lives = initialLives;
    }


    public boolean isGameOver() {
        return lives == 0;
    }

    public void loseLife(int amount) {
        lives -= amount;
        if (lives <= 0) {
            lives = 0;
        }
    }

    public int getLives() {
        return lives;
    }
}
