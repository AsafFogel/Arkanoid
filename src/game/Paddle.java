package game;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import shapes.Point;
import shapes.Rectangle;

import java.awt.*;

/**
 * The Paddle class represents a paddle controlled by the player.
 */
public class Paddle implements Sprite, Collidable {
    private static final double EPSILON = 0.0000001;
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rectangle;
    private int speed;

    /**
     * Constructs a Paddle.
     *
     * @param keyboard  The keyboard sensor.
     * @param rectangle The rectangle representing the paddle.
     * @param speed     The speed of the paddle.
     */
    public Paddle(KeyboardSensor keyboard, Rectangle rectangle, int speed) {
        this.keyboard = keyboard;
        this.rectangle = rectangle;
        this.speed = speed;
    }

    /**
     * Moves the paddle to the left.
     */
    public void moveLeft() {
        double newX = this.rectangle.getUpperLeft().getX() - this.speed;
        if (newX < 0) {
            newX = 800;
        }
        this.rectangle = new Rectangle(new Point(newX, rectangle.getUpperLeft().getY()), rectangle.getWidth(),
                rectangle.getHeight());
    }

    /**
     * Moves the paddle to the right.
     */
    public void moveRight() {
        double newX = this.rectangle.getUpperLeft().getX() + this.speed;
        if (newX + rectangle.getWidth() > 800) {
            newX = 0 - rectangle.getWidth();
        }
        this.rectangle = new Rectangle(new Point(newX, rectangle.getUpperLeft().getY()), rectangle.getWidth(),
                rectangle.getHeight());
    }

    /**
     * Notifies the paddle that time has passed.
     */
    // Arkanoid.Sprites.arkanoid.Game.arkanoid.Sprite
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draws the paddle on the given surface.
     *
     * @param d The draw surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        Point p1 = this.rectangle.getUpperLeft();
        double width = this.rectangle.getWidth();
        double height = this.rectangle.getHeight();
        d.setColor(Color.yellow);
        d.fillRectangle((int) p1.getX(), (int) p1.getY(), (int) width, (int) height);
    }

    /**
     * Gets the collision rectangle of the paddle.
     *
     * @return The collision rectangle.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Handles the ball hitting the paddle.
     *
     * @param collisionPoint  The point of collision.
     * @param currentVelocity The current velocity of the ball.
     * @return The new velocity after the collision.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double regionWidth = (this.rectangle.getWidth() / 5);
        double collisionX = collisionPoint.getX();
        double collisionY = collisionPoint.getY();

        double startX = this.rectangle.getUpperLeft().getX();
        double startY = this.rectangle.getUpperLeft().getY();
        double width = this.rectangle.getWidth();
        double height = this.rectangle.getHeight();

        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // Calculate the new velocity based on the region of the paddle hit
        Velocity newVelocity;
        if (collisionX > startX && collisionX < startX + regionWidth) {
            newVelocity = Velocity.fromAngleAndSpeed(300, currentVelocity.getSpeed());
            return newVelocity;
        } else if (collisionX > startX + regionWidth && collisionX < startX + (2 * regionWidth)) {
            newVelocity = Velocity.fromAngleAndSpeed(360, currentVelocity.getSpeed());
            return newVelocity;
        } else if (collisionX > startX + (2 * regionWidth) && collisionX < startX + (3 * regionWidth)) {
            newVelocity = new Velocity(dx, -dy);
            return newVelocity;
        } else if (collisionX > startX + (3 * regionWidth) && collisionX < startX + (4 * regionWidth)) {
            newVelocity = Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
            return newVelocity;
        } else if (collisionX > startX + (4 * regionWidth) && collisionX < startX + (5 * regionWidth)) {
            newVelocity = Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
            return newVelocity;
        } else
            if ((Math.abs(collisionX - startX) < EPSILON && Math.abs(collisionY - startY) < EPSILON)
                || (Math.abs(collisionX - (startX + width)) < EPSILON && Math.abs(collisionY - startY) < EPSILON)
                || (Math.abs(collisionX - startX) < EPSILON && Math.abs(collisionY - (startY + height)) < EPSILON)
                || (Math.abs(collisionX - (startX + width)) < EPSILON
                    && Math.abs(collisionY - (startY + height)) < EPSILON)) {
            // Handle corner cases
            dx = -dx;
            dy = -dy;
            return new Velocity(dx, dy);
        } else {
            // Handle edge cases more precisely
            if (Math.abs(collisionPoint.getX() - this.rectangle.getUpperLeft().getX()) < EPSILON
                    || Math.abs(collisionPoint.getX() - (this.rectangle.getUpperLeft().getX()
                    + this.rectangle.getWidth())) < EPSILON) {
                dx = -dx;
            }
            return new Velocity(dx, dy);
        }
    }
    /**
     * Adds the paddle to the game.
     *
     * @param g The game.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Indicates whether this object is a paddle.
     * @return true if this object is a paddle, false otherwise.
     */
    @Override
    public boolean isPaddle() {
        return true;
    }
}