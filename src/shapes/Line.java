package shapes;

/**
 * Represents a line segment in 2D space defined by two points.
 */
public class Line {
    private static final double EPSILON = 0.0000001;
    private Point start;
    private Point end;
    /**
     * Constructs a Line with the specified start and end points.
     *
     * @param start the start point of the line
     * @param end   the end point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }
    /**
     * Constructs a Line with the specified coordinates.
     *
     * @param x1 the x-coordinate of the start point
     * @param y1 the y-coordinate of the start point
     * @param x2 the x-coordinate of the end point
     * @param y2 the y-coordinate of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of the line.
     *
     * @return the length of the line
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return the middle point
     */
    public Point middle() {
        double xMid = (start.getX() + end.getX()) / 2;
        double yMid = (start.getY() + end.getY()) / 2;
        return new Point(xMid, yMid);
    }

    /**
     * Returns the start point of the line.
     *
     * @return the start point
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return the end point
     */
    public Point end() {
        return this.end;
    }


    /**
     * Returns the slope of the line.
     *
     * @return the slope of the line
     */
    public double getSlope() {
        double x1, x2, y1, y2;
        x1 = this.start.getX();
        x2 = this.end.getX();
        y1 = this.start.getY();
        y2 = this.end.getY();
        return (y2 - y1) / (x2 - x1);
    }
    /**
     * Returns the y-intercept (b) of the line equation y = mx + b.
     *
     * @return the y-intercept of the line
     */
    public double getB() {
        return (this.end.getY() - (this.getSlope() * this.end.getX()));
    }

    /**
     * Checks if this line intersects with another line.
     *
     * @param other the other line
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null || isCollinear(other);
    }

    /**
     * Checks if this line intersects with two other lines.
     *
     * @param other1 the first other line
     * @param other2 the second other line
     * @return true if both lines intersect with this line, false otherwise
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return isIntersecting(other1) && isIntersecting(other2);
    }
    /**
     * Returns the intersection point of this line with another line, if they intersect.
     *
     * @param other the other line
     * @return the intersection point, or null if they do not intersect
     */
    public Point intersectionWith(Line other) {
        // First line X coordinates
        double x1 = this.start.getX();
        double x2 = this.end.getX();
        // Second line X coordinates
        double x3 = other.start.getX();
        double x4 = other.end.getX();

        // Check if this line is a point (start and end are the same)
        if (this.start.equals(this.end)) {
            // check if the other line is also point
            if (other.start.equals(other.end)) {
                // If both are points, return the point if they are the same, otherwise null
                return (this.start.equals(other.start) ? start : null);
            } else {
                // If this line is a point but the other is not, check if this point lies on the other line
                return (other.isPointOnLine(start().getX(), start().getY()) ? start : null);
            }
        }
        // If other line is a point but this line is not, check if this point lies on this line
        if (other.start.equals(other.end)) {
            return (other.isPointOnLine(start().getX(), start().getY()) ? start : null);
        }

        // If both lines are vertical (parallel), they do not intersect
        if ((Math.abs(x1 - x2) < EPSILON) && (Math.abs(x3 - x4) < EPSILON)) {
            return null;
        }

        // If this line is vertical, calculate the intersection point using the other line's slope and intercept
        if (Math.abs(x1 - x2) < EPSILON) {
            double m2 = other.getSlope();
            double b2 = other.getB();
            //the line is vertical so the x coordinate of the intersection point is x2
            double intersectionY = m2 * x2 + b2;
            // Check if the intersection point lies on both lines
            if (isPointOnLine(x2, intersectionY) && other.isPointOnLine(x2, intersectionY)) {
                return new Point(x2, intersectionY);
            }
            return null;
        }

        // If the other line is vertical, calculate the intersection point using this line's slope and intercept
        if (Math.abs(x3 - x4) < EPSILON) {
            double m1 = getSlope();
            double b1 = getB();
            //the line is vertical so the x coordinate of the intersection point is x4
            double intersectionY = m1 * x4 + b1;
            // Check if the intersection point lies on both lines
            if (isPointOnLine(x4, intersectionY) && other.isPointOnLine(x4, intersectionY)) {
                return new Point(x4, intersectionY);
            }
            return null;
        }

        // Calculate slopes and y-intercepts of both lines
        double m1 = getSlope();
        double b1 = getB();
        double m2 = other.getSlope();
        double b2 = other.getB();
        // If the slopes are equal, the lines are parallel and do not intersect
        if (Math.abs(m1 - m2) < EPSILON) {
            return null;
        }

        // Calculate intersection point using the formulas for line intersection
        double intersectionX = (b2 - b1) / (m1 - m2);
        double intersectionY = m1 * intersectionX + b1;
        // Check if the intersection point lies on both lines
        if (isPointOnLine(intersectionX, intersectionY) && other.isPointOnLine(intersectionX, intersectionY)) {
            return new Point(intersectionX, intersectionY);
        }
        return null;
    }
    /**
     * Checks if a point (x, y) lies on this line segment.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return true if the point lies on the line segment, false otherwise
     */
    public boolean isPointOnLine(double x, double y) {

        double minX = Math.min(start.getX(), end.getX());
        double maxX = Math.max(start.getX(), end.getX());
        double minY = Math.min(start.getY(), end.getY());
        double maxY = Math.max(start.getY(), end.getY());

        if (x < minX || x > maxX || y < minY || y > maxY) {
            return false;
        }

        if (Math.abs(start.getX() - end.getX()) < EPSILON) {
            return Math.abs(start.getX() - x) < EPSILON;
        }

        double m1 = this.getSlope();
        double b1 = this.getB();
        double expectedY = (m1 * x) + b1;

        return Math.abs(expectedY - y) < EPSILON;
    }
    /**
     * Checks if two lines are collinear and overlapping.
     *
     * @param other the other line
     * @return true if the lines are collinear and overlapping, false otherwise
     */
    public boolean isCollinear(Line other) {
        // Check if either endpoint of the other line lies on this line, or vice versa
        return isPointOnLine(other.start.getX(), other.start.getY())
                || isPointOnLine(other.end.getX(), other.end.getY())
                || other.isPointOnLine(this.start.getX(), this.start.getY())
                || other.isPointOnLine(this.end.getX(), this.end.getY());
    }

    /**
     * Checks if this line is equal to another line.
     * Two lines are considered equal if they have the same start and end points,
     * regardless of the order of the points.
     *
     * @param other The other line to compare to.
     * @return {@code true} if the lines are equal, {@code false} otherwise.
     */
    public boolean equals(Line other) {
        return this.start.equals(other.start) && this.end.equals(other.end)
                || this.start.equals(other.end) && this.end.equals(other.start);
    }
    /**
     * Finds the closest intersection point to the start of the line within the given rectangle.
     *
     * @param rect The rectangle to check for intersection points.
     * @return The closest intersection point to the start of the line, or null if no intersections exist.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> list = rect.intersectionPoints(this);
        if (list.isEmpty()) {
            return null;
        }
        double d = Double.MAX_VALUE;
        Point returnPoint = null;
        for (Point p : list) {
            if (p != null && p.distance(this.start) < d) {
                d = p.distance(this.start);
                returnPoint = p;
            }
        }
        return returnPoint;
    }
}