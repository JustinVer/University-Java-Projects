/**
 * A class of different methods to create different key orders for loading passengers
 */
public class KeyCreator {

    /**
     * Creates an array of keys based on the passengers array. The key is based on an outer to inner order.
     *
     * @param passengers the array of passengers to create keys for
     * @return the array of keys
     */
    public static int[] outerToInnerKeyOrder(Passenger[] passengers) {
        int[] key = new int[passengers.length];
        for (int i = 0; i < key.length; i++) {
            if (Simulation.isWindowSeat(passengers[i].seat)) {
                key[i] = 1;
            } else {
                key[i] = 2;
            }
        }
        return key;
    }

    /**
     * Creates an array of keys based on the passengers array. The key is based on a back to front order.
     *
     * @param passengers the array of passengers to create keys for
     * @return the array of keys
     */
    public static int[] backToFrontKeyOrder(Passenger[] passengers, int numAisles) {
        int[] key = new int[passengers.length];
        for (int i = 0; i < key.length; i++) {
            key[i] = numAisles + 1 - passengers[i].row;
        }
        return key;
    }

    /**
     * Creates an array of keys based on the passengers array. The key is based on a fastest first order.
     *
     * @param passengers the array of passengers to create keys for
     * @return the array of keys
     */
    public static int[] fastestFirstKeyOrder(Passenger[] passengers) {
        int[] key = new int[passengers.length];
        for (int i = 0; i < key.length; i++) {
            key[i] = passengers[i].seconds;
        }
        return key;
    }

    /**
     * Creates an array of keys based on the passengers array. The key is based on a slowest first order.
     *
     * @param passengers the array of passengers to create keys for
     * @return the array of keys
     */
    public static int[] slowestFirstKeyOrder(Passenger[] passengers) {
        int[] key = new int[passengers.length];
        for (int i = 0; i < key.length; i++) {
            key[i] = 5 - passengers[i].seconds;
        }
        return key;
    }

    /**
     * Creates an array of keys based on the passengers array. The key is based on a first come first served order.
     *
     * @param passengers the array of passengers to create keys for
     * @return the array of keys
     */
    public static int[] firstComeFirstServedKeyOrder(Passenger[] passengers) {
        int[] key = new int[passengers.length];
        for (int i = 0; i < key.length; i++) {
            key[i] = i + 1;
        }
        return key;
    }
}
