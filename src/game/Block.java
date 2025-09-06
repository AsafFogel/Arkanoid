package game;

import biuoop.DrawSurface;
import shapes.Point;
import shapes.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a block in the game, which is a collidable object.
 */
public class Block implements Collidable, Sprite, HitNotifier {


    private List<HitListener> hitListeners;
    private static final double EPSILON = 0.0000001;
    private Rectangle rectangle;
    private Color color;

    /**
     * Constructs a block with the given rectangle.
     *
     * @param rectangle The collision rectangle associated with the block.
     */
    public Block(Rectangle rectangle) {
        this.rectangle = rectangle;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * Constructs a block with the given rectangle.
     *
     * @param rectangle The collision rectangle associated with the block.
     * @param color     the color of the block.
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * @return the color of the block.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Returns the collision rectangle of this block.
     *
     * @return The collision rectangle.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Calculates the new velocity of an object after a collision.
     *
     * @param collisionPoint  The point of collision.
     * @param currentVelocity The current velocity of the colliding object.
     * @return The new velocity after the collision.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // Check if the object was hit from left or right
        if (Math.abs(collisionPoint.getX() - rectangle.getUpperLeft().getX()) < EPSILON
                || Math.abs(collisionPoint.getX() - (rectangle.getUpperLeft().getX()
                + rectangle.getWidth())) < EPSILON) {
            dx = -dx;
        }
        // Check if the object was hit from bottom or top
        if (Math.abs(collisionPoint.getY() - rectangle.getUpperLeft().getY()) < EPSILON
                || Math.abs(collisionPoint.getY() - (rectangle.getUpperLeft().getY()
                + rectangle.getHeight())) < EPSILON) {
            dy = -dy;
        }
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }
        return new Velocity(dx, dy);
    }


    /**
     * Draws the blocks on the given DrawSurface.
     *
     * @param d the DrawSurface to draw the blocks
     */
    @Override
    public void drawOn(DrawSurface d) {
        if (this.color == null) {
            this.color = Color.BLACK;
        }
        d.setColor(this.color);
        int x = (int) this.rectangle.getUpperLeft().getX();
        int y = (int) this.rectangle.getUpperLeft().getY();
        int width = (int) this.rectangle.getWidth();
        int height = (int) this.rectangle.getHeight();
        d.fillRectangle(x, y, width, height);

        // Draw the border
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, width, height);
    }

    @Override
    public void timePassed() {
    }

    /**
     * This method adds the current object to the game as a sprite and a collidable.
     *
     * @param g the game to which the current object is to be added.
     */
    public void addToGame(Game g) {
        // Add the current object to the game as a sprite
        g.addSprite(this);

        // Add the current object to the game as a collidable
        g.addCollidable(this);
    }

    /**
     * This method checks if the object is a paddle.
     * @return false as the object is not a paddle.
     */
    @Override
    public boolean isPaddle() {
        return false;
    }

    /**
     * This method checks if the color of the ball matches the color of this object.
     * @param ball The ball whose color is to be matched.
     * @return true if the colors match, false otherwise.
     */
    public boolean ballColorMatch(Ball ball) {
        return ball.getColor() == this.color;
    }

    /**
     * This method removes this object from the game.
     * @param game The game from which the object is to be removed.
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }

    /**
     * This method adds a hit listener to the hit listeners list.
     * @param hl The hit listener to be added.
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * This method removes a hit listener from the hit listeners list.
     * @param hl The hit listener to be removed.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * This method notifies all hit listeners when a hit occurs.
     * @param hitter The ball that hit.
     */

    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
