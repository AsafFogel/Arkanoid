package game;

import shapes.Line;
import shapes.Point;


/**
 * Represents the game environment that manages collidable objects.
 */
public class GameEnvironment {

    private java.util.List<Collidable> collidables;

    /**
     * Constructs a new game environment with an empty list of collidables.
     */
    public GameEnvironment() {
        this.collidables = new java.util.ArrayList<>();
    }

    /**
     * Constructs a GameEnvironment with the given list of collidable objects.
     *
     * @param c The list of collidable objects.
     */
    public GameEnvironment(java.util.List<Collidable> c) {
        this.collidables = c;
    }

    /**
     * Adds a collidable object to the environment.
     *
     * @param c The collidable object to add.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Removes a collidable object from the list of collidables.
     *
     * @param c the collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Finds the closest collision point between a trajectory and the collidables.
     *
     * @param trajectory The trajectory (line) to check for collisions.
     * @return A CollisionInfo object containing information about the closest collision,
     * or null if no collision occurs.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestCollisionPoint = null;
        Collidable closestCollidable = null;
        double closestDistance = -1;

        for (Collidable collidable : collidables) {
            Point collisionP = trajectory.closestIntersectionToStartOfLine(collidable.getCollisionRectangle());
            if (collisionP != null) {
                double d = trajectory.start().distance(collisionP);
                if (d > closestDistance || closestDistance == -1) {
                    closestDistance = d;
                    closestCollisionPoint = collisionP;
                    closestCollidable = collidable;
                }
            }
        }
        if (closestCollidable == null) {
            return null;
        }
        return new CollisionInfo(closestCollisionPoint, closestCollidable);
    }
}