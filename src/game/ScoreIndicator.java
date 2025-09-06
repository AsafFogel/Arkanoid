package game;

import biuoop.DrawSurface;

/**
 * arkanoid.Game.arkanoid.ScoreIndicator class is in charge of displaying the current score.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
    * Construct a arkanoid.Game.arkanoid.ScoreIndicator with a given score counter.
    *
    * @param score the score counter.
    */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * Get the current score.
     *
     * @return the current score.
     */
    public int getScore() {
        return this.score.getValue();
    }

    /**
     * Draw the score on the given DrawSurface.
     *
     * @param d the surface to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.drawText(350, 20, "Score: " + this.score.getValue(), 16);
    }
    /**
     * Handle the passage of time.
     */
    @Override
    public void timePassed() {
    }
    /**
     * Add this arkanoid.Game.arkanoid.ScoreIndicator to a game.
     *
     * @param g the game.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}
