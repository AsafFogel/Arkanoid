package game;

/**
 * BallRemover is in charge of removing balls from the game, as well as keeping count
 * of the number of balls that remain.
 */
public class BallRemover implements HitListener {

    private Game game;
    private Counter remainingBalls;

    /**
     * Construct a BallRemover with a given game and a counter for the remaining balls.
     *
     * @param game the game.
     * @param remainingBalls the counter for the remaining balls.
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit the block that is being hit.
     * @param hitter the ball that hit.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {

        hitter.removeFromGame(this.game);

        this.remainingBalls.decrease(1);
    }
}
