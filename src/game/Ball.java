package game;

import biuoop.DrawSurface;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;

import java.awt.*;

/**
 * Represents Ball with a center point, radius, color, and velocity.
 */

public class Ball implements Sprite {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity velocity;
    private Rectangle grayRectangle;
    private Rectangle yellowRectangle;
    private GameEnvironment gameE;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    /**
     * Constructs a Ball with a center point, radius, and color.
     *
     * @param center the center point of the ball
     * @param r      the radius of the ball
     * @param color  the color of the ball
     */

    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.velocity = new Velocity(0, 0); // Default velocity
        this.grayRectangle = new Rectangle(50, 50, 500, 500);
        this.yellowRectangle = new Rectangle(450, 450, 600, 600);
    }

    /**
     * Sets the game environment.
     *
     * @param gameEnvironment The GameEnvironment object to set
     */
    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameE = gameEnvironment;
    }

    /**
     * Constructs a Ball with x, y coordinates for the center, radius, and color.
     *
     * @param x     the x-coordinate of the center
     * @param y     the y-coordinate of the center
     * @param r     the radius of the ball
     * @param color the color of the ball
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new Point((int) x, (int) y);
        this.r = r;
        this.color = color;
        // Default velocity
        this.velocity = new Velocity(0, 0);

        this.grayRectangle = new Rectangle(50, 50, 500, 500);
        this.yellowRectangle = new Rectangle(450, 450, 600, 600);
    }

    /**
     * Returns the x-coordinate of the center of the ball.
     *
     * @return the x-coordinate of the center
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Returns the y-coordinate of the center of the ball.
     *
     * @return the y-coordinate of the center
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Returns the radius of the ball.
     *
     * @return the radius of the ball
     */
    public int getSize() {
        return r;
    }

    /**
     * Returns the color of the ball.
     *
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }


    /**
     * Sets the color of this object.
     *
     * @param color the new color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }



    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param surface the DrawSurface to draw the ball on
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.getColor());
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param velocity the new velocity of the ball
     */

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param dx the change in x direction
     * @param dy the change in y direction
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Returns the velocity of the ball.
     *
     * @return the velocity of the ball
     */

    public Velocity getVelocity() {
        return this.velocity;
    }
    /**
     * Moves the object one step according to its velocity.
     * Handles collisions with other collidable objects.
     */
    public void moveOneStep() {
        Point newCenter = this.velocity.applyToPoint(this.center);
        Line trajectory = new Line(center, newCenter);
        CollisionInfo collisionInfo = this.gameE.getClosestCollision(trajectory);
        if (collisionInfo == null) {
            this.center = newCenter;
        } else {
            Point collisionP = collisionInfo.collisionPoint();
            Collidable collidable = collisionInfo.collisionObject();

            double newX = collisionP.getX() - velocity.getDx() * 0.05;
            double newY = collisionP.getY() - velocity.getDy() * 0.05;
            this.center = new Point(newX, newY);

            this.velocity = collidable.hit(this, collisionP, this.velocity);

            // Check if the ball is still inside the block
            if (collidable.getCollisionRectangle().isInside(this.center)) {
                // If it is, move the ball is outside the block
                this.center = this.velocity.applyToPoint(this.center);
                // Add this line to change the direction of the ball when it is inside the paddle
                this.velocity = new Velocity(-this.velocity.getDx(), -this.velocity.getDy());
            }
            if (collidable.isPaddle() && collidable.getCollisionRectangle().isInside(this.center)) {
                this.center = new Point(newCenter.getX(), newCenter.getY() - 12);
                this.velocity = new Velocity(-this.velocity.getDx(), this.velocity.getDy());
            }
        }
    }

    /**
     * Moves the ball one step within the gray rectangle area.
     * If the ball hits the border of the gray rectangle, its direction is reversed.
     */
    public void moveOneStepInGrey() {
        Point newCenter = this.velocity.applyToPoint(this.center);
        double newX = newCenter.getX();
        double newY = newCenter.getY();
        // Get the boundaries of the gray rectangle
        int minX = grayRectangle.getMinX();
        int maxX = grayRectangle.getMaxX();
        int minY = grayRectangle.getMinY();
        int maxY = grayRectangle.getMaxY();
        // Check for collision with the left edge
        if (newX - this.r < minX) {
            newX = minX + this.r; // Adjust position to be inside the rectangle
            this.velocity = new Velocity(-this.velocity.getDx(), this.velocity.getDy());
        }
        // Check for collision with the left edge
        if (newX + this.r > maxX) {
            newX = maxX - this.r; // Adjust position to be inside the rectangle
            this.velocity = new Velocity(-this.velocity.getDx(), this.velocity.getDy());
        }
        // Check for collision with the top edge
        if (newY - this.r < minY) {
            newY = minY + this.r;
            this.velocity = new Velocity(this.velocity.getDx(), -this.velocity.getDy());
        }
        // Check for collision with the bottom edge
        if (newY + this.r > maxY) {
            newY = maxY - this.r; // Adjust position to be inside the rectangle
            this.velocity = new Velocity(this.velocity.getDx(), -this.velocity.getDy());
        }
        // Update the center of the ball with the new position
        this.center = new Point(newX, newY);
    }
    /**
     * Moves the ball one step outside the gray rectangle, handling collisions
     * with the window boundaries, the gray rectangle, and the yellow rectangle.
     */
    public void moveOneStepOutsideGrey() {
        Point newCenter = this.velocity.applyToPoint(this.center);
        double newX = newCenter.getX();
        double newY = newCenter.getY();
        //// Gray Arkanoid.Shapes.Shapes.Rectangle boundaries
        int minGrayX = grayRectangle.getMinX();
        int maxGrayX = grayRectangle.getMaxX();
        int minGrayY = grayRectangle.getMinY();
        int maxGrayY = grayRectangle.getMaxY();
        //// yellow Arkanoid.Shapes.Shapes.Rectangle boundaries
        int yellowMinX = yellowRectangle.getMinX();
        int yellowMaxX = yellowRectangle.getMaxX();
        int yellowMinY = yellowRectangle.getMinY();
        int yellowMaxY = yellowRectangle.getMaxY();
        //window boundaries
        // Check for collision with the left or right edge
        if (newX - this.r < 0) {
            newX = this.r;
            this.velocity = new Velocity(-this.velocity.getDx(), this.velocity.getDy());
        }
        if (newX + this.r > WIDTH) {
            newX = WIDTH - this.r;
            this.velocity = new Velocity(-this.velocity.getDx(), this.velocity.getDy());
        }
        // Check for collision with the top or bottom edge
        if (newY - this.r < 0) {
            newY = this.r;
            this.velocity = new Velocity(this.velocity.getDx(), -this.velocity.getDy());
        }
        if (newY + this.r > HEIGHT) {
            newY = HEIGHT - this.r;
            this.velocity = new Velocity(this.velocity.getDx(), -this.velocity.getDy());
        }
        // Gray area
        // Left Grey area
        if (newX - this.r < minGrayX && newX + this.r > minGrayX && newY > minGrayY && newY < maxGrayY) {
            newX = minGrayX - this.r;
            this.velocity = new Velocity(-this.velocity.getDx(), this.velocity.getDy());
        }
        // Top Grey area
        if (newY - this.r < minGrayY && newY + this.r > minGrayY && newX > minGrayX && newX < maxGrayX) {
            newY = minGrayY - this.r;
            this.velocity = new Velocity(this.velocity.getDx(), -this.velocity.getDy());
        }
        // Right Grey area
        if (newX + this.r > maxGrayX && newX - this.r < maxGrayX && newY > minGrayY && newY < maxGrayY) {
            newX = maxGrayX + this.r;
            this.velocity = new Velocity(-this.velocity.getDx(), this.velocity.getDy());
        }
        // Bottom Grey area
        if (newY + this.r > maxGrayY && newY - this.r < maxGrayY && newX > minGrayX && newX < maxGrayX) {
            newY = maxGrayY + this.r;
            this.velocity = new Velocity(this.velocity.getDx(), -this.velocity.getDy());
        }
        //yellow area
        // Left yellow area
        if (newX - this.r < yellowMinX && newX + this.r > yellowMinX && newY > yellowMinY && newY < yellowMaxY) {
            newX = yellowMinX - this.r;
            this.velocity = new Velocity(-this.velocity.getDx(), this.velocity.getDy());
        }
        // Top yellow area
        if (newY - this.r < yellowMinY && newY + this.r > yellowMinY && newX > yellowMinX && newX < yellowMaxX) {
            newY = yellowMinY - this.r;
            this.velocity = new Velocity(this.velocity.getDx(), -this.velocity.getDy());
        }
        // Right yellow area
        if (newX + this.r > yellowMaxX && newX - this.r < yellowMaxX && newY > yellowMinY && newY < yellowMaxY) {
            newX = yellowMaxX + this.r;
            this.velocity = new Velocity(-this.velocity.getDx(), this.velocity.getDy());
        }
        // Bottom yellow area
        if (newY + this.r > yellowMaxY && newY - this.r < yellowMaxY && newX > yellowMinX && newX < yellowMaxX) {
            newY = yellowMaxY + this.r;
            this.velocity = new Velocity(this.velocity.getDx(), -this.velocity.getDy());
        }
        // Check for collision with the corners of gray and yellow rectangles
        Point[] grayCorners = {
                new Point(minGrayX, minGrayY),
                new Point(minGrayX, maxGrayY),
                new Point(maxGrayX, minGrayY),
                new Point(maxGrayX, maxGrayY)
            };
        Point[] yellowCorners = {
                new Point(yellowMinX, yellowMinY),
                new Point(yellowMinX, yellowMaxY),
                new Point(yellowMaxX, yellowMinY),
                new Point(yellowMaxX, yellowMaxY)
            };
        for (int i = 0; i < 4; i++) {
            Point point = new Point(newX, newY);
            if (point.distance(grayCorners[i]) <= this.r) {
                this.velocity = new Velocity(-this.velocity.getDx(), -this.velocity.getDy());
            }
        }
        for (int i = 0; i < 4; i++) {
            Point point = new Point(newX, newY);
            if (point.distance(yellowCorners[i]) <= this.r) {
                this.velocity = new Velocity(-this.velocity.getDx(), -this.velocity.getDy());
            }
        }
        this.center = new Point(newX, newY);
    }

    /**
     * Adds this object to the game.
     *
     * @param g The game to which this object should be added.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * Removes this sprite from the specified game.
     *
     * @param game the game from which to remove this sprite
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }

}