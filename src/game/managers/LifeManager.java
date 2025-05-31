package game.managers;

/**
 * The LifeManager class manages the player's lives in the game.
 */
public class LifeManager {

    private int lives;

    /**
     * Constructs a new LifeManager with the predefined initial number of lives.
     *
     * @param initialLives the starting number of lives.
     */
    public LifeManager(int initialLives) {
        this.lives = initialLives;
    }

    /**
     * Checks if the game is over.
     * The game is over if the number of lives has reached 0.
     *
     * @return true if lives equals 0.
     */
    public boolean isGameOver() {
        return lives <= 0;
    }

    /**
     * Reduces the player's lives by the specified amount.
     * If the resulting number of lives is less than or equal to zero, the lives are set to 0.
     *
     * @param amount the number of lives to lose.
     */
    public void loseLife(int amount) {
        if (amount <= 0) {
            return;
        }
        lives -= amount;
        if (lives < 0) {
            lives = 0;
        }
    }


    public int getLives() {
        return lives;
    }
}
