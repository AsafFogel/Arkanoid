package game;

/**
 * ScoreTrackingListener is a HitListener that updates the game score when blocks are hit.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructor for arkanoid.Game.arkanoid.ScoreTrackingListener.
     *
     * @param counter the counter that keeps track of the current score
     */
    public ScoreTrackingListener(Counter counter) {
        this.currentScore = counter;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * Increases the score by 5 points.
     *
     * @param beingHit the block that is being hit
     * @param hitter   the ball that is hitting the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
    }
}
