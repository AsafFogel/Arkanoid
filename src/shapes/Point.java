package shapes;

/**
 * Represents a point in 2D space with x and y coordinates.
 */
public class Point {
    private static final double EPSILON = 0.0000001;
    private double x;
    private double y;
    /**
     * Constructs a Shapes.Point with the specified coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Returns the distance between this point and another point.
     *
     * @param other the other point
     * @return the distance to the other point
     */
    public double distance(Point other) {
        double xLength = (this.x - other.x) * (this.x - other.x);
        double yLength = (this.y - other.y) * (this.y - other.y);
        return Math.sqrt(xLength + yLength);
    }
    /**
     * Checks if this point is equal to another point.
     *
     * @param other the other point
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }
        return Math.abs(this.x - other.x) < EPSILON && Math.abs(this.y - other.y) < EPSILON;
    }

    /**
     * Returns the x-coordinate of this point.
     *
     * @return the x-coordinate
     */
    public double getX() {
        return this.x;
    }
    /**
     * Returns the y-coordinate of this point.
     *
     * @return the y-coordinate
     */
    public double getY() {
        return this.y;
    }
}
