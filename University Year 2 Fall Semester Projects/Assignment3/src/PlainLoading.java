import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Runs simulations based on different ways to load a plain
 * <p>
 * IDE used: IntelliJ
 *
 * @Author Justin Verhoog
 * ST# 7756034
 * @Version 1.0
 * @Since 2024-11-08
 */
public class PlainLoading {
    //The number of aisles read from the text file
    int numAisles = 0;
    //The number of rows in the plane for bulk simulation testing
    int[] n = new int[]{10, 20, 50};

    PlainLoading() throws FileNotFoundException {

        //Part A of the assignment. Runs all five simulations on the data from the text file
        System.out.println("Part A:");
        Passenger[] passengers = loadFromFile("assn3in.txt");
        MinHeap heap = new MinHeap();

        //first come first served order simulation
        heap.buildHeap(passengers, KeyCreator.firstComeFirstServedKeyOrder(passengers));
        Simulation simFCFS = new Simulation(heap, numAisles, true);
        System.out.println();
        System.out.println("Running first come first served order simulation");
        System.out.println("Total time to run first come first served order simulation is " + simFCFS.runSimulation());
        //slowest first order simulation
        heap.buildHeap(passengers, KeyCreator.slowestFirstKeyOrder(passengers));
        Simulation simSF = new Simulation(heap, numAisles, true);
        System.out.println();
        System.out.println("Running slowest first order simulation");
        System.out.println("Total time to run slowest first order simulation is " + simSF.runSimulation());
        //fastest first order simulation
        heap.buildHeap(passengers, KeyCreator.fastestFirstKeyOrder(passengers));
        Simulation simFF = new Simulation(heap, numAisles, true);
        System.out.println();
        System.out.println("Running fastest first order simulation");
        System.out.println("Total time to run fastest first order simulation is " + simFF.runSimulation());
        //back to front order simulation
        heap.buildHeap(passengers, KeyCreator.backToFrontKeyOrder(passengers, numAisles));
        Simulation simBTF = new Simulation(heap, numAisles, true);
        System.out.println();
        System.out.println("Running back to front order simulation");
        System.out.println("Total time to run back to front order simulation is " + simBTF.runSimulation());
        //outer to inner order simulation
        heap.buildHeap(passengers, KeyCreator.outerToInnerKeyOrder(passengers));
        Simulation simOTI = new Simulation(heap, numAisles, true);
        System.out.println();
        System.out.println("Running outer to inner order simulation");
        System.out.println("Total time to run outer to inner order simulation is " + simOTI.runSimulation());

        //Part B of the assignment. Runs a number of simulations and outputs a table.
        System.out.println("Part B:");

        int[][][] times = new int[3][5][10];
        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < 10; j++) {
                passengers = generateFullPlane(n[i]);
                //First come first served order simulation
                heap.buildHeap(passengers, KeyCreator.firstComeFirstServedKeyOrder(passengers));
                simFCFS = new Simulation(heap, n[i], false);
                times[i][0][j] = simFCFS.runSimulation();
                //slowest first order simulation
                heap.buildHeap(passengers, KeyCreator.slowestFirstKeyOrder(passengers));
                simSF = new Simulation(heap, n[i], false);
                times[i][1][j] = simSF.runSimulation();
                //fastest first order simulation
                heap.buildHeap(passengers, KeyCreator.fastestFirstKeyOrder(passengers));
                simFF = new Simulation(heap, n[i], false);
                times[i][2][j] = simFF.runSimulation();
                //back to front order simulation
                heap.buildHeap(passengers, KeyCreator.backToFrontKeyOrder(passengers, numAisles));
                simBTF = new Simulation(heap, n[i], false);
                times[i][3][j] = simBTF.runSimulation();
                //outer to inner order simulation
                heap.buildHeap(passengers, KeyCreator.outerToInnerKeyOrder(passengers));
                simOTI = new Simulation(heap, n[i], false);
                times[i][4][j] = simOTI.runSimulation();
            }
        }
        printTimes(times);
        //Paragraphs about the data
        System.out.println("\n\nFastest First is on average the fastest, followed by Slowest First, followed by First come first served \n" +
                "and Outer to inner which both have very similar times since they are both essentially random, and \n" +
                "lastly the slowest which is Back to front taking around 30% longer than fastest first. I think that \n" +
                "fastest first and slowest first are the two fastest since they minimize the number of aisles that are \n" +
                "empty at any time since there aren’t people of different speeds going at the same time. The first to \n" +
                "only have to wait if someone is getting into their seat so it is faster. First come first served and outer \n" +
                "to inner are both around the same speed because they both essentially just choose people at \n" +
                "random to go since even when picking the outer most seats first, they still randomly select between \n" +
                "the people that have the same seat number. Finally, back to front is the slowest because it causes a \n" +
                "lot of waisted time since around three quarters of people have to wait for someone to get into there \n" +
                "seat and this method also leaves a lot of aisles empty since the back of the plane is filled first.\n" +
                "\n" +
                "I think the best way to load a plane would be to sort by speed slowest to fastest and then board \n" +
                "people so that one person from each row starting from the back boards so that people all have \n" +
                "similar speeds, and no one is waiting to get past someone while they are getting in their seats. I \n" +
                "think this would be one of the fastest methods since the most time-consuming part of the boarding \n" +
                "process is people getting into their seats and this method makes it so that everyone gets to their \n" +
                "seat around the same time as everyone else in the plane at that time. You sort by speed first so that \n" +
                "everyone gets to their seat around the same time since slow people wouldn’t be stopping fast \n" +
                "people from moving.\n" +
                "\n" +
                "The number of each passenger’s slowness being drastically different could change the results or \n" +
                "wouldn’t make much of an effect depending on the loading order. For example, in the random \n" +
                "loading orders that result wouldn’t change linearly by the number of people that are faster or slower \n" +
                "because even only having a low amount of slow people still causes a lot of people to be waiting \n" +
                "behind them no matter what the speed of the people behind them are. It would of course change \n" +
                "the result but not by a lot. Whereas with algorithms such as fastest first or the one I proposed \n" +
                "having more people be faster would decrease the amount of time it takes to load around a linear \n" +
                "scale. This is because these algorithms are designed to have people waiting around less and if \n" +
                "there are faster people they should be able to get to there seat faster. The back to front algorithm \n" +
                "would also be greatly affected by different speeds since a lot of people are stuck waiting in that \n" +
                "algorithm.\n");
    }

    /**
     * Prints out the times of the data and then makes a summary table
     *
     * @param times the times of the data. First layer of the array is for each row count. Second layer is for each type of simulation. Third layer is the actual times.
     */
    private void printTimes(int[][][] times) {
        //Print out all the times
        for (int[][] time : times) {
            for (int[] ints : time) {
                System.out.println(Arrays.toString(ints));
            }
        }
        //Creates the table
        StringBuilder builder = new StringBuilder();
        builder.append(" ___________________________________________________________________________________________________");
        builder.append("\n| Number of Rows\t| FCFS Order\t| SF Order\t\t| FF Order\t\t| BTF Order\t\t| OTI Order\t\t|");
        builder.append("\n|___________________________________________________________________________________________________|");
        for (int i = 0; i < times.length; i++) {
            builder.append("\n| ").append(n[i]).append("\t\t\t\t| ");
            for (int j = 0; j < times[i].length; j++) {
                float average = 0;
                for (int k = 0; k < times[i][j].length; k++) {
                    average += times[i][j][k];
                }
                average = average / (float) times.length;
                builder.append((int) average).append("\t\t\t").append("| ");
            }
        }
        builder.append("\n|___________________________________________________________________________________________________|");
        System.out.println(builder);
    }

    /**
     * Generates a random array full of passengers to fill the plane with size n
     *
     * @param n the number of rows in the plane
     * @return an array full of passengers to fill the plane with size n
     */
    private Passenger[] generateFullPlane(int n) {
        char[] seatLetters = new char[]{'A', 'B', 'C', 'D'};
        Passenger[] passengers = new Passenger[n * 4];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < seatLetters.length; j++) {
                passengers[(seatLetters.length * (i - 1)) + j] = new Passenger(i, seatLetters[j], (int) (Math.random() * 4) + 1);
            }
        }
        //Shuffles the array
        Collections.shuffle(Arrays.asList(passengers));
        return passengers;
    }

    /**
     * Loads the data from the specified file
     *
     * @param filepath file path of the file to be read
     * @return an array of the passengers read from the file
     * @throws FileNotFoundException
     */
    private Passenger[] loadFromFile(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        Scanner scanner;
        scanner = new Scanner(file);
        scanner.useDelimiter("\t");
        numAisles = Integer.parseInt(scanner.nextLine());
        Passenger[] passengers = new Passenger[4 * numAisles];
        for (int i = 0; scanner.hasNext(); i++) {
            passengers[i] = new Passenger(scanner.nextInt(), scanner.next().charAt(0), Integer.parseInt(scanner.nextLine().replace("\t", "")));
        }
        return passengers;
    }

    /**
     * main
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        new PlainLoading();
    }
}