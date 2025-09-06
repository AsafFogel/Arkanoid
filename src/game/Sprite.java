package game;

import biuoop.DrawSurface;

/**
 * Represents a sprite that can be drawn on a surface.
 */
public interface Sprite {

    /**
     * Draws the sprite on the given drawing surface.
     *
     * @param d The drawing surface to draw on.
     */
    void drawOn(DrawSurface d);

    /**
     * Updates the sprite's state as time passes.
     */
    void timePassed();
}
