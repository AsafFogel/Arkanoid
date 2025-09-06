package game;


/**
 * BlockRemover is a HitListener that is in charge of removing blocks from the game,
 * as well as keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * Constructor for BlockRemover.
     *
     * @param game the game instance from which blocks should be removed
     * @param remainingBlocks the counter for the number of remaining blocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
            this.game = game;
            this.remainingBlocks = remainingBlocks;
    }
    /**
     * Blocks that are hit should be removed from the game.
     * This method removes the block from the game, removes this listener from the block,
     * and decreases the count of remaining blocks. Additionally, it changes the ball's color
     * to the color of the block that was hit.
     *
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit the block
     */

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {

        beingHit.removeFromGame(this.game);

        beingHit.removeHitListener(this);

        this.remainingBlocks.decrease(1);
        // Change the ball's color to the block's color
        hitter.setColor(beingHit.getColor());
    }
}
