/**
 * A class to store passenger information
 */
public class Passenger {
    public int row;
    public int seconds;
    public char seat;

    /**
     * Creates a new passenger
     *
     * @param r the row in the plane
     * @param p the speed of the passenger in seconds
     * @param s the seat letter of the passenger
     */
    Passenger(int r, char p, int s) {
        this.row = r;
        this.seconds = s;
        this.seat = p;
    }

    @Override
    public String toString() {
        return "[Passenger " +
                "row " + row +
                ", seat " + seat +
                ", seconds " + seconds + "]";
    }
}
