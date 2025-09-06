import game.Game;

/**
 * The Ass5Game class is the main entry point for the game.
 * It creates a new game, initializes it, and then runs it.
 */
public class Ass5Game {
    /**
     * The main method that is executed when the program starts.
     * @param args command-line arguments. Not used in this program.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
