package game;

import shapes.Point;

/**
 * The Velocity class represents a velocity with horizontal and vertical components (dx and dy).
 * It includes methods to create a velocity from an angle and speed, apply the velocity to a point,
 * and retrieve the velocity components.
 */
public class Velocity {

    private double dx; // Horizontal component of the velocity
    private double dy; // Vertical component of the velocity

    /**
    * Constructs a Velocity with the specified components.
    *
    * @param dx the horizontal component of the velocity
    * @param dy the vertical component of the velocity
    */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    /**
     * Creates a Velocity from an angle and speed.
     *
     * @param angle the angle in degrees
     * @param speed the speed
     * @return the new arkanoid.Game.arkanoid.Velocity object
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radians = Math.toRadians(angle); // Convert angle to radians
        double dx = speed * Math.sin(radians);
        double dy = -speed * Math.cos(radians);
        return new Velocity(dx, dy);
    }
    /**
     * Applies this velocity to a given point, resulting in a new point.
     *
     * @param p the point to which the velocity is applied
     * @return the new point after applying the velocity
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }
    /**
     * Returns the horizontal component of the velocity.
     *
     * @return the dx value
     */
    public double getDx() {
        return dx;
    }
    /**
     * Returns the vertical component of the velocity.
     *
     * @return the dy value
     */
    public double getDy() {
        return dy;
    }
    /**
     * Calculates the speed based on the current velocity components.
     *
     * @return The speed.
     */
    public double getSpeed() {
        return Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
    }
}
