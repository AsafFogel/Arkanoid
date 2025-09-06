package game;

import shapes.Point;

/**
 * Represents information about a collision.
 */

public class CollisionInfo {

    private Point collisionPoint;
    private Collidable collidableObject;

    /**
     * Constructs a CollisionInfo object with the given collision point and collidable object.
     *
     * @param collisionPoint The point of collision.
     * @param collidableObject The collidable object involved in the collision.
     */
    public CollisionInfo(Point collisionPoint, Collidable collidableObject) {
        this.collisionPoint = collisionPoint;
        this.collidableObject = collidableObject;
    }

    /**
     * Returns the collision point.
     *
     * @return The collision point.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Returns the collidable object involved in the collision.
     *
     * @return The collidable object.
     */
    public Collidable collisionObject() {
        return this.collidableObject;
    }
}
