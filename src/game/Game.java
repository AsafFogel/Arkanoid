package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import shapes.Point;
import shapes.Rectangle;

import java.awt.Color;
import java.util.Random;


/**
 * Represents the main game class.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment gameEnvironment;
    private GUI gui;
    private biuoop.Sleeper sleeper;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;

    /**
     * Constructs a Game instance.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.gameEnvironment = new GameEnvironment();
        this.gui = new GUI("Game", 800, 600);
        this.sleeper = new Sleeper();
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
        this.score = new Counter();
    }


    /**
     * Adds a collidable object to the game environment.
     *
     * @param c The collidable object to add.
     */
    public void addCollidable(Collidable c) {
        this.gameEnvironment.addCollidable(c);
    }


    /**
     * Adds a sprite object to the game.
     *
     * @param s The sprite object to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initializes the game by setting up the environment, creating objects, and adding them to the game.
     */
    public void initialize() {
        // Create Paddle
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        Paddle paddle = new Paddle(keyboard, new Rectangle(new Point(350, 590), 100, 10), 5);
        paddle.addToGame(this);


        // Create the borders
        Block borderTop = new Block(new Rectangle(new Point(0, 0), 800, 0));
        Block borderBottom = new Block(new Rectangle(new Point(0, 600), 800, 0));
        Block borderLeft = new Block(new Rectangle(new Point(0, 0), 0, 600));
        Block borderRight = new Block(new Rectangle(new Point(800, 0), 0, 600));


        // Add the borders to the game
        Block[] borders = {borderRight, borderBottom, borderTop, borderLeft};
        for (Block b :borders) {
            b.addToGame(this);
        }
        BallRemover bl = new BallRemover(this, this.remainingBalls);
        borderBottom.addHitListener(bl);


        Random rand = new Random();
        Ball ball1 = new Ball(700, 10, 5, Color.DARK_GRAY);
        Ball ball2 = new Ball(700, 10, 5, Color.BLUE);
        Ball ball3 = new Ball(700, 10, 5, Color.YELLOW);
        Ball[] balls = {ball1, ball2, ball3};
        for (Ball ball:balls) {
            int angle = rand.nextInt(360);
            ball.setVelocity(Velocity.fromAngleAndSpeed(angle, 3));
            ball.setGameEnvironment(this.gameEnvironment);
            ball.addToGame(this);
            this.remainingBalls.increase(1);
        }



        // Generate block
        Color color1 = Color.YELLOW;
        Color color2 = Color.PINK;
        Color color3 = Color.ORANGE;
        Color color4 = Color.GRAY;
        Color color5 = Color.GREEN;
        Color color6 = Color.RED;
        int blockWidth = 50;
        int blockHeight = 20;
        int k = 7;
        Color[] colors = {color1, color2, color3, color4, color5, color6};
        for (int j = 0; j < 7; j++) {
            Color rowColor = colors[j % colors.length];
            for (int i = 1; i <= k && k <= 12; i++) {
                Block block = new Block(new Rectangle(new Point(800 - (i * blockWidth),
                        150 - (j * blockHeight)), blockWidth, blockHeight), rowColor);
                block.addToGame(this);

                // Add BlockRemover as a listener
                BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
                block.addHitListener(blockRemover);
                ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
                block.addHitListener(scoreTrackingListener);
                this.remainingBlocks.increase(1);
            }
            k++;
        }
        // Create the ScoreIndicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        scoreIndicator.addToGame(this);
    }

    /**
     * Draws the background on the given DrawSurface.
     *
     * @param d the DrawSurface to draw the background on
     */
    public void drawBackground(DrawSurface d) {
        d.setColor(Color.CYAN); // Set the background color
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight()); // Fill the background
    }

    /**
     * Runs the game's animation loop.
     */
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            if (this.remainingBlocks.getValue() == 0 || this.remainingBalls.getValue() == 0) {
                this.score.increase(100);
                gui.close();
                return;
            }
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            drawBackground(d);


            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Removes a collidable object from the game environment.
     *
     * @param c the collidable to be removed
     */
    public void removeCollidable(Collidable c) {
        this.gameEnvironment.removeCollidable(c);
    }

    /**
     * Removes a sprite from the sprite collection.
     *
     * @param s the sprite to be removed
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

}





