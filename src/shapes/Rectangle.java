package shapes;

import java.util.ArrayList;


/**
 * The Rectangle class represents a rectangle defined by its minimum and maximum x and y coordinates.
 */
public class Rectangle {
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Constructs a Rectangle with specified minimum and maximum x and y coordinates.
     *
     * @param minX the minimum x-coordinate of the rectangle
     * @param minY the minimum y-coordinate of the rectangle
     * @param maxX the maximum x-coordinate of the rectangle
     * @param maxY the maximum y-coordinate of the rectangle
     */
    public Rectangle(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Creates a rectangle based on the given upper-left corner point, width, and height.
     *
     * @param upperLeft The upper-left corner point of the rectangle.
     * @param width     The width of the rectangle.
     * @param height    The height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }
    /**
     * Returns the intersection points of the rectangle with a given line.
     *
     * @param line The line to check for intersection.
     * @return A list of intersection points, or an empty list if there is no intersection.
     */
    public java.util.List<Point> intersectionPoints(Line line) {

        java.util.List<Point> list = new ArrayList<>();
        Point p1 = this.upperLeft;
        Point p2 = new Point(p1.getX(), p1.getY() + this.height);
        Point p3 = new Point(p1.getX() + this.width, p1.getY() + this.height);
        Point p4 = new Point(p1.getX() + this.width, p1.getY());


        Line line1 = new Line(p1, p2);
        Line line2 = new Line(p2, p3);
        Line line3 = new Line(p3, p4);
        Line line4 = new Line(p4, p1);


        Line[] lines = {line1, line2, line3, line4};
        for (Line l : lines) {
            if (l.intersectionWith(line) != null) {
                list.add(l.intersectionWith(line));
            }
        }
        return list;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return The width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return The height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return The height of the rectangle.
     */

    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Sets the maximum x-coordinate of the rectangle.
     *
     * @param maxX the new maximum x-coordinate
     */
    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }
    /**
     * Sets the maximum y-coordinate of the rectangle.
     *
     * @param maxY the new maximum y-coordinate
     */
    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    /**
     * Sets the minimum x-coordinate of the rectangle.
     *
     * @param minX the new minimum x-coordinate
     */
    public void setMinX(int minX) {
        this.minX = minX;
    }
    /**
     * Sets the minimum y-coordinate of the rectangle.
     *
     * @param minY the new minimum y-coordinate
     */
    public void setMinY(int minY) {
        this.minY = minY;
    }
    /**
     * Returns the maximum x-coordinate of the rectangle.
     *
     * @return the maximum x-coordinate
     */
    public int getMaxX() {
        return maxX;
    }
    /**
     * Returns the maximum y-coordinate of the rectangle.
     *
     * @return the maximum y-coordinate
     */
    public int getMaxY() {
        return maxY;
    }
    /**
     * Returns the minimum x-coordinate of the rectangle.
     *
     * @return the minimum x-coordinate
     */
    public int getMinX() {
        return minX;
    }
    /**
     * Returns the minimum y-coordinate of the rectangle.
     *
     * @return the minimum y-coordinate
     */
    public int getMinY() {
        return minY;
    }

    /**
     * This method checks if a given point is inside the rectangle.
     *
     * @param p a given point
     * @return True if the point is inside the rectangle or False otherwise.
     */
    public boolean isInside(Point p) {
        // Get the x and y coordinates of the point
        double x = p.getX();
        double y = p.getY();
        // Get the x and y coordinates of the upper left corner of the rectangle
        double rectX = this.upperLeft.getX();
        double rectY = this.upperLeft.getY();
        // Get the width and height of the rectangle
        double rectWidth = this.width;
        double rectHeight = this.height;
        // Check if the point is inside the rectangle
        // The point is inside the rectangle if its x-coordinate is between the x-coordinate of the left and right sides
        // of the rectangle and it y-coordinate is between the y-coordinate of the top and bottom sides of the rectangle
        return (x >= rectX && x <= rectX + rectWidth) && (y >= rectY && y <= rectY + rectHeight);
    }

}
