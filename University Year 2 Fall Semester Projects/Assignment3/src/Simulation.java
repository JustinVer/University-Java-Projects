/**
 * A simulation of loading passengers onto a plane
 */
public class Simulation {
    private final MinHeap passengerHeap;
    int numAisles;
    //Boolean on whether the simulation should print out the values at each cycle
    boolean print;

    /**
     * Creates a new simulation
     *
     * @param passengerHeap a heap of all the passengers
     * @param numAisles     the number of aisles to simulate
     * @param print         whether the simulation should print out the values at each cycle or not
     */
    Simulation(MinHeap passengerHeap, int numAisles, boolean print) {
        this.passengerHeap = passengerHeap;
        this.numAisles = numAisles;
        this.print = print;
    }

    /**
     * Runs the simulation
     *
     * @return the total time the simulation took
     */
    public int runSimulation() {
        MinHeap passengerLine = passengerHeap;
        Aisle[] aisles = new Aisle[numAisles];
        //Initiates the aisles with null passengers
        for (int i = 0; i < aisles.length; i++) {
            aisles[i] = new Aisle();
        }
        int simulationLength = 0;
        //Runs the simulation until everyone is in their seat
        while (!passengerLine.isEmpty() || !aislesEmpty(aisles)) {
            printSimInfo(aisles, passengerLine);
            for (int i = aisles.length - 1; i > -1; i--) {
                if (aisles[i].passenger != null) {
                    if (aisles[i].wait > 0) {
                        aisles[i].wait--;
                    }
                    if (aisles[i].wait <= 0) {
                        if (aisles[i].passenger.row == i + 1) {
                            aisles[i].passenger = null;
                        } else {
                            moveForward(aisles, i);
                        }
                    }
                }
            }
            if (aisles[0].passenger == null && !passengerLine.isEmpty()) {
                aisles[0].passenger = passengerLine.removeMin();
                calculateWait(aisles, 0);
            }
            simulationLength++;
        }
        printSimInfo(aisles, passengerLine);
        return simulationLength;
    }

    /**
     * prints the simulation information if designated print
     *
     * @param aisles        the array of aisles
     * @param passengerLine heap of passengers
     */
    private void printSimInfo(Aisle[] aisles, MinHeap passengerLine) {
        //Prints the info if the print boolean is true
        if (print) {
            System.out.println("Aisle: " + java.util.Arrays.toString(aisles));
            passengerLine.printContents();
            System.out.println();
        }
    }

    /**
     * moves the person in aisle i forward if the next aisle is empty
     *
     * @param aisles the array of aisles
     * @param i      the index of the person to move forward
     */
    private void moveForward(Aisle[] aisles, int i) {
        if (aisles.length > i + 1 && aisles[i + 1].passenger == null) {
            aisles[i + 1].passenger = aisles[i].passenger;
            aisles[i].passenger = null;
            calculateWait(aisles, i + 1);
        }
    }

    /**
     * Calculates the wait time of the person in the aisle of index i
     *
     * @param aisles the array of aisles
     * @param i      the index of the person to calculate
     */
    private void calculateWait(Aisle[] aisles, int i) {
        int wait = 0;
        if (aisles[i].passenger == null) {
            throw new RuntimeException("calculating empty aisle wait");
        }
        wait += aisles[i].passenger.seconds;
        if (aisles[i].passenger.row == i + 1) {
            wait += aisles[i].passenger.seconds * 20;
            if (isWindowSeat(aisles[i].passenger.seat)) {
                wait += 10;
            }
        }
        aisles[i].wait = wait;
    }

    /**
     * Checks if seat letter is a window seat
     *
     * @param seat the letter of the seat
     * @return true if seat is a window, false otherwise
     */
    public static boolean isWindowSeat(char seat) {
        String windowSeats = "AD";
        return windowSeats.contains("" + seat);
    }

    /**
     * Checks if all the aisles are empty
     *
     * @param aisles the array of aisles
     * @return true if the aisles are all empty, false otherwise
     */
    private boolean aislesEmpty(Aisle[] aisles) {
        for (Aisle aisle : aisles) {
            if (aisle.passenger != null) {
                return false;
            }
        }
        return true;
    }

}
