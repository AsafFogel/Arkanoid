package game;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a collection of sprites.
 */
public class SpriteCollection {
    // List to store all the sprites
    private java.util.List<Sprite> spriteList;

    /**
     * Constructor arkanoid.Game.arkanoid.SpriteCollection.
     * Initializes an empty list of sprites.
     */
    public SpriteCollection() {
        this.spriteList = new java.util.ArrayList<>();
    }

    /**
     * Adds a sprite to the collection.
     *
     * @param s The sprite to be added.
     */
    public void addSprite(Sprite s) {
        this.spriteList.add(s);
    }

    /**
     * Removes a sprite from the list of sprites.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.spriteList.remove(s);
    }

    /**
     * Calls the timePassed method for all sprites in the collection.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spriteListCopy = new ArrayList<>(this.spriteList);
        for (Sprite s : spriteListCopy) {
            s.timePassed();
        }
    }

    /**
     * Calls the drawOn method for all sprites in the collection.
     *
     * @param d The surface on which the sprites should be drawn.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : spriteList) {
            s.drawOn(d);
        }
    }
}
