package game;

/**
 * Counter is a simple class that is used for counting things.
 */
public class Counter {

    private int value;

    /**
     * Default constructor for Counter.
     * Initializes the counter to zero.
     */
    public Counter() {
        this.value = 0;
    }

    /**
     * Constructor for Counter with an initial value.
     *
     * @param counter the initial value for the counter
     */
    public Counter(int counter) {
        this.value = counter;
    }

    /**
     * Adds a number to the current count.
     *
     * @param number the number to be added
     */
    void increase(int number) {
        this.value = this.value + number;
    }

    /**
     * Subtracts a number from the current count.
     *
     * @param number the number to be subtracted
     */
    void decrease(int number) {
        this.value = this.value - number;
    }

    /**
     * Returns the current count.
     *
     * @return the current count
     */
    int getValue() {
        return this.value;
    }
}
