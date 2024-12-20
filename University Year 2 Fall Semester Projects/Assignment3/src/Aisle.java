/**
 * A class to store aisle information
 */
public class Aisle {
    Passenger passenger;
    int wait;

    /**
     * Creates a new aisle
     *
     * @param passenger the passenger to store
     * @param wait      the amount of time the passenger must wait for
     */
    Aisle(Passenger passenger, int wait) {
        this.passenger = passenger;
        this.wait = wait;
    }

    /**
     * Creates a new aisle with base values of a null passenger and wait of 0
     */
    Aisle() {
        new Aisle(null, 0);
    }

    @Override
    public String toString() {
        if (passenger != null) {
            return "[" + passenger + " waiting for " + wait + " longer]";
        } else {
            return "[null waiting for 0 longer]";
        }
    }
}
