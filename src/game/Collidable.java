package game;

import shapes.Point;
import shapes.Rectangle;

/**
 * Represents a collidable object in the game.
 */
public interface Collidable {
        /**
         * Returns the collision rectangle of this collidable.
         *
         * @return The collision rectangle.
         */
        Rectangle getCollisionRectangle();

        /**
         * This method calculates the new velocity of the ball after it hits.
         * @param hitter The ball that hits.
         * @param collisionPoint The point at which the ball collides.
         * @param currentVelocity The current velocity of the ball.
         * @return The new velocity of the ball after the hit.
         */
        Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

        /**
         * This method checks if the object is a paddle.
         * @return false as the object is not a paddle.
         */
        boolean isPaddle();

}
